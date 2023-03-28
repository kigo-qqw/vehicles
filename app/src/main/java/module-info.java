module ru.nstu.vehicles.app {
    requires javafx.controls;
    requires javafx.fxml;

//    opens ru.nstu.vehicles.app to javafx.fxml;
    opens ru.nstu.vehicles.app.controller to javafx.fxml;
    opens ru.nstu.vehicles.app.view.components to javafx.fxml;

    exports ru.nstu.vehicles.app;
}