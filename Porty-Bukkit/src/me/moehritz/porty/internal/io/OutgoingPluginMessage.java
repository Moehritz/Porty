package me.moehritz.porty.internal.io;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class OutgoingPluginMessage {

	private ByteArrayOutputStream out = new ByteArrayOutputStream();
	private DataOutputStream dout = new DataOutputStream(out);

	public OutgoingPluginMessage(byte subChannel) {
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
}
