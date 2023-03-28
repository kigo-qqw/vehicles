package ru.nstu.vehicles.app.model.service;

import ru.nstu.vehicles.app.model.entities.Vehicle;

import java.util.Optional;

public interface IVehicleSpawnerService {
    Optional<Vehicle> trySpawn(long timeOffset);
}
