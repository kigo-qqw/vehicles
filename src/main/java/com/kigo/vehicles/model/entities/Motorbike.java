package com.kigo.vehicles.model.entities;

import javafx.scene.image.Image;

import java.util.Objects;

public class Motorbike extends Vehicle implements IBehavior {
    private final static Image image = new Image(Objects.requireNonNull(Motorbike.class.getResourceAsStream("/com/kigo/vehicles/images/motorbike.jpg")));

    public Motorbike(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public Image getImage() {
        return image;
    }
}
