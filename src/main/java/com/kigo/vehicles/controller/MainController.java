package com.kigo.vehicles.controller;

import com.kigo.vehicles.model.Habitat;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private AnchorPane root;
    private final Habitat model;

    public MainController(Habitat model) {
        this.model = model;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        model.setView(root);
    }
}
