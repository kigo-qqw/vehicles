package ru.nstu.vehicles.app.dto;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.List;

public record ExistingConnectionsDto(List<InetAddress> addresses) implements Serializable {
}
