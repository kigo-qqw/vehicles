package com.kigo.vehicles.model.factories;

import com.kigo.vehicles.model.entities.Motorbike;
import java.util.Random;

public class MotorbikeFactory implements IVehicleFactory {
    private final int width;
    private final int height;
    private final Random random = new Random();

    public MotorbikeFactory(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public Motorbike makeVehicle() {
        return new Motorbike(random.nextInt(0, this.width), random.nextInt(0, this.height), 100, 100);
    }
}
