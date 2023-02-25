package com.kigo.vehicles.model.entities;

import javafx.scene.image.Image;

public class Motorbike extends Vehicle implements IBehavior, IViewable {
    private final static Image image = new Image("/com/kigo/vehicles/images/WiseMysticalTree.jpg");

    public Motorbike(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public Image getImage() {
        return image;
    }
}
