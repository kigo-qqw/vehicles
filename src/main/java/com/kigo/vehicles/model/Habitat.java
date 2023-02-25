package com.kigo.vehicles.model;

import com.kigo.vehicles.model.factories.AutomobileFactory;
import com.kigo.vehicles.model.factories.MotorbikeFactory;
import com.kigo.vehicles.model.repositories.IVehicleRepository;
import com.kigo.vehicles.model.spawners.IVehicleSpawner;
import com.kigo.vehicles.model.spawners.ProbabilisticSpawner;
import javafx.scene.layout.Pane;

public class Habitat {
    private final IVehicleRepository vehicleRepository;
    private final IVehicleSpawner[] vehicleSpawners;
    private Pane view = null;
    private State state;

    private enum State {Active, Paused, Inactive}

    public Habitat(IVehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleSpawners = new IVehicleSpawner[]{
                new ProbabilisticSpawner(vehicleRepository, new AutomobileFactory(), System.currentTimeMillis(), 2000, 0.5f),
                new ProbabilisticSpawner(vehicleRepository, new MotorbikeFactory(), System.currentTimeMillis(), 3000, 0.7f),
        };
    }

    public void start() {
        state = State.Active;
    }

    public void stop() {
        state = State.Inactive;
    }

    public void update(long timeOffset) {
        for (IVehicleSpawner spawner : this.vehicleSpawners) {
            spawner.trySpawn(timeOffset);
        }
    }

    public void setView(Pane view) {
        this.view = view;
    }
}
