package ru.nstu.vehicles.app.model.service;

import ru.nstu.vehicles.app.model.entities.Vehicle;
import ru.nstu.vehicles.app.model.service.ais.BaseAI;

import java.util.HashMap;
import java.util.Map;

public class AIService implements IAIService {
    private final Map<Class<? extends Vehicle>, BaseAI> ais = new HashMap<>();

    @Override
    public <T extends Vehicle, AI extends BaseAI> void use(Class<T> type, AI ai) {
        this.ais.put(type, ai);
        ai.start();
        ai.deactivate();

        this.ais.forEach((aClass, baseAI) -> System.out.println("aClass:" + aClass + " baseAI: " + baseAI));
    }

    @Override
    public <T extends Vehicle> void pause(Class<T> type) {
        if (ais.containsKey(type)) ais.get(type).deactivate();
    }

    @Override
    public <T extends Vehicle> void resume(Class<T> type) {
        System.out.println("resume " + type.getName());
        if (ais.containsKey(type)) ais.get(type).activate();
    }

    @Override
    public <T extends Vehicle> void setPriority(Class<T> type, int priority) {
        this.ais.get(type).setPriority(priority);
    }
}
