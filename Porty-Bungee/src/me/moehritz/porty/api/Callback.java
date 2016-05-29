package me.moehritz.porty.api;

import net.md_5.bungee.api.connection.ProxiedPlayer;

public interface Callback {

    enum State {
        NOT_STARTED, WAITING, TIMEOUT, SUCCESS, ERROR, SOMETHING_WEIRD_HAPPENED
    }

    State getState();

    ProxiedPlayer getPlayer();

    void setRunnable(CallbackRunnable run);

    int getUniqueID();
}
