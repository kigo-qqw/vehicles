package ru.nstu.vehicles.app.model.service;

import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import ru.nstu.vehicles.app.controller.MainViewController;
import ru.nstu.vehicles.app.model.Habitat;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;

public interface IServerUpdaterService {
    void connect(InetAddress address, int port) throws IOException;

    boolean isConnected();

    void bindConnectedMap(ObservableMap<InetSocketAddress, Boolean> addresses);

    void setModel(Habitat model);
    InetSocketAddress getInetSocketAddress();

    void send(Object object) throws IOException;
}
