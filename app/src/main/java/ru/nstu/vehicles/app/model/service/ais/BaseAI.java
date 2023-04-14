package ru.nstu.vehicles.app.model.service.ais;

import java.util.Timer;
import java.util.TimerTask;

public abstract class BaseAI extends Thread {
    private volatile boolean isSuspended;

    public abstract void update();
    private void runUpdate() {
        try {
            synchronized (this) {
                while (isSuspended()) wait();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        update();
    }
    @Override
    public void run() {
        Timer timer = new Timer();
        timer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        runUpdate();
                    }
                }, 0, 100
        );
    }

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
