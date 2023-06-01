package ru.nstu.vehicles.app.model.service;

import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import ru.nstu.vehicles.app.controller.MainViewController;
import ru.nstu.vehicles.app.dto.*;
import ru.nstu.vehicles.app.model.Habitat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ServerUpdaterService implements IServerUpdaterService {
    private Socket socket;
    private Thread worker;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private ObservableMap<InetSocketAddress, Boolean> addresses;
    private Habitat model;

    public void connect(InetAddress address, int port) throws IOException {
        this.socket = new Socket(address, port);

        this.oos = new ObjectOutputStream(this.socket.getOutputStream());
        this.ois = new ObjectInputStream(this.socket.getInputStream());

        this.worker = new Thread(new Runner());
        this.worker.start();
    }

    @Override
    public boolean isConnected() {
        return this.socket.isConnected();
    }

    @Override
    public void bindConnectedMap(ObservableMap<InetSocketAddress, Boolean> addresses) {
        this.addresses = addresses;
    }

    @Override
    public void setModel(Habitat model) {
        this.model = model;
    }

    @Override
    public InetSocketAddress getInetSocketAddress() {
//        return new InetSocketAddress(this.socket.getInetAddress(), this.socket.getPort());
//        return (InetSocketAddress) this.socket.getRemoteSocketAddress();
        return (InetSocketAddress) this.socket.getLocalSocketAddress();
    }

    @Override
    public void send(Object object) throws IOException {
        this.oos.writeObject(object);
    }

    private class Runner implements Runnable {
        private boolean isRun = true;

        public void stop() {
            this.isRun = false;
        }

        @Override
        public void run() {
            while (isRun) {
                try {
                    Object object = ois.readObject();

                    if (object instanceof ExistingConnectionsDto existingConnectionsDto) {
                        addresses.clear();
                        addresses.putAll(existingConnectionsDto.addresses());
                    }
                    if (object instanceof NewConnectionDto newConnectionDto) {
                        addresses.put(newConnectionDto.address(), false);
                    }
                    if (object instanceof DropConnectionDto dropConnectionDto) {
                        addresses.remove(dropConnectionDto.address());
                    }
                    if (object instanceof UploadVehiclesNotificationDto uploadVehiclesNotificationDto) {
                        addresses.put(uploadVehiclesNotificationDto.address(), true);
                    }
                    if (object instanceof DownloadVehiclesResponseDto downloadVehiclesResponseDto) {
                        System.out.println("downloaded");
                        model.setVehicles(downloadVehiclesResponseDto.vehicles().stream().map(
                                vehicleDto -> model.getVehicleService()
                                        .get(vehicleDto, 640, 480))
                        );
                    }
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
