package ru.nstu.vehicles.app.view.components;

import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class NumberField extends TextField {
    public NumberField() {
        addEventFilter(KeyEvent.KEY_TYPED, event -> {
            String s = event.getCharacter();
            char c = s.charAt(s.length() - 1);
            if (c < '0' || c > '9') event.consume();
        });
    }
}
