package me.moehritz.porty.internal;

import com.google.common.base.Preconditions;

import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import me.moehritz.porty.Porty;
import me.moehritz.porty.api.CallbackRunnable;
import me.moehritz.porty.api.GlobalLocation;
import me.moehritz.porty.api.PortyAPI;
import me.moehritz.porty.api.Callback;
import me.moehritz.porty.api.TeleportRequestHandler;
import me.moehritz.porty.internal.io.IOStatics;
import me.moehritz.porty.internal.io.OutgoingPluginMessage;

public class IPortyAPI implements PortyAPI {

	@Override
	public Callback teleport(ProxiedPlayer player, ProxiedPlayer to) {
		return teleport(player, to, null);
	}

	@Override
	public Callback teleport(ProxiedPlayer player, GlobalLocation to) {
		return teleport(player, to, null);
	}

	@Override
	public Callback teleport(ProxiedPlayer player, ProxiedPlayer to, CallbackRunnable run) {
		Preconditions.checkNotNull(player);
		Preconditions.checkNotNull(to);
		
		ICallback callback = new ICallback(player, run);
		TeleportHandler.addRunningTeleport(callback);
		
		silentServerSwitch(player, to.getServer().getInfo());
		
		OutgoingPluginMessage msg = new OutgoingPluginMessage(IOStatics.TP_TO_PLAYER, to.getServer().getInfo());
		msg.setCallback(callback);
		msg.write(callback.getUniqueID());
		msg.write(player.getName());
		msg.write(to.getName());
		msg.send();
		
		return callback;
	}

	@Override
	public Callback teleport(ProxiedPlayer player, GlobalLocation to, CallbackRunnable run) {
		Preconditions.checkNotNull(player);
		Preconditions.checkNotNull(to);
		
		ICallback callback = new ICallback(player, run);
		TeleportHandler.addRunningTeleport(callback);
		
		silentServerSwitch(player, to.getServerInfo());
		
		OutgoingPluginMessage msg = new OutgoingPluginMessage(IOStatics.TP_TO_LOCATION, to.getServerInfo());
		msg.setCallback(callback);
		msg.write(callback.getUniqueID());
		msg.write(player.getName());
		msg.write(to.getServer()).write(to.getWorld()).write(to.getX()).write(to.getY()).write(to.getZ());
		msg.write(to.getYaw()).write(to.getPitch());
		msg.send();
		
		return callback;
	}

	private void silentServerSwitch(ProxiedPlayer player, ServerInfo server) {
		Preconditions.checkNotNull(player);
		Preconditions.checkNotNull(server);

		if (!player.getServer().getInfo().equals(server)) player.connect(server);

	}

	@Override
	public TeleportRequestHandler getTeleportRequestHandler()
	{
		return Porty.getInstance().getTpaHandler();
	}

}
