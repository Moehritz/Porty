package me.moehritz.porty.internal;

import java.util.HashMap;
import java.util.Map;

import me.moehritz.porty.Porty;
import me.moehritz.porty.api.GlobalLocation;
import me.moehritz.porty.internal.io.CallbackSender;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class TeleportScheduler implements Listener {

	private Map<OfflinePlayer, GlobalLocation> scheduledTeleports = new HashMap<OfflinePlayer, GlobalLocation>();
	private Map<OfflinePlayer, Integer> scheduledTeleportUids = new HashMap<OfflinePlayer, Integer>();

	public TeleportScheduler() {
		Bukkit.getPluginManager().registerEvents(this, Porty.getInstance());
	}

	public void scheduleTeleport(OfflinePlayer player, GlobalLocation loc, int uid) {
		if (player.isOnline()) {
			player.getPlayer().teleport(loc.toBukkitLocation());
			
			CallbackSender.sendCallback(uid, 0);
			return;
		}

		if (scheduledTeleports.containsKey(player)) {
			scheduledTeleports.remove(player);
			scheduledTeleportUids.remove(player);
		}

		scheduledTeleports.put(player, loc);
		scheduledTeleportUids.put(player, uid);
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if (scheduledTeleports.containsKey(player)) {
			try {
				GlobalLocation loc = scheduledTeleports.get(player);

				event.getPlayer().teleport(loc.toBukkitLocation());

				CallbackSender.sendCallback(scheduledTeleportUids.get(player), 0);
			} catch (Exception ex) {
				// Occurs if the target World is null
				CallbackSender.sendCallback(scheduledTeleportUids.get(player), 1);
			}

			scheduledTeleports.remove(player);
			scheduledTeleportUids.remove(player);
		}
	}
}