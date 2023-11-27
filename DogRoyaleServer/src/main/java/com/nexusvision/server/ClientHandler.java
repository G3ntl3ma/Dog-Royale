package com.nexusvision.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * ClientHandler, der sich dem übergebenem ClientSocket widmet
 * und mit diesem kommuniziert
 *
 * @author felixwr
 */
public class ClientHandler implements Runnable {
    private static final Logger logger = LogManager.getLogger(ClientHandler.class);
    private final Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter writer = new PrintWriter(
                    clientSocket.getOutputStream(), true);

            String clientMessage;
            while ((clientMessage = reader.readLine()) != null) {
                // TODO: MessageHandling
                logger.info("Von Client wurde folgendes empfangen: " + clientMessage);

                writer.println("Nachricht erhalten");
            }

        } catch (IOException e) {
            logger.error(e.getStackTrace());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                logger.error(e.getStackTrace());
            }
        }
    }
}
