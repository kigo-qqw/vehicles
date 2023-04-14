package ru.nstu.vehicles.app.model.service;

import ru.nstu.vehicles.app.model.repository.IRepository;
import ru.nstu.vehicles.app.model.repository.IVehicleRepository;

import java.util.List;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

public class SpawnTimerService implements ISpawnTimerService, Runnable {
    private final IVehicleRepository vehicleRepository;
    private List<IVehicleSpawnerService> vehicleSpawnerServices;
    private Thread worker;
    private long simulationTime;
    private Timer timer;
    private final int PERIOD = 10;

    public SpawnTimerService(IVehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public void start() {
        this.worker = new Thread(this);
        this.worker.start();
    }

    @Override
    public void pause() {
        this.timer.cancel();
        this.timer.purge();
    }

    @Override
    public void resume() {
        this.timer = new Timer();
        this.timer.schedule(new UpdateTask(), 0, PERIOD);
    }

    @Override
    public void stop() {
        this.timer.cancel();
        this.timer.purge();
        this.simulationTime = 0;
        try {
            this.worker.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long getSimulationTime() {
        return this.simulationTime;
    }

    @Override
    public void setVehicleSpawnerServices(List<IVehicleSpawnerService> vehicleSpawnerServices) {
        this.vehicleSpawnerServices = vehicleSpawnerServices;
    }

    @Override
    public void run() {
        this.timer = new Timer();
        this.timer.schedule(new UpdateTask(), 0, PERIOD);
    }

    private class UpdateTask extends TimerTask {
        @Override
        public void run() {
//            Platform.runLater(() -> {
                simulationTime += PERIOD;
//            });
            synchronized (vehicleRepository) {
                vehicleSpawnerServices
                        .stream()
                        .map(service -> service.trySpawn(simulationTime))
                        .filter(Optional::isPresent)
                        .forEach(vehicle -> {
                            try {
                                vehicleRepository.tryAdd(vehicle.get());
                            } catch (IRepository.OutOfSpaceException e) {
                                stop();
                            }
                        });
                vehicleRepository.update(simulationTime);
            }
        }
    }
}
