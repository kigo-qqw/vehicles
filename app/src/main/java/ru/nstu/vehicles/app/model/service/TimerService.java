package ru.nstu.vehicles.app.model.service;

import javafx.application.Platform;
import ru.nstu.vehicles.app.model.Habitat;

import java.util.Timer;
import java.util.TimerTask;

public class TimerService implements ITimerService {
    private Habitat habitat;
    private long simulationTime;
    private Timer timer;
    private final int PERIOD = 10;

    @Override
    public void setHabitat(Habitat habitat) {
        this.habitat = habitat;
    }

    @Override
    public void start() {
        this.timer = new Timer();
        this.timer.schedule(new UpdateTask(), 0, PERIOD);
    }

    @Override
    public void pause() {
        this.timer.cancel();
        this.timer.purge();
    }

    @Override
    public void resume() {
        this.timer = new Timer();
        this.timer.schedule(new UpdateTask(), 0, PERIOD);
    }

    @Override
    public void stop() {
        this.timer.cancel();
        this.timer.purge();
        this.simulationTime = 0;
    }

    @Override
    public long getSimulationTime() {
        return this.simulationTime;
    }

    private class UpdateTask extends TimerTask {
        @Override
        public void run() {
            Platform.runLater(() -> {
                simulationTime += PERIOD;
                habitat.update(simulationTime);
            });
        }
    }
}
