package ru.nstu.vehicles.app.dto;

import java.io.Serializable;
import java.net.InetSocketAddress;
import java.util.Map;

//public record ExistingConnectionsDto(List<InetSocketAddress> addresses) implements Serializable {
//}

public record ExistingConnectionsDto(Map<InetSocketAddress, Boolean> addresses) implements Serializable {
}
