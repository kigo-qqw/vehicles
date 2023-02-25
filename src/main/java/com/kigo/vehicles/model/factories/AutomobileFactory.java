package com.kigo.vehicles.model.factories;

import com.kigo.vehicles.model.entities.Automobile;

public class AutomobileFactory implements IVehicleFactory {
    @Override
    public Automobile makeVehicle() {
        return new Automobile(1, 1, 40, 40);
    }
}
