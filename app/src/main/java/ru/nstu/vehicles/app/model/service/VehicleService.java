package ru.nstu.vehicles.app.model.service;

import ru.nstu.vehicles.app.model.dto.VehicleDto;
import ru.nstu.vehicles.app.model.entities.Automobile;
import ru.nstu.vehicles.app.model.entities.Motorbike;
import ru.nstu.vehicles.app.model.entities.Vehicle;

import java.lang.reflect.InvocationTargetException;

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

    @Override
    public Vehicle get(VehicleDto vehicleDto, int habitatWidth, int habitatHeight) {
        return switch (vehicleDto.type()) {
            case AUTOMOBILE -> new Automobile(vehicleDto.x(), vehicleDto.y(), vehicleDto.dx(), vehicleDto.dy(),
                    vehicleDto.birthTime(), vehicleDto.lifeTime(), habitatWidth, habitatHeight);
            case MOTORBIKE -> new Motorbike(vehicleDto.x(), vehicleDto.y(), vehicleDto.dx(), vehicleDto.dy(),
                    vehicleDto.birthTime(), vehicleDto.lifeTime(), habitatWidth, habitatHeight);
            case UNKNOWN -> throw new RuntimeException();
        };
    }
}
