package me.moehritz.porty.internal;

import com.google.common.base.Preconditions;
import me.moehritz.porty.Messages;
import me.moehritz.porty.Porty;
import me.moehritz.porty.TextUtil;
import me.moehritz.porty.api.*;
import me.moehritz.porty.cmds.BasePortyCommand;
import me.moehritz.porty.internal.io.IOStatics;
import me.moehritz.porty.internal.io.OutgoingPluginMessage;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class IPortyAPI implements PortyAPI {

    @Override
    public Callback teleport(ProxiedPlayer player, ProxiedPlayer to) {
        return teleport(player, to, false);
    }

    @Override
    public Callback teleport(ProxiedPlayer player, GlobalLocation to) {
        return teleport(player, to, false);
    }

    public Callback teleport(final ProxiedPlayer player, final ProxiedPlayer to, boolean ignoreTimer) {
        Preconditions.checkNotNull(to);
        return calcTimer(player, new CallbackAcceptor() {
            @Override
            public void accpet(ICallback callback) {
                executeTeleport(player, to, callback);
            }
        }, ignoreTimer);
    }

    public Callback teleport(final ProxiedPlayer player, final GlobalLocation to, boolean ignoreTimer) {
        Preconditions.checkNotNull(to);
        return calcTimer(player, new CallbackAcceptor() {
            @Override
            public void accpet(ICallback callback) {
                executeTeleport(player, to, callback);
            }
        }, ignoreTimer);
    }

    private ICallback calcTimer(final ProxiedPlayer player, final CallbackAcceptor doTeleport, boolean ignoreTimer) {
        Preconditions.checkNotNull(player);

        int timer = 0;
        if (!ignoreTimer) {
            timer = Porty.getInstance().getTimer().getTimeToWait(player);
        }

        final ICallback callback = new ICallback(player);
        getTaskHandler().addRunningTask(callback);

        if (timer <= 0) {
            doTeleport.accpet(callback);
        } else {
            ICallback timerCallback = new ICallback(player);
            getTaskHandler().addRunningTask(timerCallback);

            OutgoingPluginMessage msg = new OutgoingPluginMessage(IOStatics.TP_TIMER, player.getServer().getInfo());
            msg.setCallback(timerCallback);
            timerCallback.setExtraTime(timer);
            msg.write(timerCallback.getUniqueID());
            msg.write(player.getName());
            msg.write(timer);
            msg.send();

            player.sendMessage(TextComponent.fromLegacyText(BasePortyCommand.PREFIX_TEXT + TextUtil.applyTag("<time>", timer + "", Messages.getMessage("teleport_cooldown", "&7Do not move for &e<time> &7seconds!"))[0]));

            timerCallback.setRunnable(new CallbackRunnable() {
                @Override
                public void success() {
                    doTeleport.accpet(callback);
                }

                @Override
                public void error(String errmsg) {
                    callback.fail(Messages.getMessage("timer_fail", "&7You moved"));
                }
            });
        }

        return callback;
    }

    private void silentServerSwitch(ProxiedPlayer player, ServerInfo server) {
        if (!player.getServer().getInfo().equals(server)) {
            player.connect(server);
        }
    }

    private void executeTeleport(ProxiedPlayer player, GlobalLocation to, ICallback callback) {
        silentServerSwitch(player, to.getServerInfo());

        OutgoingPluginMessage msg = new OutgoingPluginMessage(IOStatics.TP_TO_LOCATION, to.getServerInfo());
        msg.setCallback(callback);
        msg.write(callback.getUniqueID());
        msg.write(player.getName());
        msg.write(to.getServer()).write(to.getWorld()).write(to.getX()).write(to.getY()).write(to.getZ());
        msg.write(to.getYaw()).write(to.getPitch());
        msg.send();
    }

    private void executeTeleport(ProxiedPlayer player, ProxiedPlayer to, ICallback callback) {
        silentServerSwitch(player, to.getServer().getInfo());

        OutgoingPluginMessage msg = new OutgoingPluginMessage(IOStatics.TP_TO_PLAYER, to.getServer().getInfo());
        msg.setCallback(callback);
        msg.write(callback.getUniqueID());
        msg.write(player.getName());
        msg.write(to.getName());
        msg.send();
    }

    @Override
    public TeleportRequestHandler getTeleportRequestHandler() {
        return Porty.getInstance().getTpaHandler();
    }

    public TaskHandler getTaskHandler() {
        return Porty.getInstance().getTaskHandler();
    }

    private interface CallbackAcceptor {
        void accpet(ICallback callback);
    }

}