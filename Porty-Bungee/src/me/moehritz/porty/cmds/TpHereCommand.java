package me.moehritz.porty.cmds;

import me.moehritz.porty.Messages;
import me.moehritz.porty.Porty;
import me.moehritz.porty.PortyUtil;
import me.moehritz.porty.api.Callback;
import me.moehritz.porty.api.CallbackRunnable;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class TpHereCommand extends BasePortyCommand {

    public TpHereCommand() {
        super("tphere", "porty.cmd.tphere", new String[]{"s", "tp2me"});
    }

    @Override
    public String[] getHelpText() {
        return Messages.getMessages("tphere_help");
    }

    @Override
    public void executeCommand(final CommandSender sender, String[] args) {
        if (args.length == 1) { // /tphere <player>
            if (!(sender instanceof ProxiedPlayer)) {
                sendMessage(sender, Messages.getMessage("console_warning", "&7You can not use this command as console or comandblock"));
                return;
            }

            String fromName = args[0];
            ProxiedPlayer fromPlayer = ProxyServer.getInstance().getPlayer(fromName);

            if (fromPlayer == null) {
                sendMessages(sender, PortyUtil.applyTag("<player>", fromName, Messages.getMessage("player_not_found", "&7Can´t find the player &e<player>&7.")));
                return;
            }

            ProxiedPlayer targetPlayer = (ProxiedPlayer) sender;

            Callback callback = Porty.getApi().teleport(fromPlayer, targetPlayer);
            callback.setRunnable(new CallbackRunnable() {

                @Override
                public void success() {
                    sendMessage(sender, Messages.getMessage("teleport_success", "&7Teleported."));
                }

                @Override
                public void error(String errmsg) {
                    sendMessages(sender, PortyUtil.applyTag("<errmsg>", errmsg, Messages.getMessage("teleport_fail", "&7The teleport failed: <errmsg>")));
                }
            });
        } else {
            sendWrongUsage(sender);
        }
    }
}
