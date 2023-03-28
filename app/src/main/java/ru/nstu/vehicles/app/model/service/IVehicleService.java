package ru.nstu.vehicles.app.model.service;

import ru.nstu.vehicles.app.model.dto.VehicleDto;
import ru.nstu.vehicles.app.model.entities.Vehicle;

public interface IVehicleService {
    VehicleDto get(Vehicle vehicle);
}
