package ru.nstu.vehicles.app.model.service.ais;

import ru.nstu.vehicles.app.model.entities.Motorbike;
import ru.nstu.vehicles.app.model.entities.Vehicle;
import ru.nstu.vehicles.app.model.repository.IVehicleRepository;

public class MotorbikeAI extends BaseAI {
    private final IVehicleRepository vehicleRepository;

    public MotorbikeAI(IVehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public void update() {
        synchronized (this.vehicleRepository) {
            this.vehicleRepository.getAll().filter(vehicle -> vehicle instanceof Motorbike).forEach(Vehicle::move);
        }
    }
}
