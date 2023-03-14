package com.kigo.vehicles.controller;

import com.kigo.vehicles.model.Habitat;
import com.kigo.vehicles.model.dto.HabitatParams;
import com.kigo.vehicles.model.mappers.VehicleMapper;
import com.kigo.vehicles.model.repositories.OutOfSpace;
import com.kigo.vehicles.model.spawners.ProbabilisticSpawnerParams;
import com.kigo.vehicles.view.components.NumberField;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.IntStream;

public class MainController {
    private final Stage stage;
    @FXML
    private MenuItem menuStopButton;
    @FXML
    private MenuItem menuStartButton;
    @FXML
    private Label timeLabel;
    @FXML
    private CheckBox showStatCheckBox;
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
    private AnchorPane root;
    private final Habitat model;
    private Timer timer = null;
    private final BooleanProperty showTimeProperty = new SimpleBooleanProperty(true);
    private final BooleanProperty showStatisticProperty = new SimpleBooleanProperty(true);

    public MainController(Stage stage, Habitat model) {
        this.stage = stage;
        this.model = model;
    }

    private void start() {
        stop();
        stopButton.setDisable(false);
        startButton.setDisable(true);
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
                        stopWithModal();
                    }
                });
            }
        }, 0, 100);
    }

    private void stop() {
        stopButton.setDisable(true);
        startButton.setDisable(false);
        if (timer != null) {
            timer.cancel();
            timer.purge();
        }
        this.model.stop();
    }

    private void stopWithModal() {
        showStopModal();
        stop();
    }

    private void toggle() {
        showTimeProperty.set(!showTimeProperty.get());
    }

    private void showStopModal() {
        if (showStatisticProperty.get()) {
            final Stage dialog = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(HabitatInfoController.class.getResource("/com/kigo/vehicles/fxml/habitat-info-view.fxml"));
            fxmlLoader.setControllerFactory(controllerClass -> new HabitatInfoController(dialog, this.model.getVehicleRepository(), new VehicleMapper()));
            try {
                dialog.setScene(new Scene(fxmlLoader.load()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            dialog.setTitle("Results");
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initOwner(
                    stage.getScene().getWindow());
            dialog.show();
        }
    }

    public void adjustView(WindowEvent ignoredWindowEvent) {
        model.setView(root, timeLabel);
        root.getScene().setOnKeyReleased(keyEvent -> {
            switch (keyEvent.getCode()) {
                case B -> start();
                case E -> stopWithModal();
                case T -> toggle();
            }
        });
        startButton.setOnAction(actionEvent -> start());
        stopButton.setOnAction(actionEvent -> stopWithModal());

        menuStartButton.setOnAction(actionEvent -> {
            menuStartButton.setDisable(true);
            menuStopButton.setDisable(false);
            start();
        });
        menuStopButton.setOnAction(actionEvent -> {
            menuStopButton.setDisable(true);
            menuStartButton.setDisable(false);
            stopWithModal();
        });

        showTimeRadioButton.setOnAction(actionEvent -> showTimeProperty.set(true));
        dontShowTimeRadioButton.setOnAction(actionEvent -> showTimeProperty.set(false));
        showTimeProperty.addListener(observable -> {
            boolean showTime = showTimeProperty.get();
            showTimeRadioButton.setSelected(showTime);
            dontShowTimeRadioButton.setSelected(!showTime);
        });

        showStatCheckBox.selectedProperty().bindBidirectional(showStatisticProperty);

        motorbikeProbability.getItems().setAll(IntStream.rangeClosed(0, 10).map(element -> 10 * element).boxed().toList());
        motorbikeProbability.setValue(100);

        automobileProbability.getItems().setAll(IntStream.rangeClosed(0, 10).map(element -> 10 * element).boxed().toList());
        automobileProbability.setValue(100);

        timeLabel.visibleProperty().bind(showTimeProperty);
    }

    public void handleToggleShowTimeMenu() {
        showTimeProperty.set(!showTimeProperty.get());
    }

    public void handleToggleShowInfoMenu() {
        showStatisticProperty.set(!showStatisticProperty.get());
    }

    public void handleAbout() {
        final Stage dialog = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(MenuAboutController.class.getResource("/com/kigo/vehicles/fxml/menu-about-view.fxml"));
        fxmlLoader.setControllerFactory(controllerClass -> new MenuAboutController(dialog));
        try {
            dialog.setScene(new Scene(fxmlLoader.load()));
        } catch (IOException e) {
            throw new RuntimeException();
        }
        dialog.setTitle("About");
        dialog.initModality(Modality.NONE);
        dialog.initOwner(
                stage.getScene().getWindow());
        dialog.show();
    }
}
