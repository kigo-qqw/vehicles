package com.kigo.vehicles.controller;

import com.kigo.vehicles.model.dto.VehicleDto;
import com.kigo.vehicles.model.mappers.IVehicleMapper;
import com.kigo.vehicles.model.repositories.IVehicleRepository;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class HabitatInfoController implements Initializable {
    private final Stage stage;
    private final IVehicleRepository vehicleRepository;
    private final IVehicleMapper vehicleMapper;
    @FXML
    private TableColumn<VehicleDto, String> typeTableColumn;
    @FXML
    private TableColumn<VehicleDto, Double> xTableColumn;
    @FXML
    private TableColumn<VehicleDto, Double> yTableColumn;
    @FXML
    private TableView<VehicleDto> tableView;

    public HabitatInfoController(Stage stage, IVehicleRepository vehicleRepository, IVehicleMapper vehicleMapper) {
        this.stage = stage;
        this.vehicleRepository = vehicleRepository;
        this.vehicleMapper = vehicleMapper;
    }

    @FXML
    void close() {
        stage.close();
    }

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        typeTableColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        xTableColumn.setCellValueFactory(new PropertyValueFactory<>("x"));
        yTableColumn.setCellValueFactory(new PropertyValueFactory<>("y"));

        tableView.setItems(FXCollections.observableList(
                vehicleRepository.getAll()
                        .map(vehicleMapper::convert)
                        .toList()
        ));
    }
}
