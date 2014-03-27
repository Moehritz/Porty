package me.moehritz.porty.cmds;

import me.moehritz.porty.Porty;
import me.moehritz.porty.api.Callback;
import me.moehritz.porty.api.CallbackRunnable;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class TpHereCommand extends BasePortyCommand {

	public TpHereCommand() {
		super("tphere", "porty.cmd.tphere", new String[] { "s", "tp2me" });
	}

	@Override
	public String[] getHelpText() {
		return new String[] { "Teleports a player to you", "/tphere <target> - Teleports the player to you" };
	}

	@Override
	public void executeCommand(final CommandSender sender, String[] args) {
		if (args.length == 1) { // /tphere <player>
			if (!(sender instanceof ProxiedPlayer)) {
				sendMessage(sender, "You can´t teleport players to the console! Use /tp <player> <target> instead");
				return;
			}

			String fromName = args[0];
			ProxiedPlayer fromPlayer = ProxyServer.getInstance().getPlayer(fromName);

			if (fromPlayer == null) {
				sendMessage(sender, "Can´t find the player " + BasePortyCommand.COLOR_HIGHLIGHT + fromName + BasePortyCommand.COLOR_TEXT + ".");
				return;
			}

			ProxiedPlayer targetPlayer = (ProxiedPlayer) sender;

			Callback callback = Porty.getApi().teleport(fromPlayer, targetPlayer);
			callback.setRunnable(new CallbackRunnable() {

				@Override
				public void success() {
					sendMessage(sender, "Teleported.");
				}

				@Override
				public void error(String errmsg) {
					sendMessage(sender, "The teleport failed: " + errmsg);
				}
			});
		} else {
			sendWrongUsage(sender);
		}
	}
}
