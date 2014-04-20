package me.moehritz.porty.internal;

import com.google.common.base.Preconditions;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import me.moehritz.porty.Porty;
import me.moehritz.porty.api.CallbackRunnable;
import me.moehritz.porty.api.GlobalLocation;
import me.moehritz.porty.api.PortyAPI;
import me.moehritz.porty.api.Callback;
import me.moehritz.porty.api.TaskHandler;
import me.moehritz.porty.api.TeleportRequestHandler;
import me.moehritz.porty.cmds.BasePortyCommand;
import me.moehritz.porty.internal.io.IOStatics;
import me.moehritz.porty.internal.io.OutgoingPluginMessage;

public class IPortyAPI implements PortyAPI
{

	@Override
	public Callback teleport(ProxiedPlayer player, ProxiedPlayer to)
	{
		return teleport(player, to, false);
	}

	@Override
	public Callback teleport(ProxiedPlayer player, GlobalLocation to)
	{
		return teleport(player, to, false);
	}

	public Callback teleport(final ProxiedPlayer player, final ProxiedPlayer to, boolean ignoreTimer)
	{
		Preconditions.checkNotNull(player);
		Preconditions.checkNotNull(to);

		int timer = 0;
		if (!ignoreTimer)
		{
			timer = Porty.getInstance().getTimer().getTimeToWait(player);
		}

		final ICallback callback = new ICallback(player);
		getTaskHandler().addRunningTask(callback);
		if (timer <= 0 || ignoreTimer)
		{

			silentServerSwitch(player, to.getServer().getInfo());

			OutgoingPluginMessage msg = new OutgoingPluginMessage(IOStatics.TP_TO_PLAYER, to.getServer().getInfo());
			msg.setCallback(callback);
			msg.write(callback.getUniqueID());
			writeTeleport(player, to, msg);
			msg.send();
		}
		else
		{
			ICallback timerCallback = new ICallback(player);
			getTaskHandler().addRunningTask(timerCallback);
			
			OutgoingPluginMessage msg = new OutgoingPluginMessage(IOStatics.TP_TIMER, player.getServer().getInfo());
			msg.setCallback(timerCallback);
			timerCallback.setExtraTime(timer);
			msg.write(timerCallback.getUniqueID());
			msg.write(player.getName());
			msg.write(timer);
			msg.send();

			player.sendMessage(TextComponent.fromLegacyText(BasePortyCommand.PREFIX_MAIN + "Don´t move for "
					+ BasePortyCommand.COLOR_HIGHLIGHT + timer + BasePortyCommand.COLOR_TEXT + " seconds!"));

			timerCallback.setRunnable(new CallbackRunnable()
			{

				@Override
				public void success()
				{
					silentServerSwitch(player, to.getServer().getInfo());

					OutgoingPluginMessage msg = new OutgoingPluginMessage(IOStatics.TP_TO_PLAYER, to.getServer()
							.getInfo());
					msg.setCallback(callback);
					msg.write(callback.getUniqueID());
					writeTeleport(player, to, msg);
					msg.send();
				}

				@Override
				public void error(String errmsg)
				{
					callback.fail("You moved");
				}
			});
		}

		return callback;
	}

	public Callback teleport(final ProxiedPlayer player, final GlobalLocation to, boolean ignoreTimer)
	{
		Preconditions.checkNotNull(player);
		Preconditions.checkNotNull(to);

		int timer = 0;
		if (!ignoreTimer)
		{
			timer = Porty.getInstance().getTimer().getTimeToWait(player);
		}

		final ICallback callback = new ICallback(player);
		getTaskHandler().addRunningTask(callback);

		if (timer <= 0 || ignoreTimer)
		{
			silentServerSwitch(player, to.getServerInfo());

			OutgoingPluginMessage msg = new OutgoingPluginMessage(IOStatics.TP_TO_LOCATION, to.getServerInfo());
			msg.setCallback(callback);
			msg.write(callback.getUniqueID());
			writeTeleport(player, to, msg);
			msg.send();
		}
		else
		{
			ICallback timerCallback = new ICallback(player);
			getTaskHandler().addRunningTask(timerCallback);
			
			OutgoingPluginMessage msg = new OutgoingPluginMessage(IOStatics.TP_TIMER, player.getServer().getInfo());
			msg.setCallback(timerCallback);
			timerCallback.setExtraTime(timer);
			msg.write(timerCallback.getUniqueID());
			msg.write(player.getName());
			msg.write(timer);
			msg.send();

			player.sendMessage(TextComponent.fromLegacyText(BasePortyCommand.PREFIX_MAIN + "Don´t move for "
					+ BasePortyCommand.COLOR_HIGHLIGHT + timer + BasePortyCommand.COLOR_TEXT + " seconds!"));

			timerCallback.setRunnable(new CallbackRunnable()
			{

				@Override
				public void success()
				{
					silentServerSwitch(player, to.getServerInfo());

					OutgoingPluginMessage msg = new OutgoingPluginMessage(IOStatics.TP_TO_LOCATION, to.getServerInfo());
					msg.setCallback(callback);
					msg.write(callback.getUniqueID());
					writeTeleport(player, to, msg);
					msg.send();
				}

				@Override
				public void error(String errmsg)
				{
					callback.fail("You moved");
				}
			});
		}

		return callback;
	}

	private void silentServerSwitch(ProxiedPlayer player, ServerInfo server)
	{
		if (!player.getServer().getInfo().equals(server))
		{
			player.connect(server);
		}
	}

	private void writeTeleport(ProxiedPlayer player, GlobalLocation to, OutgoingPluginMessage msg)
	{
		msg.write(player.getName());
		msg.write(to.getServer()).write(to.getWorld()).write(to.getX()).write(to.getY()).write(to.getZ());
		msg.write(to.getYaw()).write(to.getPitch());
	}

	private void writeTeleport(ProxiedPlayer player, ProxiedPlayer to, OutgoingPluginMessage msg)
	{
		msg.write(player.getName());
		msg.write(to.getName());
	}

	@Override
	public TeleportRequestHandler getTeleportRequestHandler()
	{
		return Porty.getInstance().getTpaHandler();
	}

	public TaskHandler getTaskHandler()
	{
		return Porty.getInstance().getTaskHandler();
	}

}
