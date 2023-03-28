package ru.nstu.vehicles.app.view;

import javafx.stage.Modality;
import javafx.stage.Window;
import ru.nstu.vehicles.app.controller.HabitatInfoDialogWindowController;
import ru.nstu.vehicles.app.controller.MainViewController;

public class HabitatInfoDialogWindow extends DialogWindow {
    public HabitatInfoDialogWindow(Window parentWindow, MainViewController mainViewController) {
        super(HabitatInfoDialogWindow.class.getResource("/ru/nstu/vehicles/app/view/habitat-info-dialog-view.fxml"),
                parentWindow, "Info", Modality.WINDOW_MODAL);
        setControllerFactory(controllerClass ->
                new HabitatInfoDialogWindowController(this, mainViewController));
    }
}
