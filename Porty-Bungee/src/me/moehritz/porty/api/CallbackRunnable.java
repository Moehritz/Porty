package me.moehritz.porty.api;

public interface CallbackRunnable {

    void success();

    void error(String errmsg);

}
