package ru.nstu.vehicles.app.model.repository;

import ru.nstu.vehicles.app.model.entities.Vehicle;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
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
    private final Connection connection;

    public VehicleRepository(Connection connection) {
        this.vehicles = new ArrayList<>();
        this.vehicleUuids = new HashSet<>();
        this.vehiclesByBirthTime = new TreeMap<>();
        this.connection = connection;
        try {
            Statement statement = this.connection.createStatement();
            statement.execute(
                    "CREATE TABLE IF NOT EXISTS 'vehicles' (" +
                            "'uuid' BINARY PRIMARY KEY, " +
                            "'x' DOUBLE ," +
                            "'y' DOUBLE, " +
                            "'dx' DOUBLE," +
                            "'dy' DOUBLE, 'birthTime' INTEGER, 'lifeTime' INTEGER  )");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
    public synchronized void save() {
            try {
                Statement statement = this.connection.createStatement();
                this.vehicles.forEach(vehicle -> {
                    try {
                        statement.execute("INSERT INTO ");
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
//                statement.execute();
                statement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
    }

    @Override
    public <T extends Vehicle> void save(Class<T> vehicleClass) {

    }

    @Override
    public void load() {

    }

    @Override
    public <T extends Vehicle> void load(Class<T> vehicleClass) {

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
