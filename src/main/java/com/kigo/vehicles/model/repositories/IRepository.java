package com.kigo.vehicles.model.repositories;

import java.util.stream.Stream;

public interface IRepository<E> {
    void tryAdd(E entity) throws OutOfSpace;
    Stream<E> getAll();
    void deleteAll();
}
