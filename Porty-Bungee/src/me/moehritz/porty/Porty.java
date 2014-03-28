package me.moehritz.porty;

import me.moehritz.porty.api.PortyAPI;
import me.moehritz.porty.cmds.TeleportCommand;
import me.moehritz.porty.cmds.TpAllCommand;
import me.moehritz.porty.cmds.TpHereCommand;
import me.moehritz.porty.internal.IPortyAPI;
import me.moehritz.porty.internal.io.IOStatics;
import me.moehritz.porty.internal.io.InputHandler;
import net.md_5.bungee.api.plugin.Plugin;

public class Porty extends Plugin {

	private static Porty instance;

	public static Porty getInstance() {
		return instance;
	}
	
	public static PortyAPI getApi() {
		return getInstance().api;
	}

	private PortyAPI api;

	@Override
	public void onEnable() {
		instance = this;
		
		this.api = new IPortyAPI();
		
		getProxy().registerChannel(IOStatics.CHANNEL);
		
		new InputHandler();
		
		getProxy().getPluginManager().registerCommand(this, new TeleportCommand());
		getProxy().getPluginManager().registerCommand(this, new TpHereCommand());
		getProxy().getPluginManager().registerCommand(this, new TpAllCommand());
	}

}
