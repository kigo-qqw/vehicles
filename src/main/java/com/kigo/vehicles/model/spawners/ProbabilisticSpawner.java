package com.kigo.vehicles.model.spawners;

import com.kigo.vehicles.model.entities.Vehicle;
import com.kigo.vehicles.model.factories.IVehicleFactory;
import com.kigo.vehicles.model.repositories.IVehicleRepository;
import com.kigo.vehicles.model.repositories.OutOfSpace;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.Random;

public class ProbabilisticSpawner implements IVehicleSpawner {
    private final IVehicleRepository vehicleRepository;

    private final IVehicleFactory vehicleFactory;
    private final Pane view;
    private final int N;
    private final double P;
    private long lastSpawnAttempt = 0;
    private final Random random = new Random();

    public ProbabilisticSpawner(IVehicleRepository vehicleRepository, IVehicleFactory vehicleFactory, Pane view, int N, double P) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleFactory = vehicleFactory;
        this.view = view;
        this.N = N;
        this.P = P;
    }

    @Override
    public void trySpawn(long timeOffset) throws OutOfSpace {
        if (timeOffset - this.lastSpawnAttempt > N) {
            if (random.nextFloat(0, 1) < P) {
                Vehicle vehicle = vehicleFactory.makeVehicle();
                vehicleRepository.tryAdd(vehicle);

                ImageView iv = new ImageView(vehicle.getImage());
                iv.setFitHeight(vehicle.getHeight());
                iv.setFitWidth(vehicle.getWidth());
                iv.setX(vehicle.getX());
                iv.setY(vehicle.getY());

                view.getChildren().add(iv);
            }
            this.lastSpawnAttempt = timeOffset;
        }

    }
}
