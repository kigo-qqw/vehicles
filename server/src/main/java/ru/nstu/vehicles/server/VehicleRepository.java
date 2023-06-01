package ru.nstu.vehicles.server;

import ru.nstu.vehicles.app.model.dto.VehicleDto;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class VehicleRepository {
    private final Map<InetSocketAddress, List<VehicleDto>> vehicles;

    public VehicleRepository() {
        this.vehicles = new HashMap<>();
    }

    public void createOrUpdate(InetSocketAddress address, List<VehicleDto> vehicles) {
        this.vehicles.put(address, vehicles);
    }

    public Optional<List<VehicleDto>> get(InetSocketAddress address) {
        if (this.vehicles.containsKey(address))
            return Optional.of(this.vehicles.get(address));
        return Optional.empty();
    }
}
