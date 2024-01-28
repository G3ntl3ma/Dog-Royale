package com.nexusvision.client;

import com.nexusvision.server.ServerApp;
import com.nexusvision.server.controller.ServerController;
import lombok.extern.log4j.Log4j2;

import java.util.Scanner;

@Log4j2
public class Ausrichter {

    private static final int PORT = ServerApp.getPORT();
    private CommandProcessor commandProcessor;
    private Scanner scanner;

    public Ausrichter() {
        this.commandProcessor = new CommandProcessor();
        this.scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        Ausrichter ausrichter = new Ausrichter();
        ausrichter.start();
    }

    public void start() {
        log.info("Starting Ausrichter...");

        String prefix = "ausrichter@localhost:" + PORT + "$ ";
        boolean running = true;
        while (running) {
            System.out.print(prefix);
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("exit")) {
                running = false;
            } else {
                String output = commandProcessor.processCommand(input);
                System.out.println(output);
            }
        }
        scanner.close();
        log.info("Ending Ausrichter");
    }
}
