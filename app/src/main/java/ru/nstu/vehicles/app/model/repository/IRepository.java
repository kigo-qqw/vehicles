package ru.nstu.vehicles.app.model.repository;

import ru.nstu.vehicles.app.model.entities.Vehicle;

import java.util.stream.Stream;

public interface IRepository<E> {
    void tryAdd(E entity) throws OutOfSpaceException;

    int getLength();

    Stream<E> getAll();

    void setAll(Stream<E> entities);

    void deleteALl();

    void save();

    <T extends Vehicle> void save(Class<T> vehicleClass);

    void load();

    <T extends Vehicle> void load(Class<T> vehicleClass);

    class OutOfSpaceException extends Exception {
    }
}
