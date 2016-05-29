package me.moehritz.porty.api;

public interface TaskHandler {

    int requestNewID();

    void addRunningTask(Callback callback);

    boolean removeRunningTask(Callback callback);

    Callback getRunningTask(int uid);
}
