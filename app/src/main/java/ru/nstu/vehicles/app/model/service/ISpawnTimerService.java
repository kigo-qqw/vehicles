package ru.nstu.vehicles.app.model.service;

import java.util.List;

public interface ISpawnTimerService extends ITimerService {
    void setVehicleSpawnerServices(List<IVehicleSpawnerService> vehicleSpawnerServices);
}
