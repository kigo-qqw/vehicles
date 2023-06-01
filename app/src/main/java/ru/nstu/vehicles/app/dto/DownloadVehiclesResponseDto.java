package ru.nstu.vehicles.app.dto;

import ru.nstu.vehicles.app.model.dto.VehicleDto;

import java.io.Serializable;
import java.util.List;

public record DownloadVehiclesResponseDto(List<VehicleDto> vehicles) implements Serializable {
}
