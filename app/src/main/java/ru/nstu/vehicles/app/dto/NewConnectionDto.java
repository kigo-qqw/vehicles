package ru.nstu.vehicles.app.dto;

import java.io.Serializable;
import java.net.InetAddress;

public record NewConnectionDto(InetAddress address) implements Serializable {
}
