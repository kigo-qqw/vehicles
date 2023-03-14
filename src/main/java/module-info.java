module com.kigo.vehicles {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.kigo.vehicles to javafx.fxml;
    opens com.kigo.vehicles.controller to javafx.fxml;
    opens com.kigo.vehicles.model.dto to java.base;
    opens com.kigo.vehicles.view.components to javafx.fxml;

    exports com.kigo.vehicles;
    exports com.kigo.vehicles.model.dto;
}