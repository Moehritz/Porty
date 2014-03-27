package me.moehritz.porty.api;

import net.md_5.bungee.api.connection.ProxiedPlayer;

public interface Callback {

	public enum State {
		NOT_STARTED, WAITING, TIMEOUT, SUCCESS, ERROR, SOMETHING_WEIRD_HAPPENED
	}
	
	public State getState();
	
	public ProxiedPlayer getPlayer();
	
	public void setRunnable(CallbackRunnable run);
	
	public int getUniqueID();
}
