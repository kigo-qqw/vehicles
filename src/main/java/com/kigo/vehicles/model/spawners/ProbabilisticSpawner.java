package com.kigo.vehicles.model.spawners;

import com.kigo.vehicles.model.factories.IVehicleFactory;
import com.kigo.vehicles.model.repositories.IVehicleRepository;

public class ProbabilisticSpawner implements IVehicleSpawner {
    private final IVehicleRepository vehicleRepository;
    private final IVehicleFactory vehicleFactory;
    private final long startTime;
    private final int N;
    private final double P;
    private long lastSpawnAttempt = 0;

    public ProbabilisticSpawner(IVehicleRepository vehicleRepository, IVehicleFactory vehicleFactory, long startTime, int N, double P) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleFactory = vehicleFactory;
        this.startTime = startTime;
        this.N = N;
        this.P = P;
    }

    @Override
    public void trySpawn(long timeOffset) {
        if (timeOffset - this.lastSpawnAttempt > N) {
            if (true) {  // TODO: add probability check
                vehicleRepository.add(vehicleFactory.makeVehicle());
            }
            this.lastSpawnAttempt = timeOffset;
        }

    }
}
