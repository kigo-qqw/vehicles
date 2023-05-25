package ru.nstu.vehicles.app.dto;

import java.io.Serializable;
import java.net.InetAddress;

public record DropConnectionDto(InetAddress address) implements Serializable {
}
