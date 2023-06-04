package ru.nstu.vehicles.server;

import ru.nstu.vehicles.app.dto.ExistingConnectionsDto;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.stream.Collectors;

public class Session implements Runnable {
    private final Socket socket;
    private final Server server;
    private final ObjectInputStream in;
    private final ObjectOutputStream out;
    private final Dispatcher dispatcher;
//    private ExistingConnectionsDto startMessage;

    public Session(Socket socket, Server server, Dispatcher dispatcher) throws IOException {
        this.socket = socket;
        this.server = server;
        this.in = new ObjectInputStream(socket.getInputStream());
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.dispatcher = dispatcher;
    }

//    public void setStartMessage(ExistingConnectionsDto startMessage) {
//        this.startMessage = startMessage;
//    }

    public void run() {
        try {
            Thread.sleep(1000);

            this.out.writeObject(new ExistingConnectionsDto(
                    new HashMap<>(this.dispatcher.getSessions().stream().collect(Collectors.toMap(
                            Session::getInetSocketAddress,
                            s -> this.dispatcher.getVehicleRepository().get(s.getInetSocketAddress()).isPresent()
                    )))
            ));
            while (true) {
                Object data = this.in.readObject();
                this.dispatcher.dispatch(this, data);
            }
        } catch (IOException | ClassNotFoundException ignored) {
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                this.server.disconnect((InetSocketAddress) this.socket.getRemoteSocketAddress());
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
            this.server.disconnect((InetSocketAddress) this.socket.getRemoteSocketAddress());
        }
    }

    public InetSocketAddress getInetSocketAddress() {
        return (InetSocketAddress)this.socket.getRemoteSocketAddress();
//        return new InetSocketAddress(this.socket.getInetAddress(), this.socket.getPort());
    }
}
