package ru.nstu.vehicles.app.controller.dto;

import javafx.beans.property.*;
import ru.nstu.vehicles.app.model.dto.VehicleDto;

import java.util.UUID;

public class ObservableVehicleDto {
    private final ObjectProperty<UUID> uuid;
    private final StringProperty type;
    private final DoubleProperty x;
    private final DoubleProperty y;
    private final LongProperty birthTime;
    private final LongProperty lifeTime;

    public ObservableVehicleDto(VehicleDto vehicleDto) {
        this.uuid = new SimpleObjectProperty<>(vehicleDto.uuid());
        this.type = new SimpleStringProperty(vehicleDto.type().toString());
        this.x = new SimpleDoubleProperty(vehicleDto.x());
        this.y = new SimpleDoubleProperty(vehicleDto.y());
        this.birthTime = new SimpleLongProperty(vehicleDto.birthTime());
        this.lifeTime = new SimpleLongProperty(vehicleDto.lifeTime());
    }

    public ObjectProperty<UUID> uuidProperty() {
        return this.uuid;
    }

    public StringProperty typeProperty() {
        return this.type;
    }

    public DoubleProperty xProperty() {
        return this.x;
    }

    public DoubleProperty yProperty() {
        return this.y;
    }

    public LongProperty birthTimeProperty() {
        return this.birthTime;
    }

    public LongProperty lifeTimeProperty() {
        return this.lifeTime;
    }
}
