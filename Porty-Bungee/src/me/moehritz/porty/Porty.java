package me.moehritz.porty;

import java.io.File;
import java.io.IOException;

import lombok.Getter;
import me.moehritz.porty.api.PortyAPI;
import me.moehritz.porty.api.TeleportRequestHandler;
import me.moehritz.porty.cmds.TeleportCommand;
import me.moehritz.porty.cmds.TpAcceptCommand;
import me.moehritz.porty.cmds.TpAllCommand;
import me.moehritz.porty.cmds.TpDenyCommand;
import me.moehritz.porty.cmds.TpHereCommand;
import me.moehritz.porty.cmds.TpaCommand;
import me.moehritz.porty.cmds.TpaHereCommand;
import me.moehritz.porty.internal.IPortyAPI;
import me.moehritz.porty.internal.ITeleportRequestHandler;
import me.moehritz.porty.internal.io.IOStatics;
import me.moehritz.porty.internal.io.InputHandler;
import net.md_5.bungee.api.plugin.Plugin;

public class Porty extends Plugin
{

	private static Porty instance;

	public static Porty getInstance()
	{
		return instance;
	}

	public static PortyAPI getApi()
	{
		return getInstance().api;
	}

	private PortyAPI api;
	@Getter
	private TeleportRequestHandler tpaHandler;
	@Getter
	private PortyConfiguration config;

	@Override
	public void onEnable()
	{
		instance = this;

		config = new PortyConfiguration();
		File dataFolder = getDataFolder();
		if (!dataFolder.exists())
		{
			dataFolder.mkdir();
		}
		File configFile = new File(dataFolder, "config.yml");
		try
		{
			config.load(configFile);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		this.api = new IPortyAPI();
		tpaHandler = new ITeleportRequestHandler();

		getProxy().registerChannel(IOStatics.CHANNEL);

		new InputHandler();

		getProxy().getPluginManager().registerCommand(this, new TeleportCommand());
		getProxy().getPluginManager().registerCommand(this, new TpHereCommand());
		getProxy().getPluginManager().registerCommand(this, new TpAllCommand());
		getProxy().getPluginManager().registerCommand(this, new TpaCommand());
		getProxy().getPluginManager().registerCommand(this, new TpaHereCommand());
		getProxy().getPluginManager().registerCommand(this, new TpDenyCommand());
		getProxy().getPluginManager().registerCommand(this, new TpAcceptCommand());
	}

}
