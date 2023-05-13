package ru.nstu.vehicles.app.model;

import ru.nstu.vehicles.app.model.dto.VehicleDto;
import ru.nstu.vehicles.app.model.entities.Automobile;
import ru.nstu.vehicles.app.model.entities.Motorbike;
import ru.nstu.vehicles.app.model.entities.Vehicle;
import ru.nstu.vehicles.app.model.repository.IVehicleRepository;
import ru.nstu.vehicles.app.model.service.*;
import ru.nstu.vehicles.app.model.service.ais.AutomobileAI;
import ru.nstu.vehicles.app.model.service.ais.MotorbikeAI;
import ru.nstu.vehicles.app.view.components.IHabitatView;
import ru.nstu.vehicles.app.view.components.ITimeView;

import java.util.List;
import java.util.Properties;
import java.util.stream.Stream;

public class Habitat {
    private final IHabitatView view;
    private final ITimeView timeView;
    private final IVehicleRepository vehicleRepository;
    private final IVehicleService vehicleService;
    private final IRedrawTimerService redrawTimerService;
    private final ISpawnTimerService spawnTimerService;
    private final IAIService aiService;
    private final Properties appConfig;

    public Habitat(
            IHabitatView view,
            ITimeView timeView,
            IVehicleRepository vehicleRepository,
            IVehicleService vehicleService,
            IRedrawTimerService redrawTimerService,
            ISpawnTimerService spawnTimerService,
            Properties appConfig
    ) {
        this.view = view;
        this.timeView = timeView;
        this.vehicleRepository = vehicleRepository;
        this.vehicleService = vehicleService;

        this.redrawTimerService = redrawTimerService;
        this.spawnTimerService = spawnTimerService;
        this.appConfig = appConfig;

        this.aiService = new AIService();
        this.aiService.use(Automobile.class, new AutomobileAI(this.vehicleRepository));
        this.aiService.use(Motorbike.class, new MotorbikeAI(this.vehicleRepository));
    }

    public void start(List<IVehicleSpawnerService> vehicleSpawnerServices) {

        this.spawnTimerService.setVehicleSpawnerServices(vehicleSpawnerServices);
        this.redrawTimerService.setView(view);

        this.spawnTimerService.start();
        this.redrawTimerService.start();
        System.out.println("vehicle.automobile.useAI=" + this.appConfig.getProperty("vehicle.motorbike.useAI"));

        if (Boolean.parseBoolean(this.appConfig.getProperty("vehicle.automobile.useAI")))
            this.aiService.resume(Automobile.class);
        if (Boolean.parseBoolean(this.appConfig.getProperty("vehicle.motorbike.useAI")))
            this.aiService.resume(Motorbike.class);
    }

    public void stop() {
        synchronized (this.vehicleRepository) {
            this.vehicleRepository.deleteALl();
        }
        this.view.deleteAll();

        this.spawnTimerService.stop();
        this.redrawTimerService.stop();

        this.aiService.pause(Automobile.class);
        this.aiService.pause(Motorbike.class);
    }

    public void pause() {
        this.spawnTimerService.pause();
        this.redrawTimerService.pause();

        this.aiService.pause(Automobile.class);
        this.aiService.pause(Motorbike.class);
    }

    public void resume() {
        this.spawnTimerService.resume();
        this.redrawTimerService.resume();

        if (Boolean.parseBoolean(this.appConfig.getProperty("vehicle.automobile.useAI")))
            this.aiService.resume(Automobile.class);
        if (Boolean.parseBoolean(this.appConfig.getProperty("vehicle.motorbike.useAI")))
            this.aiService.resume(Motorbike.class);
    }

    public void update(long timeOffset) {
        synchronized (this.vehicleRepository) {
            this.view.update(this.vehicleRepository.getAll().map(this.vehicleService::get).toList());
        }
        this.timeView.setTime(timeOffset);
    }

    public Stream<VehicleDto> getAll() {
        return this.vehicleRepository.getAll().map(this.vehicleService::get);
    }

    public ITimerService getSpawnTimerService() {
        return this.spawnTimerService;
    }

    public <T extends Vehicle> void resumeAI(Class<T> type) {
        System.out.println("resume: " + type);
        this.aiService.resume(type);
    }

    public <T extends Vehicle> void pauseAI(Class<T> type) {
        System.out.println("pause: " + type);
        this.aiService.pause(type);
    }

    public <T extends Vehicle> void setAIPriority(Class<T> type, int priority) {
        this.aiService.setPriority(type, priority);
    }
}
