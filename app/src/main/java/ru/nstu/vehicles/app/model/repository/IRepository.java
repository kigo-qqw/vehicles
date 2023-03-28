package ru.nstu.vehicles.app.model.repository;

import java.util.stream.Stream;

public interface IRepository<E> {
    void tryAdd(E entity) throws OutOfSpaceException;

    Stream<E> getAll();

    void deleteALl();

    class OutOfSpaceException extends Exception {
    }
}
