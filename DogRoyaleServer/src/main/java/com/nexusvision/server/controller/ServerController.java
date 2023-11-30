package com.nexusvision.server.controller;

import com.nexusvision.server.handler.ClientHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.util.ArrayList;

/**
 * Die Server-Applikation, die den Server startet
 *
 * @author felixwr
 */
public class ServerController {
    private static final Logger logger = LogManager.getLogger(ServerController.class);
    private static final int PORT = 8080;
    private static final ExecutorService executorService = Executors.newFixedThreadPool(100);

    private static ArrayList<Integer> clientIDList = new ArrayList<>();
    // private static ArrayList<SpiellogikInstanz> lobbyList = new ArrayList<>();

    public static void main(String[] args) {
        startServer(PORT);
    }

    /**
     * Setzt ServerSocket auf und erstellt für eingehende Verbindungen
     * einen Thread und fügt diese zum Threadpool hinzu
     *
     * @param port Der Port mit dem der ServerSocket gestartet wird
     */
    public static void startServer(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("ServerSocket erfolgreich gestartet unter Port " + port);
            logger.info("Warte auf Verbindungen...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                logger.info("Neue Verbindung akzeptiert von " + clientSocket.getInetAddress());

                executorService.submit(new ClientHandler(clientSocket));
            }
        } catch (IOException e) {
            logger.error(e.getStackTrace());
        }
    }

    public static int generateClientID() {
        // TODO: Create a client ID that is not in clientIDList
        //       put it into the list and return it
        return 0;
    }

    /*
    public static SpiellogikInstanz getLobby(int lobbyID) {
        // Gebe Spiellogik zurück
    }
     */
}
