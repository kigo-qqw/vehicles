package ru.nstu.vehicles.app.model.service;

import ru.nstu.vehicles.app.model.service.ais.BaseAI;

public interface IAIService {
    <AI extends BaseAI>void use(Class<AI> type, AI ai);
    <AI extends BaseAI>void pause(Class<AI> type);
    <AI extends BaseAI>void resume(Class<AI> type);

}
