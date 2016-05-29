package me.moehritz.porty;

import lombok.Getter;
import me.moehritz.porty.internal.PlayerListener;
import me.moehritz.porty.internal.TeleportScheduler;
import me.moehritz.porty.internal.TeleportTimer;
import me.moehritz.porty.internal.io.IOStatics;
import me.moehritz.porty.internal.io.InputHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class Porty extends JavaPlugin {

    private static Porty instance;

    public static Porty getInstance() {
        return instance;
    }

    @Getter
    private TeleportScheduler teleportScheduler;
    @Getter
    private TeleportTimer teleportTimer;

    @Override
    public void onEnable() {
        instance = this;

        getServer().getMessenger().registerIncomingPluginChannel(this, IOStatics.CHANNEL, new InputHandler());
        getServer().getMessenger().registerOutgoingPluginChannel(this, IOStatics.CHANNEL);

        teleportScheduler = new TeleportScheduler();
        teleportTimer = new TeleportTimer();

        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
    }

}
