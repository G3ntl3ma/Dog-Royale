package com.nexusvision.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Die Client-Applikation, die eine Verbindung zum Server herstellt und Nachrichten sendet
 * und empfängt
 */
public class Client {
    private static final Logger logger = LogManager.getLogger(Client.class);
    private static final String SERVER_ADDRESS = "127.0.0.1"; // IP-Adresse des Servers
    private static final int SERVER_PORT = 8080; // Port des Servers

    public static void main(String[] args) {
        new Client().startClient();
    }

    /**
     * Startet den Client und verbindet sich mit dem Server
     */
    public void startClient() {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT)) {
            logger.info("Verbunden mit Server: " + SERVER_ADDRESS + ":" + SERVER_PORT);

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

            // Kontinuierliche Eingabe von Nachrichten vom Benutzer und Senden an den Server
            while (true) {
                System.out.print("Geben Sie eine Nachricht ein (oder 'exit' zum Beenden): ");
                String userInput = consoleReader.readLine();

                if (userInput.equalsIgnoreCase("exit")) {
                    break;
                }

                writer.println(userInput); // Nachricht an den Server senden

                String serverResponse = reader.readLine(); // Auf Antwort des Servers warten
                logger.info("Antwort vom Server: " + serverResponse);
            }
        } catch (IOException e) {
            logger.error("Fehler bei der Kommunikation mit dem Server: " + e.getMessage());
        }
    }
}
