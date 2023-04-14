package ru.nstu.vehicles.app.controller;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ru.nstu.vehicles.app.model.Habitat;
import ru.nstu.vehicles.app.model.dto.VehicleDto;
import ru.nstu.vehicles.app.model.entities.Automobile;
import ru.nstu.vehicles.app.model.entities.Motorbike;
import ru.nstu.vehicles.app.model.entities.factories.AutomobileFactory;
import ru.nstu.vehicles.app.model.entities.factories.MotorbikeFactory;
import ru.nstu.vehicles.app.model.service.IVehicleSpawnerService;
import ru.nstu.vehicles.app.model.service.ProbabilisticVehicleSpawnerService;
import ru.nstu.vehicles.app.view.AboutDialogWindow;
import ru.nstu.vehicles.app.view.HabitatInfoDialogWindow;
import ru.nstu.vehicles.app.view.components.HabitatView;
import ru.nstu.vehicles.app.view.components.ProbabilisticVehicleStrategyParams;
import ru.nstu.vehicles.app.view.components.TimeView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MainViewController implements IController {
    @FXML
    private VBox root;
    @FXML
    private MenuItem menuStartButton;
    @FXML
    private MenuItem menuStopButton;
    @FXML
    private CheckMenuItem menuShowTimeCheckBox;
    @FXML
    private CheckMenuItem menuShowInfoModalCheckBox;
    @FXML
    private MenuItem menuAboutButton;
    @FXML
    private Button startButton;
    @FXML
    private Button stopButton;
    @FXML
    private CheckBox showInfoModalCheckBox;
    @FXML
    private ToggleGroup showTimeToggleGroup;
    @FXML
    private RadioButton showTimeRadioButton;
    @FXML
    private RadioButton dontShowTimeRadioButton;
    @FXML
    private ProbabilisticVehicleStrategyParams automobileParams;
    @FXML
    private ProbabilisticVehicleStrategyParams motorbikeParams;
    @FXML
    private HabitatView habitatView;
    @FXML
    private TimeView timeView;
    @FXML
    private CheckBox automobileAICheckBox;
    @FXML
    private CheckBox motorbikeAICheckBox;
    @FXML
    private ComboBox<Integer> automobileAIThreadPriorityComboBox;
    @FXML
    private ComboBox<Integer> motorbikeAIThreadPriorityComboBox;
    private Habitat model = null;
    private final BooleanProperty isSimulationActive = new SimpleBooleanProperty(false);
    private final BooleanProperty isShowTime = new SimpleBooleanProperty(true);
    private final BooleanProperty isShowInfoModal = new SimpleBooleanProperty(true);
    private Stage stage;

    public void startSimulation() {
        List<IVehicleSpawnerService> vehicleSpawnerServices = new ArrayList<>();

        vehicleSpawnerServices.add(new ProbabilisticVehicleSpawnerService(
                new AutomobileFactory(
                        this.model.getSpawnTimerService(),
                        this.automobileParams.getLifeTime(),
                        640, 480),
                this.automobileParams.getPeriod(),
                (double) this.automobileParams.getChance() / 100));

        vehicleSpawnerServices.add(new ProbabilisticVehicleSpawnerService(
                new MotorbikeFactory(
                        this.model.getSpawnTimerService(),
                        this.motorbikeParams.getLifeTime(),
                        640, 480),
                this.motorbikeParams.getPeriod(),
                (double) this.motorbikeParams.getChance() / 100));

        this.isSimulationActive.set(true);

        this.model.start(vehicleSpawnerServices);
    }

    public void pauseSimulation() {
        this.model.pause();
        if (this.isShowInfoModal.get()) {
            try {
                new HabitatInfoDialogWindow(this.stage, this).run();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            stopSimulation();
        }
    }

    public void resumeSimulation() {
        this.model.resume();
    }

    public void stopSimulation() {
        this.model.stop();
        this.isSimulationActive.set(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.root.setOnKeyReleased(keyEvent -> {
            switch (keyEvent.getCode()) {
                case B -> startSimulation();
                case E -> pauseSimulation();
                case T -> this.isShowTime.set(!this.isShowTime.get());
            }
        });

        this.menuStartButton.setOnAction(event -> startSimulation());
        this.menuStopButton.setOnAction(event -> pauseSimulation());
        this.startButton.setOnAction(event -> startSimulation());
        this.stopButton.setOnAction(event -> pauseSimulation());

        this.menuStartButton.disableProperty().bind(this.isSimulationActive);
        this.menuStopButton.disableProperty().bind(this.isSimulationActive.not());
        this.startButton.disableProperty().bind(this.isSimulationActive);
        this.stopButton.disableProperty().bind(this.isSimulationActive.not());

        this.menuShowTimeCheckBox.selectedProperty().bindBidirectional(this.isShowTime);
        this.menuShowTimeCheckBox.selectedProperty().addListener(
                (changed, oldValue, newValue) -> this.showTimeToggleGroup.selectToggle(
                        this.isShowTime.get()
                                ? this.showTimeRadioButton
                                : this.dontShowTimeRadioButton
                )
        );
        this.showTimeToggleGroup.selectedToggleProperty().addListener(
                (changed, oldValue, newValue) -> this.isShowTime.set(newValue.equals(this.showTimeRadioButton))
        );

        this.menuShowInfoModalCheckBox.selectedProperty().bindBidirectional(this.isShowInfoModal);
        this.showInfoModalCheckBox.selectedProperty().bindBidirectional(this.isShowInfoModal);

        this.menuAboutButton.setOnAction(event -> {
            try {
                new AboutDialogWindow(this.stage).run();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        this.timeView.visibleProperty().bind(this.isShowTime);

        this.automobileAICheckBox.setOnAction(event -> {
            System.out.println("automobileAICheckBox: " + this.automobileAICheckBox.isSelected());
            if (this.automobileAICheckBox.isSelected()) this.model.resumeAI(Automobile.class);
            else this.model.pauseAI(Automobile.class);
        });
        this.motorbikeAICheckBox.setOnAction(event -> {
            if (this.motorbikeAICheckBox.isSelected()) this.model.resumeAI(Motorbike.class);
            else this.model.pauseAI(Motorbike.class);
        });

        this.automobileAIThreadPriorityComboBox.getItems().setAll(IntStream.rangeClosed(1, 10).boxed().toList());
        this.automobileAIThreadPriorityComboBox.setOnAction(
                event -> this.model.setAIPriority(
                        Automobile.class,
                        this.automobileAIThreadPriorityComboBox.getValue()
                )
        );
        this.motorbikeAIThreadPriorityComboBox.getItems().setAll(IntStream.rangeClosed(1, 10).boxed().toList());
        this.motorbikeAIThreadPriorityComboBox.setOnAction(
                event -> this.model.setAIPriority(
                        Motorbike.class,
                        this.motorbikeAIThreadPriorityComboBox.getValue()
                )
        );
    }

    public HabitatView getHabitatView() {
        return this.habitatView;
    }

    public void setModel(Habitat model) {
        this.model = model;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Stream<VehicleDto> getAllVehicles() {
        return this.model.getAll();
    }

    public TimeView getTimeView() {
        return this.timeView;
    }


}
