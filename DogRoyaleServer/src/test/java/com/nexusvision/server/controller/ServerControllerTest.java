package com.nexusvision.server.controller;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit and integration tests that test the ServerSocket
 * and the connection to the ClientSocket
 *
 * @author felixwr
 */
public class ServerControllerTest {

    private static final String SERVER_ADDRESS = "localhost";
    private static final int PORT = 8088;
    private static Thread serverThread;
    private static ServerController serverController;

    @BeforeAll
    public static void setup() {
        serverController = ServerController.getInstance();
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

            String serverMessage = reader.readLine();
            assertNotNull(serverMessage);
            assertTrue(serverThread.isAlive());

            clientSocket.close();
        } catch (IOException e) {
            fail("Error in the connection to the server: " + e.getMessage());
        }
    }

    @Test
    public void testCreateGame() {
        try {
            Socket clientSocket = new Socket(SERVER_ADDRESS, PORT);

            assertEquals(0, serverController.getGameCount());
            assertTrue(clientSocket.isConnected());
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter writer = new PrintWriter(
                    clientSocket.getOutputStream(), true);

            writer.println("{\"type\": 100, \"name\": \"obs\", \"isObserver\": true}");
            assertTrue(serverThread.isAlive());

            String serverMessage = reader.readLine();
            assertNotNull(serverMessage);
            assertTrue(serverThread.isAlive());

            //read server message
            Gson gson = new Gson();
            JsonElement jsonElement = JsonParser.parseString(serverMessage);
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            int myClientId = jsonObject.get("clientId").getAsInt();

            writer.println("{\"type\": 114, \"playerOrder\": {\"type\": 1, \"order\": [{\"clientId\": " + myClientId + ", \"name\": \"a\"}]}}");
            //serverMessage = reader.readLine();

            // wait for the server in the other thread to receive stuff
            try {
                TimeUnit.SECONDS.sleep(1);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            assertEquals(1, serverController.getGameCount());

            clientSocket.close();
        } catch (IOException e) {
            fail("Fehler bei der Verbindung zum Server: " + e.getMessage());
        }
    }

}
