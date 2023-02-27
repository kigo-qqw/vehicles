package com.kigo.vehicles.model.spawners;

import com.kigo.vehicles.model.repositories.OutOfSpace;

public interface IVehicleSpawner {
    void trySpawn(long timeOffset) throws OutOfSpace;
}
