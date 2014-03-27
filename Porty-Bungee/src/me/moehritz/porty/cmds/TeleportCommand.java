package me.moehritz.porty.cmds;

import me.moehritz.porty.Porty;
import me.moehritz.porty.api.Callback;
import me.moehritz.porty.api.CallbackRunnable;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class TeleportCommand extends BasePortyCommand {

	public TeleportCommand() {
		super("teleport", "porty.cmd.teleport", new String[] { "tp", "tp2p" });
	}

	@Override
	public String[] getHelpText() {
		return new String[] { "Teleports a player to a player", "/tp <target> - Teleports the sender",
				"/tp <player> <target> - Teleports the given player" };
	}

	@Override
	public void executeCommand(final CommandSender sender, String[] args) {
		if (args.length == 1) { // /tp <target>
			if (!(sender instanceof ProxiedPlayer)) {
				sendMessage(sender, "As console, you can teleport other players with /tp <player> <target>");
				return;
			}

			ProxiedPlayer fromPlayer = (ProxiedPlayer) sender;

			String targetName = args[0];
			ProxiedPlayer targetPlayer = ProxyServer.getInstance().getPlayer(targetName);

			if (targetPlayer == null) {
				sendMessage(sender, "Can´t find the player " + BasePortyCommand.COLOR_HIGHLIGHT + targetName + BasePortyCommand.COLOR_TEXT + ".");
				return;
			}

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
		} else if (args.length == 2) { // /tp <player> <target>
			String fromName = args[0];
			final ProxiedPlayer fromPlayer = ProxyServer.getInstance().getPlayer(fromName);

			if (fromPlayer == null) {
				sendMessage(sender, "Can´t find the player " + BasePortyCommand.COLOR_HIGHLIGHT + fromName + BasePortyCommand.COLOR_TEXT + ".");
				return;
			}

			String targetName = args[0];
			final ProxiedPlayer targetPlayer = ProxyServer.getInstance().getPlayer(fromName);

			if (targetPlayer == null) {
				sendMessage(sender, "Can´t find the player " + BasePortyCommand.COLOR_HIGHLIGHT + targetName + BasePortyCommand.COLOR_TEXT + ".");
				return;
			}

			Callback callback = Porty.getApi().teleport(fromPlayer, targetPlayer);
			callback.setRunnable(new CallbackRunnable() {

				@Override
				public void success() {
					sendMessage(fromPlayer, "Teleported.");
					sendMessage(sender, "Teleported player " + fromPlayer.getDisplayName() + " to " + targetPlayer.getDisplayName() + ".");
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
