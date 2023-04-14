package ru.nstu.vehicles.app.model.service;

public interface ITimerService {
    void start();

    void pause();

    void resume();

    void stop();
    long getSimulationTime();
}
