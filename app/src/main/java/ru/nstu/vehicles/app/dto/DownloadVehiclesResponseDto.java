package ru.nstu.vehicles.app.dto;

import ru.nstu.vehicles.app.model.dto.VehicleDto;

import java.io.Serializable;
import java.util.ArrayList;

public record DownloadVehiclesResponseDto(ArrayList<VehicleDto> vehicles) implements Serializable {
}
