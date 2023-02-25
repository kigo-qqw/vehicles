package com.kigo.vehicles.model.repositories;

public interface IRepository<E> {
    void add(E entity);

    void deleteAll();
}
