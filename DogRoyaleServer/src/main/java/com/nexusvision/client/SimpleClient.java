package com.nexusvision.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Reine Testklasse, die noch keine richtige Funktionalität bieten soll
 *
 * TODO: Als Test formalisieren
 */
public class SimpleClient {
    private static final Logger logger = LogManager.getLogger(SimpleClient.class);
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 8080;

    public static void main(String[] args) {
        try {
            // Verbindung zum Server herstellen
            Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            logger.info("Verbunden mit Server: " + socket.getInetAddress());

            // Input- und Output-Streams für die Verbindung
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            // Daten an den Server senden
            writer.println("Hallo, Server!");

            // Daten vom Server empfangen
            String serverMessage = reader.readLine();
            logger.info("Nachricht vom Server: " + serverMessage);

            // Verbindung schließen
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
