package me.moehritz.porty.cmds;

import me.moehritz.porty.Porty;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class TpaCommand extends BasePortyCommand
{

	public TpaCommand()
	{
		super("tpa", "porty.cmd.tpa", new String[] { "tpask" });
	}

	@Override
	public String[] getHelpText()
	{
		return new String[] { "Asks a player to teleport to him",
				"/tpa <player> - Sends the request, it can be accepted with /tpaccept or denied with /tpdeny" };
	}

	@Override
	public void executeCommand(CommandSender sender, String[] args)
	{
		if (!(sender instanceof ProxiedPlayer))
		{
			sendMessage(sender, "You cannot use the command as console!");
			return;
		}

		if (args.length == 1)
		{
			ProxiedPlayer fromPlayer = (ProxiedPlayer) sender;

			String targetName = args[0];
			ProxiedPlayer targetPlayer = ProxyServer.getInstance().getPlayer(targetName);

			if (targetPlayer == null)
			{
				sendMessage(sender, "Can´t find the player " + BasePortyCommand.COLOR_HIGHLIGHT + targetName
						+ BasePortyCommand.COLOR_TEXT + ".");
				return;
			}

			Porty.getApi().getTeleportRequestHandler().addTpaRequest(fromPlayer, targetPlayer);
			sendMessage(fromPlayer, "Your request has been sent.");
			sendMessage(targetPlayer, "The player " + BasePortyCommand.COLOR_HIGHLIGHT + fromPlayer.getDisplayName()
					+ BasePortyCommand.COLOR_TEXT
					+ " asks you to teleport to you. Use /tpaccept and /tpdeny to respond to it.");
		}
		else
		{
			sendWrongUsage(sender);
		}
	}
}
