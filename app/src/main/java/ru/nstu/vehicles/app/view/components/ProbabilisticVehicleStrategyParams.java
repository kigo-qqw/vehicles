package ru.nstu.vehicles.app.view.components;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.stream.IntStream;

public class ProbabilisticVehicleStrategyParams extends VBox {
    private final Label prompt;
    private final NumberField lifeTime;
    private final NumberField period;
    private final ComboBox<Integer> chance;

    public ProbabilisticVehicleStrategyParams() {
        this("", 10000, 1000, 100);
    }

    public ProbabilisticVehicleStrategyParams(String prompt, long lifeTime, int period, int chance) {
        this.prompt = new Label();
        this.lifeTime = new NumberField();
        this.period = new NumberField();
        this.chance = new ComboBox<>();

        setPrompt(prompt);
        setLifeTime(lifeTime);
        setPeriod(period);

        this.chance.getItems().setAll(IntStream.rangeClosed(0, 10).map(element -> 10 * element).boxed().toList());
        setChance(chance);

        getChildren().addAll(this.prompt, this.lifeTime, this.period, this.chance);
    }

    public int getPeriod() {
        return Integer.parseInt(this.period.getText());
    }

    public void setPeriod(int period) {
        this.period.setText(String.valueOf(period));
    }

    public int getChance() {
        return this.chance.getValue();
    }

    public void setChance(int chance) {
        this.chance.setValue(chance);
    }

    public String getPrompt() {
        return this.prompt.getText();
    }

    public void setPrompt(String prompt) {
        this.prompt.setText(prompt);
    }

    public long getLifeTime() {
        return Long.parseLong(this.lifeTime.getText());
    }

    public void setLifeTime(long lifeTime) {
        this.lifeTime.setText(String.valueOf(lifeTime));
    }
}
