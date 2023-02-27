package com.kigo.vehicles;

import com.kigo.vehicles.controller.MainController;
import com.kigo.vehicles.controller.MainControllerFactory;
import com.kigo.vehicles.model.Habitat;
import com.kigo.vehicles.model.repositories.FixedArrayVehicleRepository;
import com.kigo.vehicles.model.repositories.IVehicleRepository;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        IVehicleRepository vehicleRepository = new FixedArrayVehicleRepository(10);
        Habitat model = new Habitat(vehicleRepository, 800, 800);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/kigo/vehicles/fxml/main-view.fxml"));
        fxmlLoader.setControllerFactory(controllerClass -> new MainControllerFactory().getMainController(model));

        Scene scene = new Scene(fxmlLoader.load(), 800, 800);

        final MainController controller = fxmlLoader.getController();
        stage.setOnShown(controller::adjustView);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        System.out.println(javafx.scene.text.Font.getFamilies());
        launch();
    }
}