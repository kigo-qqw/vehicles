package ru.nstu.vehicles.app.model.dto;

import ru.nstu.vehicles.app.model.entities.Automobile;
import ru.nstu.vehicles.app.model.entities.Motorbike;
import ru.nstu.vehicles.app.model.entities.Vehicle;

import java.io.Serializable;
import java.util.UUID;

public record VehicleDto(UUID uuid, double x, double y, double dx, double dy, long birthTime, long lifeTime,
                         Type type) implements Serializable {
    public enum Type {
        UNKNOWN, MOTORBIKE, AUTOMOBILE;

        public static Type fromVehicle(Vehicle vehicle) {
            if (vehicle instanceof Motorbike) return MOTORBIKE;
            if (vehicle instanceof Automobile) return AUTOMOBILE;
            return UNKNOWN;
        }
    }
}
