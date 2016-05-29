package me.moehritz.porty.api;

import net.md_5.bungee.api.connection.ProxiedPlayer;

public interface PortyAPI {

    Callback teleport(ProxiedPlayer player, ProxiedPlayer to);

    Callback teleport(ProxiedPlayer player, GlobalLocation to);

    Callback teleport(ProxiedPlayer player, ProxiedPlayer to, boolean ignoreTimer);

    Callback teleport(ProxiedPlayer player, GlobalLocation to, boolean ignoreTimer);

    TeleportRequestHandler getTeleportRequestHandler();

    TaskHandler getTaskHandler();

}
