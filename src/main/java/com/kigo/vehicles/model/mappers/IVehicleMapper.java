package com.kigo.vehicles.model.mappers;

import com.kigo.vehicles.model.dto.VehicleDto;
import com.kigo.vehicles.model.entities.Vehicle;

public interface IVehicleMapper {
    VehicleDto convert(Vehicle v);
}
