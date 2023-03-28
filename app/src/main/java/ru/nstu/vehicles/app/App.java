package ru.nstu.vehicles.app;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.nstu.vehicles.app.controller.MainViewController;
import ru.nstu.vehicles.app.model.Habitat;
import ru.nstu.vehicles.app.model.repository.VehicleRepository;
import ru.nstu.vehicles.app.model.service.TimerService;
import ru.nstu.vehicles.app.model.service.VehicleService;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ru/nstu/vehicles/app/view/main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1080, 720);

        MainViewController controller = fxmlLoader.getController();
        Habitat model = new Habitat(
                controller.getHabitatView(),
                controller.getTimeView(),
                new VehicleRepository(),
                new VehicleService(),
                new TimerService()
        );
        controller.setModel(model);
        controller.setStage(primaryStage);

        primaryStage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
