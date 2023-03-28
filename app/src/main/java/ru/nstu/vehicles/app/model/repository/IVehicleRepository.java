package ru.nstu.vehicles.app.model.repository;

import ru.nstu.vehicles.app.model.entities.Vehicle;

public interface IVehicleRepository extends IRepository<Vehicle> {
    void update(long currentSimulationTime);
}
