package ru.nstu.vehicles.app.view;

import javafx.stage.Modality;
import javafx.stage.Window;
import ru.nstu.vehicles.app.controller.ConsoleDialogWindowController;
import ru.nstu.vehicles.app.controller.MainViewController;

public class ConsoleDialogWindow extends DialogWindow {
    public ConsoleDialogWindow(Window parentWindow, MainViewController mainViewController) {
        super(AboutDialogWindow.class.getResource("/ru/nstu/vehicles/app/view/console-dialog-view.fxml"),
                parentWindow, "Console", Modality.NONE);
        setControllerFactory(controllerClass -> new ConsoleDialogWindowController(this, mainViewController));
    }
}
