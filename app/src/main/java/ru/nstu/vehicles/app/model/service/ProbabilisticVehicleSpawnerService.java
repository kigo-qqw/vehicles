package ru.nstu.vehicles.app.model.service;

import ru.nstu.vehicles.app.model.entities.Vehicle;
import ru.nstu.vehicles.app.model.entities.factories.IVehicleFactory;

import java.util.Optional;
import java.util.Random;

public class ProbabilisticVehicleSpawnerService implements IVehicleSpawnerService {
    private final int period;
    private final double probability;
    private final Random random = new Random();
    private final IVehicleFactory vehicleFactory;
    private long lastSpawnAttempt = -1;

    public ProbabilisticVehicleSpawnerService(IVehicleFactory vehicleFactory, int period, double probability) {
        this.vehicleFactory = vehicleFactory;
        this.period = period;
        this.probability = probability;
    }

    @Override
    public Optional<Vehicle> trySpawn(long timeOffset) {
        if (timeOffset - this.lastSpawnAttempt > this.period) {
            this.lastSpawnAttempt = timeOffset;
            if (this.random.nextFloat(0, 1) < this.probability) {
                Vehicle vehicle = this.vehicleFactory.makeVehicle();
                return Optional.of(vehicle);
            }
        }
        return Optional.empty();
    }
}
