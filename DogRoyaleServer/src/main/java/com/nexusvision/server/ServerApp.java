package com.nexusvision.server;

import com.nexusvision.server.controller.ServerController;

public class ServerApp {
    private static final int PORT = 8082;

    public static void main(String[] args) {
        ServerController serverController = ServerController.getInstance();
        serverController.startServer(PORT);
    }
}