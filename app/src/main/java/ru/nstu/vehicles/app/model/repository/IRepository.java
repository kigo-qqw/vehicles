package ru.nstu.vehicles.app.model.repository;

import java.util.stream.Stream;

public interface IRepository<E> {
    void tryAdd(E entity) throws OutOfSpaceException;

    int getLength();
    Stream<E> getAll();
    void setAll(Stream<E> entities);

    void deleteALl();

    class OutOfSpaceException extends Exception {
    }
}
