package com.nexusvision.servertest.controllertest;

import com.nexusvision.server.controller.ServerController;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit- und IT-Tests die den ServerSocket
 * und die Verbindung zum ClientSocket testen
 *
 * @author felixwr
 */
public class ServerControllerTest {

    private static final String SERVER_ADDRESS = "localhost";
    private static final int PORT = 8088;
    private static Thread serverThread;

    @BeforeAll
    public static void setup() {
        ServerController serverController = ServerController.getInstance();
        serverThread = new Thread(() ->
                serverController.startServer(PORT)
        );
        serverThread.start();

        // Warte eine kurze Zeit, um dem Server Zeit zum Starten zu geben
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @AfterAll
    public static void tearDown() {
        serverThread.interrupt();
    }

    @Test
    public void testServerStartup() {
        assertTrue(serverThread.isAlive());
    }

    @Test
    public void testClientConnection() {
        try {
            Socket clientSocket = new Socket(SERVER_ADDRESS, PORT);

            assertTrue(clientSocket.isConnected());
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter writer = new PrintWriter(
                    clientSocket.getOutputStream(), true);

            writer.println("{test: test}");
            assertTrue(serverThread.isAlive());

            // String serverMessage = reader.readLine();
            // assertNotNull(serverMessage);
            // assertTrue(serverThread.isAlive());

            clientSocket.close();
        } catch (IOException e) {
            fail("Fehler bei der Verbindung zum Server: " + e.getMessage());
        }
    }
}
