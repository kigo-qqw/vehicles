package com.kigo.vehicles.model.repositories;

import com.kigo.vehicles.model.entities.Vehicle;

public class FixedArrayVehicleRepository implements IVehicleRepository {
    private Vehicle[] vehicles;
    private int nextElementIndex;


    public FixedArrayVehicleRepository(int size) {
        this.vehicles = new Vehicle[size];
    }

    @Override
    public void add(Vehicle entity) {
        this.vehicles[this.nextElementIndex++] = entity;
    }

    @Override
    public void deleteAll() {
        this.vehicles = new Vehicle[this.vehicles.length];
        this.nextElementIndex = 0;
    }
}
