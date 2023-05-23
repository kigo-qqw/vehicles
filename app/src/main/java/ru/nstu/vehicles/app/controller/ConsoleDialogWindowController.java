package ru.nstu.vehicles.app.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import ru.nstu.vehicles.app.view.ConsoleDialogWindow;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class ConsoleDialogWindowController implements IController {
    private final ConsoleDialogWindow consoleDialogWindow;
    private final MainViewController mainViewController;
    private final Pattern killMotorbikesPattern = Pattern.compile("killMotorbikes \\d+%");
    @FXML
    private TextArea outputTextArea;
    @FXML
    private TextField commandTextField;
    @FXML
    private Button submitButton;
    @FXML
    private Button closeButton;


    public ConsoleDialogWindowController(ConsoleDialogWindow consoleDialogWindow, MainViewController mainViewController) {
        this.consoleDialogWindow = consoleDialogWindow;
        this.mainViewController = mainViewController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.outputTextArea.setEditable(false);
        this.closeButton.setOnAction(event -> this.consoleDialogWindow.close());
        this.submitButton.setOnAction(event -> onSubmit());
    }

    private void onSubmit() {
        String rawCommand = this.commandTextField.getText().trim();
        this.commandTextField.clear();

        if (this.killMotorbikesPattern.matcher(rawCommand).matches()) {
            String arg = rawCommand.split(" ")[1].trim();
            int percent = Integer.parseInt(arg.substring(0, arg.length() - 1));

            int amount = this.mainViewController.deleteMotorbikes(percent);
            String output = rawCommand + "\nDeleted: " + amount + "\n";
            this.outputTextArea.setText(this.outputTextArea.getText() + output);
        }
    }
}
