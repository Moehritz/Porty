package me.moehritz.porty.internal;

import me.moehritz.porty.Porty;
import me.moehritz.porty.internal.TeleportTimer.TeleportTimerRun;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerListener implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        TeleportTimerRun run = Porty.getInstance().getTeleportTimer().getTimer(e.getPlayer());
        if (run != null) {
            if (e.getFrom().getX() != e.getTo().getX() || e.getFrom().getY() != e.getTo().getY() || e.getFrom().getZ() != e.getTo().getZ()) {
                run.cancel();
            }
        }
    }
}
