package ru.nstu.vehicles.app.view.components;

import ru.nstu.vehicles.app.model.dto.VehicleDto;

import java.util.List;

public interface IHabitatView {
    void update(List<VehicleDto> vehicles);
    void deleteAll();
}
