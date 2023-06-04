package ru.nstu.vehicles.app.dto;

import java.io.Serializable;
import java.net.InetSocketAddress;
import java.util.HashMap;

//public record ExistingConnectionsDto(List<InetSocketAddress> addresses) implements Serializable {
//}

public record ExistingConnectionsDto(HashMap<InetSocketAddress, Boolean> addresses) implements Serializable {
}
