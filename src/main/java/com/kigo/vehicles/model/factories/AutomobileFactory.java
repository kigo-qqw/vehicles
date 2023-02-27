package com.kigo.vehicles.model.factories;

import com.kigo.vehicles.model.entities.Automobile;

import java.util.Random;

public class AutomobileFactory implements IVehicleFactory {
    private final int width;
    private final int height;
    private final Random random = new Random();

    public AutomobileFactory(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public Automobile makeVehicle() {
        return new Automobile(random.nextInt(0, this.width), random.nextInt(0, this.height), 100, 100);
    }
}
