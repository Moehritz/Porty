package me.moehritz.porty.api;

import net.md_5.bungee.api.connection.ProxiedPlayer;

public interface PortyAPI {

	public Callback teleport(ProxiedPlayer from, ProxiedPlayer to);

	public Callback teleport(ProxiedPlayer from, GlobalLocation to);
	
	public Callback teleport(ProxiedPlayer from, ProxiedPlayer to, CallbackRunnable run);

	public Callback teleport(ProxiedPlayer from, GlobalLocation to, CallbackRunnable run);
	
	public TeleportRequestHandler getTeleportRequestHandler();
	
}
