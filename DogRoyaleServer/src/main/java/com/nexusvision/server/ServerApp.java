package com.nexusvision.server;

import com.nexusvision.server.controller.ServerController;

/**
 * Starting the server
 *
 * @author felixwr
 */
public class ServerApp {
    private static final int PORT = 8082;

    /**
     * Entry point for the server, sets up controller and connects to port
     *
     * @param args Command-line arguments (Optional).
     */
    public static void main(String[] args) {
        ServerController serverController = ServerController.getInstance();
        serverController.startServer(PORT);
    }
}