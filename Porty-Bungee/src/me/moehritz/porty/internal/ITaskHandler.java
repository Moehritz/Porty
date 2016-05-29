package me.moehritz.porty.internal;

import me.moehritz.porty.api.Callback;
import me.moehritz.porty.api.TaskHandler;

import java.util.ArrayList;
import java.util.List;

public class ITaskHandler implements TaskHandler {

    private int tpId = Integer.MAX_VALUE;

    private final List<Callback> runningTasks = new ArrayList<>();

    public int requestNewID() {
        int ret = tpId;
        tpId--;
        return ret;
    }

    public void addRunningTask(Callback callback) {
        if (callback != null) runningTasks.add(callback);
    }

    public boolean removeRunningTask(Callback callback) {
        return runningTasks.remove(callback);
    }

    public Callback getRunningTask(int uid) {
        for (Callback cb : runningTasks) {
            if (cb.getUniqueID() == uid) return cb;
        }
        return null;
    }
}
