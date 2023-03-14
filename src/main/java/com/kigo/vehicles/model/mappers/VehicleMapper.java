package com.kigo.vehicles.model.mappers;

import com.kigo.vehicles.model.dto.VehicleDto;
import com.kigo.vehicles.model.entities.Vehicle;

public class VehicleMapper implements IVehicleMapper {
    @Override
    public VehicleDto convert(Vehicle v) {
        String classname = v.getClass().getName();
        return new VehicleDto(classname.substring(classname.lastIndexOf('.') + 1), v.getX(), v.getY());
    }
}
