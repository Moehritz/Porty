package me.moehritz.porty.internal;

import java.util.concurrent.TimeUnit;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.scheduler.ScheduledTask;
import lombok.Setter;
import me.moehritz.porty.Porty;
import me.moehritz.porty.api.Callback;
import me.moehritz.porty.api.CallbackRunnable;

public class ICallback implements Callback {

	private static long timeoutInSeconds = 5;

	private ProxiedPlayer player;
	private State state;
	private CallbackRunnable runnnable;
	private ScheduledTask timeoutCheck;
	private int uid;
	@Setter
	private int extraTime;

	public ICallback(ProxiedPlayer player) {
		this(player, null);
	}

	public ICallback(ProxiedPlayer player, CallbackRunnable run) {
		this.player = player;
		this.runnnable = run;
		setUniqueID();
	}

	private void setUniqueID() {
		this.uid = TeleportHandler.requestNewID();
	}

	@Override
	public State getState() {
		return state;
	}

	@Override
	public ProxiedPlayer getPlayer() {
		return player;
	}

	public void timeout() {
		state = State.TIMEOUT;
		run(false);
	}

	public void success() {
		state = State.SUCCESS;
		run(true);
	}

	public void fail() {
		state = State.ERROR;
		run(false);
	}

	public void start() {
		state = State.WAITING;
		timeoutCheck = ProxyServer.getInstance().getScheduler().schedule(Porty.getInstance(), new Runnable() {
			@Override
			public void run() {
				timeout();
			}
		}, timeoutInSeconds, TimeUnit.SECONDS);
		}, Porty.getInstance().getConfig().getTimeout() + extraTime, TimeUnit.SECONDS);
	}

	public void weirdThingsAreGoingOn() {
		state = State.SOMETHING_WEIRD_HAPPENED;
		run(false);
	}

	private void run(boolean success) {
		timeoutCheck.cancel();
		
		if (runnnable != null) {
			if (success) {
				runnnable.success();
			} else {
				String errmsg = "Unknown Error.";
				switch (state) {
				case ERROR:
					errmsg = "Teleport failed.";
					break;
				case SOMETHING_WEIRD_HAPPENED:
					errmsg = "Something really weird happened to this action. Please contact the dev.";
					break;
				case TIMEOUT:
					errmsg = "The Pluginmessage to one of your Bukkit servers timed out.";
					break;
				default:
					break;
				}
				runnnable.error(errmsg);
			}
		}
	}

	@Override
	public void setRunnable(CallbackRunnable run) {
		this.runnnable = run;
	}

	@Override
	public int getUniqueID() {
		return uid;
	}
}