package com.kigo.vehicles.view.components;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.converter.IntegerStringConverter;

public class NumberField extends TextField {
    public NumberField() {
        setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));
    }
}
