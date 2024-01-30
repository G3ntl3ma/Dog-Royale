package com.nexusvision.server;

import com.nexusvision.config.AppConfig;
import com.nexusvision.server.controller.AusrichterController;
import com.nexusvision.server.controller.ServerController;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

/**
 * Starting the server
 *
 * @author felixwr
 */
@Log4j2
public class ServerApp {
    @Getter
    private static int PORT = Integer.parseInt(AppConfig.getInstance().getProperty("port"));

    /**
     * Entry point for the server, sets up controller and connects to port
     *
     * @param args Command-line arguments (optional)
     */
    public static void main(String[] args) {
        if (args.length >= 1) {
            try {
                PORT = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                log.error("Expected the argument to be an integer, but was actually " + args[0]);
            }
        }
        AusrichterController.getInstance().startAusrichter();
        ServerController.getInstance().startServer(PORT);
    }
}