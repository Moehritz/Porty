package me.moehritz.porty.internal.io;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import me.moehritz.porty.Porty;
import me.moehritz.porty.api.GlobalLocation;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class InputHandler implements PluginMessageListener
{

	@Override
	public void onPluginMessageReceived(String ch, Player p, byte[] msg)
	{
		if (!ch.equals(IOStatics.CHANNEL)) return;

		DataInputStream in = new DataInputStream(new ByteArrayInputStream(msg));

		try
		{
			byte type = in.readByte();
			int uid = in.readInt();
			switch (type)
			{
			case IOStatics.CALLBACK:
				// Impossible atm
				break;
			case IOStatics.TP_TO_LOCATION:
				prepareLocationTeleport(uid, in);
				break;
			case IOStatics.TP_TO_PLAYER:
				preparePlayerTeleport(uid, in);
				break;
			case IOStatics.TP_TIMER:
				teleportTimer(uid, in);
				break;
			}

			in.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private void prepareLocationTeleport(int uid, DataInputStream data) throws IOException
	{
		String p = data.readUTF();
		String server = data.readUTF();
		String world = data.readUTF();

		double x = data.readDouble();
		double y = data.readDouble();
		double z = data.readDouble();

		float yaw = data.readFloat();
		float pitch = data.readFloat();

		GlobalLocation loc = new GlobalLocation(x, y, z, yaw, pitch, world, server);

		OfflinePlayer player = Bukkit.getOfflinePlayer(p);

		Porty.getInstance().getTeleportScheduler().scheduleTeleport(player, loc, uid);
	}

	private void preparePlayerTeleport(int uid, DataInputStream data) throws IOException
	{
		String p = data.readUTF();
		String t = data.readUTF();

		Player target = Bukkit.getPlayer(t);

		if (target == null)
		{
			CallbackSender.sendCallback(uid, 1);
			return;
		}

		GlobalLocation loc = new GlobalLocation(target);

		OfflinePlayer player = Bukkit.getOfflinePlayer(p);

		Porty.getInstance().getTeleportScheduler().scheduleTeleport(player, loc, uid);
	}

	private void teleportTimer(int uid, DataInputStream data) throws IOException
	{
		String p = data.readUTF();
		int time = data.readInt();

		Player player = Bukkit.getPlayer(p);

		if (player == null)
		{
			CallbackSender.sendCallback(uid, 1);
			return;
		}
		Porty.getInstance().getTeleportTimer().addTimer(uid, player, time);

	}
}
