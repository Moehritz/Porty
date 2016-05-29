package me.moehritz.porty.cmds;

import me.moehritz.porty.Messages;
import me.moehritz.porty.Porty;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class TpDenyCommand extends BasePortyCommand {

    public TpDenyCommand() {
        super("tpdeny", "porty.cmd.tpdeny", new String[]{});
    }

    @Override
    public String[] getHelpText() {
        return Messages.getMessages("tpdeny_help");
    }

    @Override
    public void executeCommand(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            sendMessage(sender, Messages.getMessage("console_warning", "&7You can not use this command as console or comandblocks"));
            return;
        }
        if (args.length == 0) {
            ProxiedPlayer fromPlayer = (ProxiedPlayer) sender;

            ProxiedPlayer targetPlayer = Porty.getApi().getTeleportRequestHandler().getTarget(fromPlayer);
            if (targetPlayer == null) {
                sendMessage(fromPlayer, Messages.getMessage("tpa_no_requests", "&7You don´t have any open requests!"));
                return;
            }

            Porty.getApi().getTeleportRequestHandler().deleteTeleportRequest(fromPlayer);
            String msg = Messages.getMessage("tpdeny", "&7Request has been cancelled");
            sendMessage(targetPlayer, msg);
            sendMessage(fromPlayer, msg);
        } else {
            sendWrongUsage(sender);
        }
    }

}
