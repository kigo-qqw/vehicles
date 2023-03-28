package ru.nstu.vehicles.app.model.entities.factories;

import ru.nstu.vehicles.app.model.entities.Motorbike;
import ru.nstu.vehicles.app.model.service.ITimerService;

import java.util.Random;

public class MotorbikeFactory implements IVehicleFactory {
    private final ITimerService timerService;
    private final long lifeTime;
    private final int width;
    private final int height;
    private final Random random = new Random();

    public MotorbikeFactory(ITimerService timerService, long lifeTime, int width, int height) {
        this.timerService = timerService;
        this.lifeTime = lifeTime;
        this.width = width;
        this.height = height;
    }

    @Override
    public Motorbike makeVehicle() {
        return new Motorbike(
                this.random.nextInt(0, this.width),
                this.random.nextInt(0, this.height),
                this.timerService.getSimulationTime(), this.lifeTime
        );
    }
}