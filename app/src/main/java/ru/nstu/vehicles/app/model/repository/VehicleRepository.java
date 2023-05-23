package ru.nstu.vehicles.app.model.repository;

import ru.nstu.vehicles.app.model.entities.Vehicle;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeMap;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class VehicleRepository implements IVehicleRepository {
    private ArrayList<Vehicle> vehicles;
    private HashSet<UUID> vehicleUuids;
    private TreeMap<Long, Vehicle> vehiclesByBirthTime;

    public VehicleRepository() {
        this.vehicles = new ArrayList<>();
        this.vehicleUuids = new HashSet<>();
        this.vehiclesByBirthTime = new TreeMap<>();
    }

    @Override
    public void tryAdd(Vehicle entity) {
        this.vehicles.add(entity);
        this.vehicleUuids.add(entity.getUuid());
        this.vehiclesByBirthTime.put(entity.getBirthTime(), entity);
    }

    @Override
    public int getLength() {
        return this.vehicles.size();
    }

    @Override
    public Stream<Vehicle> getAll() {
        return this.vehicles.stream();
    }

    @Override
    public void setAll(Stream<Vehicle> entities) {
        this.vehicles = entities.collect(Collectors.toCollection(ArrayList::new));
        this.vehicleUuids.clear();
        this.vehiclesByBirthTime.clear();
        this.vehicles.forEach(vehicle -> {
            this.vehicleUuids.add(vehicle.getUuid());
            this.vehiclesByBirthTime.put(vehicle.getBirthTime(), vehicle);
        });
        System.out.println(this.vehicles);
    }

    @Override
    public void deleteALl() {
        this.vehicles.clear();
        this.vehicleUuids.clear();
        this.vehiclesByBirthTime.clear();
    }

    @Override
    public void update(long currentSimulationTime) {
        ArrayList<Vehicle> newVehicles = new ArrayList<>();
        HashSet<UUID> newVehicleUuids = new HashSet<>();
        TreeMap<Long, Vehicle> newVehiclesByBirthTime = new TreeMap<>();

        this.vehicles.forEach(vehicle -> {
            if (vehicle.getBirthTime() + vehicle.getLifeTime() > currentSimulationTime) {
                newVehicles.add(vehicle);
                newVehicleUuids.add(vehicle.getUuid());
                newVehiclesByBirthTime.put(vehicle.getBirthTime(), vehicle);
            }
        });

        this.vehicles = newVehicles;
        this.vehicleUuids = newVehicleUuids;
        this.vehiclesByBirthTime = newVehiclesByBirthTime;
    }
}
