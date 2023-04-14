package ru.nstu.vehicles.app.model.service;

import ru.nstu.vehicles.app.view.components.IHabitatView;

public interface IRedrawTimerService extends ITimerService {
    void setView(IHabitatView habitatView);

//    void setVehicleRepository(IVehicleRepository vehicleRepository);
}
