package me.moehritz.porty;

import me.moehritz.porty.api.PortyAPI;
import me.moehritz.porty.api.TaskHandler;
import me.moehritz.porty.api.TeleportRequestHandler;
import me.moehritz.porty.cmds.*;
import me.moehritz.porty.internal.IPortyAPI;
import me.moehritz.porty.internal.ITaskHandler;
import me.moehritz.porty.internal.ITeleportRequestHandler;
import me.moehritz.porty.internal.TeleportTimer;
import me.moehritz.porty.internal.io.IOStatics;
import me.moehritz.porty.internal.io.InputHandler;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

import java.io.File;
import java.io.IOException;

public class Porty extends Plugin {

    private static Porty instance;

    public static Porty getInstance() {
        return instance;
    }

    public static PortyAPI getApi() {
        return getInstance().api;
    }

    private PortyAPI api;
    private TeleportRequestHandler tpaHandler;
    private PortyConfiguration config;
    private TaskHandler taskHandler;
    private TeleportTimer timer;

    public TeleportRequestHandler getTpaHandler() {
        return tpaHandler;
    }

    public PortyConfiguration getConfig() {
        return config;
    }

    public TaskHandler getTaskHandler() {
        return taskHandler;
    }

    public TeleportTimer getTimer() {
        return timer;
    }

    @Override
    public void onEnable() {
        instance = this;

        this.config = new PortyConfiguration();

        File dataFolder = getDataFolder();
        if (!dataFolder.exists()) {
            dataFolder.mkdir();
        }
        File configFile = new File(dataFolder, "config.yml");
        try {
            config.load(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        File messagesFile = new File(dataFolder, "messages.yml");
        try {
            Messages.load(messagesFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.api = new IPortyAPI();
        this.tpaHandler = new ITeleportRequestHandler();
        this.taskHandler = new ITaskHandler();
        this.timer = new TeleportTimer();

        getProxy().registerChannel(IOStatics.CHANNEL);

        new InputHandler();

        registerCommands();
    }

    public void reload() {
        getConfig().reload();
        Messages.reload();
        PluginManager pm = getProxy().getPluginManager();
        pm.unregisterCommands(this);
        registerCommands();
    }

    private void registerCommands() {
        PluginManager pm = getProxy().getPluginManager();
        pm.registerCommand(this, new TeleportCommand());
        pm.registerCommand(this, new TpHereCommand());
        pm.registerCommand(this, new TpAllCommand());
        pm.registerCommand(this, new TpaCommand());
        pm.registerCommand(this, new TpaHereCommand());
        pm.registerCommand(this, new TpDenyCommand());
        pm.registerCommand(this, new TpAcceptCommand());
        pm.registerCommand(this, new PortyReloadCommand());
    }
}
