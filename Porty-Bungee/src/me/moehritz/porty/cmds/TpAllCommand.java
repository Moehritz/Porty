package me.moehritz.porty.cmds;

import java.util.Collection;

import me.moehritz.porty.Porty;
import me.moehritz.porty.api.Callback;
import me.moehritz.porty.api.CallbackRunnable;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class TpAllCommand extends BasePortyCommand
{

	public TpAllCommand()
	{
		super("tpall", "porty.cmd.tpall", new String[] { "teleportall" });
	}

	@Override
	public String[] getHelpText()
	{
		return new String[] { "Teleports all players on this BungeeCord instance", "/tpall - Teleports them to you",
				"/tp <target> - Teleports them to the given player" };
	}

	@Override
	public void executeCommand(final CommandSender sender, String[] args)
	{
		if (args.length == 0)
		{ // /tpall
			if (!(sender instanceof ProxiedPlayer))
			{
				sendMessage(sender, "Please use /tpall <target> instead!");
				return;
			}

			ProxiedPlayer targetPlayer = (ProxiedPlayer) sender;

			Collection<ProxiedPlayer> allPlayers = ProxyServer.getInstance().getPlayers();

			for (final ProxiedPlayer player : allPlayers)
			{
				if (player.equals(targetPlayer))
				{
					continue;
				}
				Callback callback = Porty.getApi().teleport(player, targetPlayer);
				callback.setRunnable(new CallbackRunnable()
				{

					@Override
					public void success()
					{
					}

					@Override
					public void error(String errmsg)
					{
						sendMessage(sender, "Failed to teleport " + BasePortyCommand.COLOR_HIGHLIGHT + player.getName()
								+ BasePortyCommand.PREFIX_TEXT + ": " + errmsg);
					}
				});
			}
			sendMessage(sender, "Teleported all players to you.");

		}
		else if (args.length == 1)
		{ // /tpall <target>
			String targetName = args[0];
			final ProxiedPlayer targetPlayer = ProxyServer.getInstance().getPlayer(targetName);

			if (targetPlayer == null)
			{
				sendMessage(sender, "Can´t find the player " + BasePortyCommand.COLOR_HIGHLIGHT + targetName
						+ BasePortyCommand.COLOR_TEXT + ".");
				return;
			}

			Collection<ProxiedPlayer> allPlayers = ProxyServer.getInstance().getPlayers();

			for (final ProxiedPlayer player : allPlayers)
			{
				if (player.equals(targetPlayer))
				{
					continue;
				}
				Callback callback = Porty.getApi().teleport(player, targetPlayer);
				callback.setRunnable(new CallbackRunnable()
				{

					@Override
					public void success()
					{
					}

					@Override
					public void error(String errmsg)
					{
						sendMessage(sender, "Failed to teleport " + BasePortyCommand.COLOR_HIGHLIGHT + player.getName()
								+ BasePortyCommand.PREFIX_TEXT + ": " + errmsg);
					}
				});
			}
			sendMessage(sender, "Teleported all players.");
		}
		else
		{
			sendWrongUsage(sender);
		}
	}

}
