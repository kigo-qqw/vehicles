package ru.nstu.vehicles.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Session implements Runnable {
    private final Socket socket;
    private final Server server;
    private final ObjectInputStream in;
    private final ObjectOutputStream out;
    private final Dispatcher dispatcher;

    public Session(Socket socket, Server server, Dispatcher dispatcher) throws IOException {
        this.socket = socket;
        this.server = server;
        this.in = new ObjectInputStream(socket.getInputStream());
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.dispatcher = dispatcher;
    }

    public void run() {
        try {
            while (true) {
                Object data = this.in.readObject();
                this.dispatcher.dispatch(this, data);
            }
        } catch (IOException | ClassNotFoundException ignored) {
        } finally {
            try {
                this.server.disconnect(socket.getInetAddress());
                this.in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void send(Object object) {
        try {
            this.out.writeObject(object);
        } catch (IOException e) {
            this.server.disconnect(socket.getInetAddress());
            throw new RuntimeException(e);
        }
    }

    public InetAddress getInetAddress(){
        return this.socket.getInetAddress();
    }
}
