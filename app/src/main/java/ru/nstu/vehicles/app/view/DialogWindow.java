package ru.nstu.vehicles.app.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;

public abstract class DialogWindow extends Stage {
    private final Window parentWindow;
    private final String title;
    private final Modality modality;
    private final FXMLLoader fxmlLoader;

    public DialogWindow(URL fxmlResource,  Window parentWindow, String title, Modality modality) {
        this.parentWindow = parentWindow;
        this.title = title;
        this.modality = modality;
        this.fxmlLoader = new FXMLLoader(fxmlResource);
    }

    public void setControllerFactory(Callback<Class<?>, Object> controllerFactory) {
        fxmlLoader.setControllerFactory(controllerFactory);
    }

    public void run() throws IOException {
        setScene(new Scene(fxmlLoader.load()));
        setTitle(this.title);
        initModality(this.modality);
        initOwner(this.parentWindow);
        show();
    }
}