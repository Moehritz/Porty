package me.moehritz.porty.cmds;

import me.moehritz.porty.Messages;
import me.moehritz.porty.Porty;
import me.moehritz.porty.PortyUtil;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class TpaHereCommand extends BasePortyCommand {

    public TpaHereCommand() {
        super("tpahere", "porty.cmd.tpahere", new String[]{"tpaskhere"});
    }

    @Override
    public String[] getHelpText() {
        return Messages.getMessages("tpahere_help");
    }

    @Override
    public void executeCommand(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            sendMessage(sender, Messages.getMessage("console_warning", "&7You can not use this command as console or comandblocks"));
            return;
        }

        if (args.length == 1) {
            ProxiedPlayer fromPlayer = (ProxiedPlayer) sender;

            String targetName = args[0];
            ProxiedPlayer targetPlayer = ProxyServer.getInstance().getPlayer(targetName);

            if (targetPlayer == null) {
                sendMessages(sender, PortyUtil.applyTag("<player>", targetName, Messages.getMessage("player_not_found", "&7Can´t find the player &e<player>&7.")));
                return;
            }

            Porty.getApi().getTeleportRequestHandler().addTpaHereRequest(fromPlayer, targetPlayer);
            sendMessage(fromPlayer, Messages.getMessage("tpa_request_sent", "Your request has been sent"));
            sendMessages(targetPlayer, PortyUtil.applyTag("<player>", fromPlayer.getName(), Messages.getMessage("tpahere", "&7The player &e<player> &7asks you to teleport to him. Use &e/tpaccept&7 or &e/tpdeny&7 in order to respond to it.")));

        } else {
            sendWrongUsage(sender);
        }
    }
}
