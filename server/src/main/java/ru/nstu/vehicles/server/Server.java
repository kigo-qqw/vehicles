package ru.nstu.vehicles.server;

import ru.nstu.vehicles.app.dto.DropConnectionDto;
import ru.nstu.vehicles.app.dto.ExistingConnectionsDto;
import ru.nstu.vehicles.app.dto.NewConnectionDto;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Server {
    private final ServerSocket socket;
    private final List<Session> sessions = new LinkedList<>();

    public Server(int port) throws IOException {
        this.socket = new ServerSocket(port);
    }

    public void disconnect(InetSocketAddress address) {
        this.sessions.removeIf(session -> session.getInetSocketAddress().equals(address));
        synchronized (this.sessions) {
            this.sessions.forEach(session -> session.send(new DropConnectionDto(address)));
        }
    }

    public void serveForever() throws IOException {
        VehicleRepository vehicleRepository = new VehicleRepository();
        Dispatcher dispatcher = new Dispatcher(this.sessions, vehicleRepository);
        boolean run = true;
        while (run) {
            try {
                Socket clientSocket = this.socket.accept();

                synchronized (this.sessions) {
                    Session session = new Session(
                            clientSocket,
                            this,
                            dispatcher);
                    this.sessions.add(session);
//                    session.setStartMessage(new ExistingConnectionsDto(
////                            this.sessions.stream().map(Session::getInetSocketAddress).toList()
//                            this.sessions.stream().collect(Collectors.toMap(
//                                    Session::getInetSocketAddress,
//                                    s ->
//                            ))
//                    ));
                    this.sessions.forEach(s -> s.send(new NewConnectionDto(new InetSocketAddress(
                            clientSocket.getInetAddress(), clientSocket.getPort()))));

                    new Thread(session).start();
                }
            } catch (IOException e) {
                run = false;
            }
        }
        socket.close();
    }
}
