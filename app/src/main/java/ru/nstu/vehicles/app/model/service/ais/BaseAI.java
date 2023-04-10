package ru.nstu.vehicles.app.model.service.ais;

public abstract class BaseAI extends Thread {
    private volatile boolean isSuspended;


    public abstract void run();

    public synchronized boolean isSuspended() {
        return this.isSuspended;
    }

    public synchronized void activate() {
        this.isSuspended = false;
        notify();
    }

    public synchronized void deactivate() {
        this.isSuspended = true;
    }
}
