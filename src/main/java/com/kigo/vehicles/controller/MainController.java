package com.kigo.vehicles.controller;

import com.kigo.vehicles.model.Habitat;
import com.kigo.vehicles.model.dto.HabitatParams;
import com.kigo.vehicles.model.repositories.OutOfSpace;
import com.kigo.vehicles.model.spawners.ProbabilisticSpawnerParams;
import com.kigo.vehicles.view.components.NumberField;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.WindowEvent;

import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.IntStream;

public class MainController {
    @FXML
    private Label timeLabel;
    @FXML
    private ToggleButton showStatButton;
    @FXML
    private RadioButton showTimeRadioButton;
    @FXML
    private RadioButton dontShowTimeRadioButton;
    @FXML
    private NumberField automobilePeriod;
    @FXML
    private ComboBox<Number> automobileProbability;
    @FXML
    private NumberField motorbikePeriod;
    @FXML
    private ComboBox<Number> motorbikeProbability;
    @FXML
    private Button startButton;
    @FXML
    private Button stopButton;
    @FXML
    private Pane root;
    @FXML
    private ImageView popup;
    private final Habitat model;
    private Timer timer = null;
    private final BooleanProperty showTimeProperty = new SimpleBooleanProperty(true);
    private final BooleanProperty showStatisticProperty = new SimpleBooleanProperty(true);

    public MainController(Habitat model) {
        this.model = model;
    }

    private void start() {
        popup.setVisible(false);
        stop();
        this.model.start(
                new HabitatParams(
                        new ProbabilisticSpawnerParams(
                                Integer.parseInt(automobilePeriod.getText()),
                                automobileProbability.getValue().doubleValue() / 100
                        ),
                        new ProbabilisticSpawnerParams(
                                Integer.parseInt(motorbikePeriod.getText()),
                                motorbikeProbability.getValue().doubleValue() / 100
                        )
                )
        );
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
        showTimeProperty.set(!showTimeProperty.get());
    }

    public void adjustView(WindowEvent ignoredWindowEvent) {
        model.setView(root, timeLabel);
        root.getScene().setOnKeyReleased(keyEvent -> {
            switch (keyEvent.getCode()) {
                case B -> start();
                case E -> stop();
                case T -> toggle();
            }
        });
        startButton.setOnAction(actionEvent -> {
            startButton.setDisable(true);
            stopButton.setDisable(false);
            start();
        });
        stopButton.setOnAction(actionEvent -> {
            stopButton.setDisable(true);
            startButton.setDisable(false);
            stop();
        });

        showTimeRadioButton.setOnAction(actionEvent -> showTimeProperty.set(true));
        dontShowTimeRadioButton.setOnAction(actionEvent -> showTimeProperty.set(false));
        showTimeProperty.addListener(observable -> {
            boolean showTime = showTimeProperty.get();
            showTimeRadioButton.setSelected(showTime);
            dontShowTimeRadioButton.setSelected(!showTime);
        });

        showStatButton.selectedProperty().bindBidirectional(showStatisticProperty);

        motorbikeProbability.getItems().setAll(IntStream.rangeClosed(0, 10).map(element -> 10 * element).boxed().toList());
        motorbikeProbability.setValue(100);

        automobileProbability.getItems().setAll(IntStream.rangeClosed(0, 10).map(element -> 10 * element).boxed().toList());
        automobileProbability.setValue(100);

        timeLabel.visibleProperty().bind(showTimeProperty);
    }
}
