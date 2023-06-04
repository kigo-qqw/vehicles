package ru.nstu.vehicles.server;

import ru.nstu.vehicles.app.dto.*;
import ru.nstu.vehicles.app.model.dto.VehicleDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Dispatcher {
    private final List<Session> sessions;
    private final VehicleRepository vehicleRepository;

    public Dispatcher(List<Session> sessions, VehicleRepository vehicleRepository) {
        this.sessions = sessions;
        this.vehicleRepository = vehicleRepository;
    }

    public void dispatch(Session session, Object object) {
        if (object instanceof UploadVehiclesDto uploadVehicles) {
            this.vehicleRepository.createOrUpdate(uploadVehicles.address(), uploadVehicles.vehicles());
            System.out.println(uploadVehicles.address());
            this.sessions.forEach(s -> s.send(new UploadVehiclesNotificationDto(uploadVehicles.address())));
        }
        if (object instanceof DownloadVehiclesRequestDto downloadVehiclesRequest) {
            Optional<List<VehicleDto>> optionalVehicles = this.vehicleRepository.get(downloadVehiclesRequest.address());
            optionalVehicles.ifPresent(
                    vehicles -> session.send(new DownloadVehiclesResponseDto(new ArrayList<>(vehicles))));
        }
    }

    public List<Session> getSessions() {
        return this.sessions;
    }

    public VehicleRepository getVehicleRepository() {
        return this.vehicleRepository;
    }
}
