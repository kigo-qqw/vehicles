package com.kigo.vehicles.controller;

import javafx.fxml.FXML;
import javafx.stage.Stage;

public class MenuAboutController {
    private final Stage stage;

    public MenuAboutController(Stage stage) {
        this.stage = stage;
    }

    @FXML
    void close() {
        stage.close();
    }
}
