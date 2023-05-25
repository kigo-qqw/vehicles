package ru.nstu.vehicles.server;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
//        Server server = new Server(Integer.parseInt(System.getenv("PORT")));
        Server server = new Server(8000);
        server.serveForever();
    }
}
