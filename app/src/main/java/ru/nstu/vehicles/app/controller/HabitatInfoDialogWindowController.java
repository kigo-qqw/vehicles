package ru.nstu.vehicles.app.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import ru.nstu.vehicles.app.controller.dto.ObservableVehicleDto;
import ru.nstu.vehicles.app.model.dto.VehicleDto;
import ru.nstu.vehicles.app.view.HabitatInfoDialogWindow;
import ru.nstu.vehicles.app.view.components.TimeView;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

public class HabitatInfoDialogWindowController implements IController {
    @FXML
    private TimeView timeView;
    @FXML
    private Label automobileCountLabel;
    @FXML
    private Label motorbikeCountLabel;
    @FXML
    private TableView<ObservableVehicleDto> tableView;
    @FXML
    private TableColumn<ObservableVehicleDto, Long> birthTimeTableColumn;
    @FXML
    private TableColumn<ObservableVehicleDto, String> typeTableColumn;
    @FXML
    private TableColumn<ObservableVehicleDto, UUID> uuidTableColumn;
    @FXML
    private TableColumn<ObservableVehicleDto, Long> lifeTimeTableColumn;
    @FXML
    private TableColumn<ObservableVehicleDto, Double> xTableColumn;
    @FXML
    private TableColumn<ObservableVehicleDto, Double> yTableColumn;
    @FXML
    private Button resumeButton;
    @FXML
    private Button stopButton;

    private final HabitatInfoDialogWindow habitatInfoDialogWindow;
    private final MainViewController mainViewController;

    public HabitatInfoDialogWindowController(HabitatInfoDialogWindow habitatInfoDialogWindow, MainViewController mainViewController) {
        this.habitatInfoDialogWindow = habitatInfoDialogWindow;
        this.mainViewController = mainViewController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resumeButton.setOnAction(event -> {
            this.mainViewController.resumeSimulation();
            this.habitatInfoDialogWindow.close();
        });
        this.stopButton.setOnAction(event -> {
            this.mainViewController.stopSimulation();
            this.habitatInfoDialogWindow.close();
        });

        this.birthTimeTableColumn.setCellValueFactory(cellDataFeatures -> cellDataFeatures.getValue().birthTimeProperty().asObject());
        this.typeTableColumn.setCellValueFactory(cellDataFeatures -> cellDataFeatures.getValue().typeProperty());
        this.uuidTableColumn.setCellValueFactory(cellDataFeatures -> cellDataFeatures.getValue().uuidProperty());
        this.lifeTimeTableColumn.setCellValueFactory(cellDataFeatures -> cellDataFeatures.getValue().lifeTimeProperty().asObject());
        this.xTableColumn.setCellValueFactory(cellDataFeatures -> cellDataFeatures.getValue().xProperty().asObject());
        this.yTableColumn.setCellValueFactory(cellDataFeatures -> cellDataFeatures.getValue().yProperty().asObject());
        this.tableView.setItems(FXCollections.observableList(this.mainViewController
                .getAllVehicles()
                .map(ObservableVehicleDto::new)
                .toList()));

        this.timeView.setText(this.mainViewController.getTimeView().getText());
        this.automobileCountLabel.setText("Automobiles: " + this.mainViewController
                .getAllVehicles()
                .filter(vehicleDto -> vehicleDto.type() == VehicleDto.Type.AUTOMOBILE)
                .count());
        this.motorbikeCountLabel.setText("Motorbikes: " + this.mainViewController
                .getAllVehicles()
                .filter(vehicleDto -> vehicleDto.type() == VehicleDto.Type.MOTORBIKE)
                .count());
    }
}
