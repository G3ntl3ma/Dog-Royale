package com.nexusvision.client;

import com.nexusvision.config.AppConfig;
import com.nexusvision.server.ServerApp;
import com.nexusvision.server.controller.ServerController;
import lombok.extern.log4j.Log4j2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

@Log4j2
public class Ausrichter {

    private static final int AUSRICHTER_PORT = Integer.parseInt(AppConfig.getInstance().getProperty("ausrichterPort"));
    private Scanner scanner;

    public Ausrichter() {
        this.scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        Ausrichter ausrichter = new Ausrichter();
        ausrichter.start();
    }

    public void start() {
        log.info("Starting Ausrichter...");

        String prefix = "ausrichter@localhost:" + AUSRICHTER_PORT + "$ ";
        try (Socket socket = new Socket("localhost", AUSRICHTER_PORT);
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            boolean running = true;
            while (running) {
                System.out.print(prefix);
                String input = scanner.nextLine();

                if (input.equalsIgnoreCase("exit")) {
                    running = false;
                } else {
                    writer.println(input);

                    StringBuilder outputBuilder = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if (line.isEmpty()) break;
                        outputBuilder.append(line).append("\n");
                    }

                    String output = outputBuilder.toString();
                    System.out.println(output);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        scanner.close();
        log.info("Ending Ausrichter");
    }
}
