package com.kigo.vehicles.model.repositories;

import com.kigo.vehicles.model.entities.Vehicle;

import java.util.Arrays;
import java.util.stream.Stream;

public class FixedArrayVehicleRepository implements IVehicleRepository {
    private Vehicle[] vehicles;
    private int nextElementIndex;

    public FixedArrayVehicleRepository(int size) {
        this.vehicles = new Vehicle[size];
    }

    @Override
    public void tryAdd(Vehicle entity) throws OutOfSpace {
        if (this.nextElementIndex >= this.vehicles.length) {
            throw new OutOfSpace();
        }
        this.vehicles[this.nextElementIndex++] = entity;
    }

    @Override
    public Stream<Vehicle> getAll() {
        return Arrays.stream(vehicles);
    }

    @Override
    public void deleteAll() {
        this.vehicles = new Vehicle[this.vehicles.length];
        this.nextElementIndex = 0;
    }
}
