package com.nexusvision.server.controller;

import com.nexusvision.client.CommandProcessor;
import com.nexusvision.config.AppConfig;
import lombok.extern.log4j.Log4j2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

@Log4j2
public class AusrichterController {
    private static AusrichterController instance;
    private static final int AUSRICHTER_PORT = Integer.parseInt(AppConfig.getInstance().getProperty("ausrichterPort"));

    private ServerSocket ausrichterServerSocket;

    private AusrichterController() {}

    public static AusrichterController getInstance() {
        if (instance == null) {
            instance = new AusrichterController();
        }
        return instance;
    }

    public void startAusrichter() {
        try {
            ausrichterServerSocket = new ServerSocket(AUSRICHTER_PORT);
            Thread ausrichterThread = new Thread(runAusrichter(ausrichterServerSocket));
            ausrichterThread.setName("AusrichterThread");
            ausrichterThread.start();
        } catch (IOException e) {
            log.error("Ausrichter unable to start");
        }
    }

    private static Runnable runAusrichter(ServerSocket ausrichterServerSocket) {
        return () -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    Socket ausrichterSocket = ausrichterServerSocket.accept();
                    log.info("Ausrichter connected successfully");
                    handleAusrichter(ausrichterSocket);
                }
            } catch (IOException e) {
                log.error("Ausrichter just died...");
                log.error("Ausrichter service not available anymore");
            }
        };
    }

    private static void handleAusrichter(Socket ausrichterSocket) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(ausrichterSocket.getInputStream()));
             PrintWriter writer = new PrintWriter(ausrichterSocket.getOutputStream(), false);
        ) {
            String command;
            CommandProcessor commandProcessor = new CommandProcessor();
            while ((command = reader.readLine()) != null) {
                writer.write(commandProcessor.processCommand(command));
                writer.println(); // empty line to end message
                writer.flush();
            }
        } catch (IOException e) {
            log.error("Ausrichter disconnected: " + e.getMessage());
        } finally {
            try {
                ausrichterSocket.close();
            } catch (IOException e) {
                log.error("Error while trying to close the connection: " + e.getMessage());
            }
        }
    }
}
