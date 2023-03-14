package com.kigo.vehicles.controller;

import com.kigo.vehicles.model.Habitat;
import javafx.stage.Stage;

public class MainControllerFactory {
    public MainController getMainController(Stage stage, Habitat model) {
        return new MainController(stage, model);
    }
}
