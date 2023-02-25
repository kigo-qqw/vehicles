module com.kigo.vehicles {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.kigo.vehicles to javafx.fxml;
    opens com.kigo.vehicles.controller to javafx.fxml;
    exports com.kigo.vehicles;
}