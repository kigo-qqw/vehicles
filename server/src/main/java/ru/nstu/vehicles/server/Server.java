package ru.nstu.vehicles.server;

import ru.nstu.vehicles.app.dto.DropConnectionDto;
import ru.nstu.vehicles.app.dto.ExistingConnectionsDto;
import ru.nstu.vehicles.app.dto.NewConnectionDto;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class Server {
    private final ServerSocket socket;
    private final List<Session> sessions = new LinkedList<>();

    public Server(int port) throws IOException {
        this.socket = new ServerSocket(port);
    }

    public void disconnect(InetAddress address) {
        synchronized (this.sessions) {
            this.sessions.forEach(session -> session.send(new DropConnectionDto(address)));
        }
    }

    public void serveForever() throws IOException {
        boolean run = true;
        while (run) {
            try {
                Socket clientSocket = this.socket.accept();
                Dispatcher dispatcher = new Dispatcher();

                synchronized (this.sessions) {
                    Session session = new Session(clientSocket, this, dispatcher);
                    this.sessions.forEach(s -> s.send(new NewConnectionDto(clientSocket.getInetAddress())));
                    this.sessions.add(session);
                    session.send(new ExistingConnectionsDto(this.sessions.stream().map(Session::getInetAddress).toList()));
                    new Thread(session).start();
                }
            } catch (IOException e) {
                run = false;
            }
        }
        socket.close();
    }
}
