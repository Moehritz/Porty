package me.moehritz.porty.api;

import net.md_5.bungee.api.connection.ProxiedPlayer;

public interface TeleportRequestHandler
{

	public void addTpaHereRequest(ProxiedPlayer fromPlayer, ProxiedPlayer targetPlayer);

	public void addTpaRequest(ProxiedPlayer fromPlayer, ProxiedPlayer targetPlayer);

	public ProxiedPlayer getTarget(ProxiedPlayer player);

	public boolean isTpaHere(ProxiedPlayer player);

	public void deleteTeleportRequest(ProxiedPlayer player);

}
