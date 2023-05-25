package ru.nstu.vehicles.app.model.service;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ServerUpdaterService implements IServerUpdaterService {
    private Socket socket;
    private Thread worker;

    public void connect(InetAddress address, int port) throws IOException {
        this.socket = new Socket(address, port);
        this.worker = new Thread(new Runner());
    }
    private class Runner implements Runnable {
        @Override
        public void run() {

        }
    }
}
