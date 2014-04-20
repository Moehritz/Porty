package me.moehritz.porty.internal.io;

import me.moehritz.porty.Porty;

import org.bukkit.Bukkit;

public class CallbackSender
{

	public static void sendCallback(int uid, int ret)
	{
		OutgoingPluginMessage out = new OutgoingPluginMessage(IOStatics.CALLBACK);

		out.write(uid);
		out.write(ret);

		Bukkit.getOnlinePlayers()[0].sendPluginMessage(Porty.getInstance(), IOStatics.CHANNEL, out.getMessage());
	}
}
