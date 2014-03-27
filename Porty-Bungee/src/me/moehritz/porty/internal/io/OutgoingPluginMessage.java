package me.moehritz.porty.internal.io;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import me.moehritz.porty.api.Callback;
import me.moehritz.porty.internal.ICallback;
import net.md_5.bungee.api.config.ServerInfo;

public class OutgoingPluginMessage {

	private ByteArrayOutputStream out = new ByteArrayOutputStream();
	private DataOutputStream dout = new DataOutputStream(out);

	private ServerInfo server;
	private ICallback callback;

	public OutgoingPluginMessage(byte subChannel, ServerInfo server) {
		this.server = server;
		write(subChannel);
	}

	public OutgoingPluginMessage write(String msg) {
		try {
			dout.writeUTF(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}

	public OutgoingPluginMessage write(byte msg) {
		try {
			dout.write(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}

	public OutgoingPluginMessage write(float msg) {
		try {
			dout.writeFloat(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}

	public OutgoingPluginMessage write(double msg) {
		try {
			dout.writeDouble(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}

	public OutgoingPluginMessage write(int msg) {
		try {
			dout.writeInt(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}

	public byte[] getMessage() {
		return out.toByteArray();
	}

	public void send() {
		if (server != null) {
			server.sendData(IOStatics.CHANNEL, getMessage());
			if (callback != null) {
				callback.start();
			}
		} else if (callback != null) {
			callback.fail();
		}
	}

	public Callback getCallback() {
		return callback;
	}

	public void setCallback(ICallback callback) {
		this.callback = callback;
	}

	public ServerInfo getServer() {
		return server;
	}

	public void setServer(ServerInfo server) {
		if (server != null) {
			this.server = server;
		}
	}

}
