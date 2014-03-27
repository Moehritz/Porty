package me.moehritz.porty.internal.io;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import me.moehritz.porty.Porty;
import me.moehritz.porty.internal.ICallback;
import me.moehritz.porty.internal.TeleportHandler;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class InputHandler implements Listener {
	
	public InputHandler() {
		ProxyServer.getInstance().getPluginManager().registerListener(Porty.getInstance(), this);
	}

	@EventHandler
	public void onPluginMessageReceive(PluginMessageEvent input) {
		if (!input.getTag().equals(IOStatics.CHANNEL)) return;

		DataInputStream in = new DataInputStream(new ByteArrayInputStream(input.getData()));

		try {
			byte type = in.readByte();
			switch (type) {
			case IOStatics.CALLBACK:
				callback(in);
				break;
			default:
				break;
			}

			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void callback(DataInputStream in) throws IOException {
		int uid = in.readInt();
		ICallback callback = (ICallback) TeleportHandler.getRunningTeleport(uid);
		if (callback == null) return;

		byte result = in.readByte();
		switch (result) {
		case 0:
			callback.success();
			break;
		case 1:
			callback.fail();
			break;
		default:
			callback.weirdThingsAreGoingOn();
		}
	}
}
