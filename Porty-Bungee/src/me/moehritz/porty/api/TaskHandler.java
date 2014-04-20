package me.moehritz.porty.api;

public interface TaskHandler
{

	public int requestNewID();

	public void addRunningTask(Callback callback);

	public boolean removeRunningTask(Callback callback);

	public Callback getRunningTask(int uid);
}
