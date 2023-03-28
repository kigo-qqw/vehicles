package ru.nstu.vehicles.app.model.service;

import ru.nstu.vehicles.app.model.Habitat;

public interface ITimerService {
    void setHabitat(Habitat habitat);

    void start();

    void pause();

    void resume();

    void stop();
    long getSimulationTime();
}
