package ru.nstu.vehicles.app;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.nstu.vehicles.app.controller.MainViewController;
import ru.nstu.vehicles.app.model.Habitat;
import ru.nstu.vehicles.app.model.repository.IVehicleRepository;
import ru.nstu.vehicles.app.model.repository.VehicleRepository;
import ru.nstu.vehicles.app.model.service.*;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException, SQLException {
        Properties appConfig = new Properties();
        appConfig.load(Objects.requireNonNull(getClass().getResource("/ru/nstu/vehicles/application.properties")).openStream());
        IServerUpdaterService serverUpdaterService = new ServerUpdaterService();


        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ru/nstu/vehicles/app/view/main-view.fxml"));
        fxmlLoader.setControllerFactory(controllerClass -> new MainViewController(appConfig, serverUpdaterService));
        Scene scene = new Scene(fxmlLoader.load(), 1080, 720);

        MainViewController controller = fxmlLoader.getController();

        System.out.println(appConfig.getProperty("jdbc.url"));
        Connection connection = DriverManager.getConnection(appConfig.getProperty("jdbc.url"));
        IVehicleRepository vehicleRepository = new VehicleRepository(connection);
        IVehicleService vehicleService = new VehicleService();
        Habitat model = new Habitat(
                controller.getHabitatView(),
                controller.getTimeView(),
                vehicleRepository,
                vehicleService,
                new RedrawTimerService(vehicleRepository, vehicleService),
                new SpawnTimerService(vehicleRepository),
                appConfig
        );
        controller.setModel(model);
        controller.setStage(primaryStage);

        primaryStage.setOnCloseRequest(event -> {
            try {
                appConfig.store(
                        new FileWriter(
                                Objects.requireNonNull(
                                        getClass().getResource("/ru/nstu/vehicles/application.properties")
                                ).getFile()
                        ), "xdd");
                System.out.println("EXIT" + appConfig);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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
