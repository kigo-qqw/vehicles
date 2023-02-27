package com.kigo.vehicles.controller;

import com.kigo.vehicles.model.Habitat;
import com.kigo.vehicles.model.repositories.OutOfSpace;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.WindowEvent;

import java.util.Timer;
import java.util.TimerTask;

public class MainController {
    @FXML
    private Pane root;
    @FXML
    private VBox labels;
    @FXML
    private Label timeLabel;
    @FXML
    private Label vehiclesInfo;
    @FXML
    private ImageView popup;
    private final Habitat model;
    private Timer timer = null;
    private boolean isShowLabels = true;

    public MainController(Habitat model) {
        this.model = model;
    }

    private void start() {
        popup.setVisible(false);
        stop();
        this.model.start();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    try {
                        model.update(System.currentTimeMillis());
                    } catch (OutOfSpace e) {
                        stop();
                        popup.setVisible(true);
                    }
                });
            }
        }, 0, 100);
    }

    private void stop() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
        }
        this.model.stop();
    }

    private void toggle() {
        isShowLabels = !isShowLabels;
        labels.setVisible(isShowLabels);
    }

    public void adjustView(WindowEvent ignoredWindowEvent) {
        model.setView(root, timeLabel, vehiclesInfo);
        root.getScene().setOnKeyReleased(keyEvent -> {
            switch (keyEvent.getCode()) {
                case B -> start();
                case E -> stop();
                case T -> toggle();
            }
        });
    }
}
