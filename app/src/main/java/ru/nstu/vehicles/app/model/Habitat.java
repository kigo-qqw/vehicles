package ru.nstu.vehicles.app.model;

import ru.nstu.vehicles.app.model.dto.VehicleDto;
import ru.nstu.vehicles.app.model.repository.IRepository;
import ru.nstu.vehicles.app.model.repository.IVehicleRepository;
import ru.nstu.vehicles.app.model.service.*;
import ru.nstu.vehicles.app.model.service.ais.AutomobileAI;
import ru.nstu.vehicles.app.model.service.ais.MotorbikeAI;
import ru.nstu.vehicles.app.view.components.IHabitatView;
import ru.nstu.vehicles.app.view.components.ITimeView;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class Habitat {
    private final IHabitatView view;
    private final ITimeView timeView;
    private final IVehicleRepository vehicleRepository;
    private List<IVehicleSpawnerService> vehicleSpawnerServices;
    private final IVehicleService vehicleService;
    private final ITimerService timerService;
    private final IAIService aiService;
    public Habitat(IHabitatView view, ITimeView timeView, IVehicleRepository vehicleRepository, IVehicleService vehicleService, ITimerService timerService) {
        this.view = view;
        this.timeView = timeView;
        this.vehicleRepository = vehicleRepository;
        this.vehicleService = vehicleService;
        this.timerService = timerService;
        this.timerService.setHabitat(this);

        this.aiService = new AIService();
        this.aiService.use(AutomobileAI.class, new AutomobileAI());
        this.aiService.use(MotorbikeAI.class, new MotorbikeAI());
    }

    public void start(List<IVehicleSpawnerService> vehicleSpawnerServices) {
        this.vehicleSpawnerServices = vehicleSpawnerServices;

        this.timerService.start();
        this.aiService.resume(AutomobileAI.class);
    }

    public void stop() {
        this.vehicleRepository.deleteALl();
        this.view.deleteAll();
        this.timerService.stop();
        this.aiService.pause(AutomobileAI.class);
    }

    public void pause() {
        this.timerService.pause();
        this.aiService.pause(AutomobileAI.class);
    }

    public void resume() {
        this.timerService.resume();
        this.aiService.resume(AutomobileAI.class);
    }

    public void update(long timeOffset) {
        this.vehicleSpawnerServices
                .stream()
                .map(service -> service.trySpawn(timeOffset))
                .filter(Optional::isPresent)
                .forEach(vehicle -> {
                    try {
                        vehicleRepository.tryAdd(vehicle.get());
                    } catch (IRepository.OutOfSpaceException e) {
                        stop();
                    }
//                    view.addVehicle(vehicleService.get(vehicle.get()));
                });
        this.vehicleRepository.update(timeOffset);
        this.view.update(this.vehicleRepository.getAll().map(this.vehicleService::get).toList());
        this.timeView.setTime(timeOffset);
    }

    public Stream<VehicleDto> getAll() {
        return this.vehicleRepository.getAll().map(this.vehicleService::get);
    }

    public ITimerService getTimerService() {
        return this.timerService;
    }
}
