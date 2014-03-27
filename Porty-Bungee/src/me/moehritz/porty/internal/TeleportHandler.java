package me.moehritz.porty.internal;

import java.util.List;
import java.util.Vector;

import me.moehritz.porty.api.Callback;

public class TeleportHandler {

	private static int tpId = Integer.MAX_VALUE;
	private static List<Callback> runningTeleports = new Vector<>();
	
	public static int requestNewID() {
		int ret = tpId;
		tpId--;
		return ret;
	}
	
	public static void addRunningTeleport(Callback callback) {
		if (callback != null) runningTeleports.add(callback);
	}
	
	public static boolean removeRunningTeleport(Callback callback) {
		return runningTeleports.remove(callback);
	}
	
	public static Callback getRunningTeleport(int uid) {
		for (Callback cb : runningTeleports) {
			if (cb.getUniqueID() == uid) return cb;
		}
		return null;
	}
}
