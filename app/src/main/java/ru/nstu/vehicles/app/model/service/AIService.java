package ru.nstu.vehicles.app.model.service;

import ru.nstu.vehicles.app.model.service.ais.BaseAI;

import java.util.HashMap;
import java.util.Map;

public class AIService implements IAIService {
    private final Map<Class<? extends BaseAI>, BaseAI> ais = new HashMap<>();

    @Override
    public <AI extends BaseAI> void use(Class<AI> type, AI ai) {
        ais.put(type, ai);
        ai.start();
        ai.deactivate();
    }

    @Override
    public <AI extends BaseAI> void pause(Class<AI> type) {
        if (ais.containsKey(type)) ais.get(type).deactivate();
    }

    @Override
    public <AI extends BaseAI> void resume(Class<AI> type) {
        System.out.println("resume " + type.getName());
        if (ais.containsKey(type)) ais.get(type).activate();
    }
}
