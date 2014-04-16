package me.moehritz.porty.internal;

import java.util.HashMap;
import java.util.Map;

import me.moehritz.porty.api.TeleportRequestHandler;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class ITeleportRequestHandler implements TeleportRequestHandler {

	private Map<ProxiedPlayer, ProxiedPlayer> teleportRequests = new HashMap<>();
	private Map<ProxiedPlayer, Boolean> teleportRequestHere = new HashMap<>();

	public void addTpaHereRequest(ProxiedPlayer sender, ProxiedPlayer target) {
		addTeleportRequest(target, sender, true);
	}

	public void addTpaRequest(ProxiedPlayer sender, ProxiedPlayer target) {
		addTeleportRequest(target, sender, false);
	}

	private void addTeleportRequest(ProxiedPlayer a, ProxiedPlayer b, boolean here) {
		deleteTeleportRequest(a);

		teleportRequests.put(a, b);
		teleportRequestHere.put(a, here);
	}

	public ProxiedPlayer getTarget(ProxiedPlayer player) {
		ProxiedPlayer ret = teleportRequests.get(player);
		return ret;
	}

	public boolean isTpaHere(ProxiedPlayer player) {
		boolean ret = teleportRequestHere.get(player);
		return ret;
	}

	public void deleteTeleportRequest(ProxiedPlayer player) {
		teleportRequests.remove(player);
		teleportRequestHere.remove(player);
	}

}
