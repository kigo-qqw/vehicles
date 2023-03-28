package ru.nstu.vehicles.app.view;

import javafx.stage.Modality;
import javafx.stage.Window;
import ru.nstu.vehicles.app.controller.AboutDialogWindowController;

import java.io.IOException;

public class AboutDialogWindow extends DialogWindow {
    public AboutDialogWindow(Window parentWindow) throws IOException {
        super(AboutDialogWindow.class.getResource("/ru/nstu/vehicles/app/view/about-dialog-view.fxml"),
                parentWindow, "About", Modality.NONE);
        setControllerFactory(controllerClass -> new AboutDialogWindowController(this));
    }
}
