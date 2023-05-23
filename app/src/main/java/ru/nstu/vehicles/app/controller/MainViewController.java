package ru.nstu.vehicles.app.controller;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
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
import ru.nstu.vehicles.app.view.ConsoleDialogWindow;
import ru.nstu.vehicles.app.view.HabitatInfoDialogWindow;
import ru.nstu.vehicles.app.view.components.HabitatView;
import ru.nstu.vehicles.app.view.components.ProbabilisticVehicleStrategyParams;
import ru.nstu.vehicles.app.view.components.TimeView;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
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
    @FXML
    private Button openConsoleButton;
    @FXML
    private Button saveVehiclesButton;
    @FXML
    private Button loadVehiclesButton;
    private Habitat model = null;
    private final BooleanProperty isSimulationActive = new SimpleBooleanProperty(false);
    private final BooleanProperty isShowTime = new SimpleBooleanProperty(true);
    private final BooleanProperty isShowInfoModal = new SimpleBooleanProperty(true);
    private Stage stage;
    private final Properties appConfig;

    public MainViewController(Properties appConfig) {
        this.appConfig = appConfig;
    }

    public void startSimulation() {
        List<IVehicleSpawnerService> vehicleSpawnerServices = new ArrayList<>();

        vehicleSpawnerServices.add(new ProbabilisticVehicleSpawnerService(
                new AutomobileFactory(
                        this.model.getSpawnTimerService(),
                        this.automobileParams.getLifeTime(),
                        640, 480),
                this.automobileParams.getPeriod(),
                (double) this.automobileParams.getChance() / 100));
        this.appConfig.setProperty("vehicle.automobile.spawnPeriod", String.valueOf(this.automobileParams.getPeriod()));
        this.appConfig.setProperty("vehicle.automobile.lifeTime", String.valueOf(this.automobileParams.getLifeTime()));
        this.appConfig.setProperty("vehicle.automobile.spawnProbability", String.valueOf(this.automobileParams.getChance()));

        vehicleSpawnerServices.add(new ProbabilisticVehicleSpawnerService(
                new MotorbikeFactory(
                        this.model.getSpawnTimerService(),
                        this.motorbikeParams.getLifeTime(),
                        640, 480),
                this.motorbikeParams.getPeriod(),
                (double) this.motorbikeParams.getChance() / 100));
        this.appConfig.setProperty("vehicle.motorbike.spawnPeriod", String.valueOf(this.motorbikeParams.getPeriod()));
        this.appConfig.setProperty("vehicle.motorbike.lifeTime", String.valueOf(this.motorbikeParams.getLifeTime()));
        this.appConfig.setProperty("vehicle.motorbike.spawnProbability", String.valueOf(this.motorbikeParams.getChance()));

        this.isSimulationActive.set(true);

        System.out.println(this.appConfig);
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
            if (this.automobileAICheckBox.isSelected()) this.model.resumeAI(Automobile.class);
            else this.model.pauseAI(Automobile.class);

            this.appConfig.setProperty("vehicle.automobile.uesAI", String.valueOf(this.automobileAICheckBox.isSelected()));
        });
        this.motorbikeAICheckBox.setOnAction(event -> {
            if (this.motorbikeAICheckBox.isSelected()) this.model.resumeAI(Motorbike.class);
            else this.model.pauseAI(Motorbike.class);

            this.appConfig.setProperty("vehicle.motorbike.uesAI", String.valueOf(this.motorbikeAICheckBox.isSelected()));
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

        this.automobileParams.setPeriod(Integer.parseInt(this.appConfig.getProperty("vehicle.automobile.spawnPeriod")));
        this.automobileParams.setLifeTime(Integer.parseInt(this.appConfig.getProperty("vehicle.automobile.lifeTime")));
        this.automobileParams.setChance(Integer.parseInt(this.appConfig.getProperty("vehicle.automobile.spawnProbability")));
        this.automobileAICheckBox.setSelected(Boolean.parseBoolean(this.appConfig.getProperty("vehicle.automobile.spawnProbability")));
        this.motorbikeParams.setPeriod(Integer.parseInt(this.appConfig.getProperty("vehicle.motorbike.spawnPeriod")));
        this.motorbikeParams.setLifeTime(Integer.parseInt(this.appConfig.getProperty("vehicle.motorbike.lifeTime")));
        this.motorbikeParams.setChance(Integer.parseInt(this.appConfig.getProperty("vehicle.motorbike.spawnProbability")));
        this.motorbikeAICheckBox.setSelected(Boolean.parseBoolean(this.appConfig.getProperty("vehicle.motorbike.spawnProbability")));

        this.openConsoleButton.setOnAction(event -> {
            try {
                new ConsoleDialogWindow(this.stage, this).run();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        FileChooser fileSaveChooser = new FileChooser();
        fileSaveChooser.setTitle("Save");
        fileSaveChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Vehicles", "*.vehicles"));

        FileChooser fileOpenChooser = new FileChooser();
        fileOpenChooser.setTitle("Open");
        fileOpenChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Vehicles", "*.vehicles"));

        this.saveVehiclesButton.setOnAction(event -> {
            File file = fileSaveChooser.showSaveDialog(this.stage);
            try {
                new ObjectOutputStream(new FileOutputStream(file)).writeObject(this.getAllVehicles().collect(Collectors.toCollection(ArrayList::new)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        this.loadVehiclesButton.setOnAction(event -> {
            File file = fileOpenChooser.showOpenDialog(this.stage);
            try {
                ArrayList<VehicleDto> vehicleDtos = (ArrayList<VehicleDto>) new ObjectInputStream(new FileInputStream(file)).readObject();
                this.model.setVehicles(vehicleDtos.stream().map(
                        vehicleDto -> this.model.getVehicleService().get(vehicleDto, 640, 480)));
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

        });
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

    public int deleteMotorbikes(int percent) {
        return this.model.deleteMotorbikes(percent);
    }
}
