package me.moehritz.porty.internal;

import me.moehritz.porty.Porty;
import me.moehritz.porty.PortyConfiguration;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class TeleportTimer {

    public int getTimeToWait(ProxiedPlayer player) {
        String server = player.getServer().getInfo().getName();
        if (player.hasPermission("porty.notimer") || player.hasPermission("porty.notimer." + server)) {
            return 0;
        }
        PortyConfiguration cfg = Porty.getInstance().getConfig();
        int ret = cfg.getGlobalTeleportTimer();
        int srv = cfg.getServerTeleportTimer().get(server);
        if (srv != -1) {
            ret = srv;
        }
        return ret;
    }
}
