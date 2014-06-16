package me.moehritz.porty.cmds;

import me.moehritz.porty.Messages;
import me.moehritz.porty.Porty;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class TpAcceptCommand extends BasePortyCommand
{

	public TpAcceptCommand()
	{
		super("tpaccept", "porty.cmd.tpaccept", new String[] {});
	}

	@Override
	public String[] getHelpText()
	{
		return Messages.getMessages("tpaccept_help");
	}

	@Override
	public void executeCommand(CommandSender sender, String[] args)
	{
		if (!(sender instanceof ProxiedPlayer))
		{
			sendMessage(sender, Messages.getMessage("console_warning", "&7You can not use this command as console or comandblocks"));
			return;
		}
		if (args.length == 0)
		{
			ProxiedPlayer fromPlayer = (ProxiedPlayer) sender;

			ProxiedPlayer targetPlayer = Porty.getApi().getTeleportRequestHandler().getTarget(fromPlayer);
			if (targetPlayer == null)
			{
				sendMessage(fromPlayer, Messages.getMessage("tpa_no_requests", "&7You don´t have any open requests!"));
				return;
			}

			boolean here = Porty.getApi().getTeleportRequestHandler().isTpaHere(fromPlayer);
			if (here)
			{
				Porty.getApi().teleport(fromPlayer, targetPlayer);
			}
			else
			{
				Porty.getApi().teleport(targetPlayer, fromPlayer);
			}

			Porty.getApi().getTeleportRequestHandler().deleteTeleportRequest(fromPlayer);
			sendMessage(targetPlayer, Messages.getMessage("tpaccept", "&7Your request has been accepted!"));
		}
		else
		{
			sendWrongUsage(sender);
		}
	}

}
