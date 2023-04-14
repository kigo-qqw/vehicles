package ru.nstu.vehicles.app.model.service;

import ru.nstu.vehicles.app.model.entities.Vehicle;
import ru.nstu.vehicles.app.model.service.ais.BaseAI;

public interface IAIService {
    <T extends Vehicle, AI extends BaseAI> void use(Class<T> type, AI ai);

    <T extends Vehicle> void pause(Class<T> type);

    <T extends Vehicle> void resume(Class<T> type);

    <T extends Vehicle> void setPriority(Class<T> type, int priority);
}
