package com.kigo.vehicles.model.factories;

import com.kigo.vehicles.model.entities.Motorbike;

public class MotorbikeFactory implements IVehicleFactory {
    @Override
    public Motorbike makeVehicle() {
        return new Motorbike(1, 1, 40, 40);
    }
}
