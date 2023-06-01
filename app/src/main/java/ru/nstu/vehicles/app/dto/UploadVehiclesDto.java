package ru.nstu.vehicles.app.dto;

import ru.nstu.vehicles.app.model.dto.VehicleDto;

import java.io.Serializable;
import java.net.InetSocketAddress;
import java.util.List;

public record UploadVehiclesDto(InetSocketAddress address, List<VehicleDto> vehicles) implements Serializable {
}
