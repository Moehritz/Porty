package me.moehritz.porty.api;

import net.md_5.bungee.api.connection.ProxiedPlayer;

public interface PortyAPI
{

	public Callback teleport(ProxiedPlayer player, ProxiedPlayer to);

	public Callback teleport(ProxiedPlayer player, GlobalLocation to);

	public Callback teleport(ProxiedPlayer player, ProxiedPlayer to, boolean ignoreTimer);

	public Callback teleport(ProxiedPlayer player, GlobalLocation to, boolean ignoreTimer);

	public TeleportRequestHandler getTeleportRequestHandler();

	public TaskHandler getTaskHandler();

}
