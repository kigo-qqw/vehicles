package ru.nstu.vehicles.app.view.components;

import javafx.scene.control.Label;

import java.text.DecimalFormat;

public class TimeView extends Label implements ITimeView {
    private final DecimalFormat format = new DecimalFormat("0.0");

    @Override
    public void setTime(long time) {
        setText("Time: " + this.format.format((double) time / 1000));
    }
}
