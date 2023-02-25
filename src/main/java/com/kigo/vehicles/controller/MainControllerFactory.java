package com.kigo.vehicles.controller;

import com.kigo.vehicles.model.Habitat;

public class MainControllerFactory {
    public MainController getMainController(Habitat model) {
        return new MainController(model);
    }
}
