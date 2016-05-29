package me.moehritz.porty.api;

import net.md_5.bungee.api.connection.ProxiedPlayer;

public interface TeleportRequestHandler {

    void addTpaHereRequest(ProxiedPlayer fromPlayer, ProxiedPlayer targetPlayer);

    void addTpaRequest(ProxiedPlayer fromPlayer, ProxiedPlayer targetPlayer);

    ProxiedPlayer getTarget(ProxiedPlayer player);

    boolean isTpaHere(ProxiedPlayer player);

    void deleteTeleportRequest(ProxiedPlayer player);

}
