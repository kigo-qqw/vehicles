package ru.nstu.vehicles.app.dto;

import java.io.Serializable;
import java.net.InetSocketAddress;

public record UploadVehiclesNotificationDto(InetSocketAddress address) implements Serializable {
}
