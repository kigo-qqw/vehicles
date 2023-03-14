package com.kigo.vehicles.model.dto;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class VehicleDto {
    private final StringProperty type;
    private final DoubleProperty x;
    private final DoubleProperty y;

    public VehicleDto(String type, double x, double y) {
        this.type = new SimpleStringProperty(type);
        this.x = new SimpleDoubleProperty(x);
        this.y = new SimpleDoubleProperty(y);
    }

    public StringProperty typeProperty() {
        return type;
    }

    public DoubleProperty xProperty() {
        return x;
    }

    public DoubleProperty yProperty() {
        return y;
    }
}