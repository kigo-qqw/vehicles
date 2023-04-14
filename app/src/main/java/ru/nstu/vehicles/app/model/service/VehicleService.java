package ru.nstu.vehicles.app.model.service;

import ru.nstu.vehicles.app.model.dto.VehicleDto;
import ru.nstu.vehicles.app.model.entities.Vehicle;

public class VehicleService implements IVehicleService {
    public VehicleDto get(Vehicle vehicle) {
        return new VehicleDto(
                vehicle.getUuid(),
                vehicle.getX(),
                vehicle.getY(),
                vehicle.getDx(),
                vehicle.getDy(),
                vehicle.getBirthTime(),
                vehicle.getLifeTime(),
                VehicleDto.Type.fromVehicle(vehicle)
        );
    }
}
