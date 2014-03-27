package me.moehritz.porty.api;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class GlobalLocation {

	private double x = 0.0d, y = 0.0d, z = 0.0d;
	private float yaw = 0.0f, pitch = 0.0f;
	private String world = "", server = "";

	public GlobalLocation(double x, double y, double z, float yaw, float pitch, String world, String server) {
		setX(x);
		setY(y);
		setZ(z);
		setYaw(yaw);
		setPitch(pitch);
		setWorld(world);
		setServer(server);
	}

	public GlobalLocation(double x, double y, double z, String world, String server) {
		setX(x);
		setY(y);
		setZ(z);
		setWorld(world);
		setServer(server);
	}

	public GlobalLocation() {

	}

	public GlobalLocation(Player  player) {
		Location pLoc = player.getLocation();
		
		setX(pLoc.getX());
		setY(pLoc.getY());
		setZ(pLoc.getZ());
		
		setPitch(pLoc.getPitch());
		setYaw(pLoc.getYaw());
		
		setWorld(pLoc.getWorld().getName());
		setServer("this");
	}

	public double getX() {
		return x;
	}

	public GlobalLocation setX(double x) {
		this.x = x;
		return this;
	}

	public double getY() {
		return y;
	}

	public GlobalLocation setY(double y) {
		this.y = y;
		return this;
	}

	public double getZ() {
		return z;
	}

	public GlobalLocation setZ(double z) {
		this.z = z;
		return this;
	}

	public float getYaw() {
		return yaw;
	}

	public GlobalLocation setYaw(float yaw) {
		this.yaw = yaw;
		return this;
	}

	public float getPitch() {
		return pitch;
	}

	public GlobalLocation setPitch(float pitch) {
		this.pitch = pitch;
		return this;
	}

	public String getWorld() {
		return world;
	}

	public GlobalLocation setWorld(String world) {
		if (world == null) world = "";
		this.world = world;
		return this;
	}

	public String getServer() {
		return server;
	}

	public GlobalLocation setServer(String server) {
		if (server == null) server = "";
		this.server = server;
		return this;
	}

	public Location toBukkitLocation() {
		return new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
	}
}
