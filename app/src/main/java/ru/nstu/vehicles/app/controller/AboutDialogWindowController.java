package ru.nstu.vehicles.app.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import ru.nstu.vehicles.app.view.AboutDialogWindow;

import java.net.URL;
import java.util.ResourceBundle;

public class AboutDialogWindowController implements IController {
    private final AboutDialogWindow aboutDialogWindow;
    @FXML
    private Button closeButton;

    public AboutDialogWindowController(AboutDialogWindow aboutDialogWindow) {
        this.aboutDialogWindow = aboutDialogWindow;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.closeButton.setOnAction(event -> aboutDialogWindow.close());
    }
}
