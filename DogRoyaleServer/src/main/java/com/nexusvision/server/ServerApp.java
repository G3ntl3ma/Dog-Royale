package com.nexusvision.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Die Server-Applikation, die den Server startet
 *
 * @author felixwr
 */
public class ServerApp {
    private static final Logger logger = LogManager.getLogger(ServerApp.class);
    private static final int PORT = 8080;
    private static final ExecutorService executorService = Executors.newFixedThreadPool(100);

    public static void main(String[] args) {
        new ServerApp().startServer(PORT);
    }

    /**
     * Setzt ServerSocket auf und erstellt für eingehende Verbindungen
     * einen Thread und fügt diese zum Threadpool hinzu
     *
     * @param port Der Port mit dem der ServerSocket gestartet wird
     */
    public void startServer(int port) {
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
}
