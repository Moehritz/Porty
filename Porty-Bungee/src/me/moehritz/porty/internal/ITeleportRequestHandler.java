package me.moehritz.porty.internal;

import me.moehritz.porty.api.TeleportRequestHandler;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.HashMap;
import java.util.Map;

public class ITeleportRequestHandler implements TeleportRequestHandler {

    private final Map<ProxiedPlayer, ProxiedPlayer> teleportRequests = new HashMap<>();
    private final Map<ProxiedPlayer, Boolean> teleportRequestHere = new HashMap<>();

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
        return teleportRequests.get(player);
    }

    public boolean isTpaHere(ProxiedPlayer player) {
        return teleportRequestHere.get(player);
    }

    public void deleteTeleportRequest(ProxiedPlayer player) {
        teleportRequests.remove(player);
        teleportRequestHere.remove(player);
    }

}
