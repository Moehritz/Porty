package me.moehritz.porty.internal;

import lombok.RequiredArgsConstructor;
import me.moehritz.porty.Porty;
import me.moehritz.porty.internal.io.CallbackSender;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class TeleportTimer {

    private final Map<Player, TeleportTimerRun> runningTimer = new HashMap<Player, TeleportTimerRun>();

    public void addTimer(int uid, Player player, int time) {
        TeleportTimerRun run = new TeleportTimerRun(uid, player);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Porty.getInstance(), run, time * 20);
        runningTimer.put(player, run);
    }

    public void removeTimer(Player player) {
        runningTimer.remove(player);
    }

    public TeleportTimerRun getTimer(Player player) {
        return runningTimer.get(player);
    }

    @RequiredArgsConstructor
    public class TeleportTimerRun implements Runnable {

        private final int uid;
        private final Player player;
        private boolean finished = false;

        @Override
        public void run() {
            if (finished) return;
            CallbackSender.sendCallback(uid, 0);
            removeTimer(player);
            finished = true;
        }

        public void cancel() {
            if (finished) return;
            CallbackSender.sendCallback(uid, 1);
            removeTimer(player);
            finished = true;
        }
    }

}
