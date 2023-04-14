package ru.nstu.vehicles.app.model.service.ais;


import ru.nstu.vehicles.app.model.entities.Automobile;
import ru.nstu.vehicles.app.model.entities.IBehavior;
import ru.nstu.vehicles.app.model.repository.IVehicleRepository;

public class AutomobileAI extends BaseAI {
    private final IVehicleRepository vehicleRepository;

    public AutomobileAI(IVehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public void update() {
        synchronized (this.vehicleRepository) {
            this.vehicleRepository.getAll().filter(vehicle -> vehicle instanceof Automobile).forEach(IBehavior::move);
        }
    }
}
