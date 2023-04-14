package ru.nstu.vehicles.app.model.service;

import javafx.application.Platform;
import ru.nstu.vehicles.app.model.Habitat;
import ru.nstu.vehicles.app.model.repository.IVehicleRepository;
import ru.nstu.vehicles.app.view.components.IHabitatView;

import java.util.Timer;
import java.util.TimerTask;

public class RedrawTimerService implements IRedrawTimerService {
    private IHabitatView habitatView;
    private IVehicleRepository vehicleRepository;
    private IVehicleService vehicleService;
    private long simulationTime;
    private Timer timer;
    private final int PERIOD = 10;

    public RedrawTimerService(IVehicleRepository vehicleRepository, IVehicleService vehicleService) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleService = vehicleService;
    }

    @Override
    public void setView(IHabitatView habitatView) {
        this.habitatView = habitatView;
    }

//    @Override
//    public void setVehicleRepository(IVehicleRepository vehicleRepository) {
//        this.vehicleRepository = vehicleRepository;
//    }

    @Override
    public void start() {
        this.timer = new Timer();
        this.timer.schedule(new UpdateTask(), 0, PERIOD);
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
    }

    @Override
    public long getSimulationTime() {
        return this.simulationTime;
    }

    private class UpdateTask extends TimerTask {
        @Override
        public void run() {
//            Platform.runLater(() -> {
                simulationTime += PERIOD;
                habitatView.update(vehicleRepository.getAll().map(vehicle -> vehicleService.get(vehicle)).toList());
//            });
        }
    }
}
