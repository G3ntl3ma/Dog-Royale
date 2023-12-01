package com.nexusvision.server.controller;

import com.nexusvision.server.handler.ClientHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
/**
 * Die Server-Applikation, die den Server startet
 *
 * @author felixwr
 */
public class ServerController {
    private static final Logger logger = LogManager.getLogger(ServerController.class);
    private static final int PORT = 8080;
    private static final ExecutorService executorService = Executors.newFixedThreadPool(100);

    private static HashMap<Integer, String> clientIDMapName = new HashMap<>();
    private static HashMap<Integer, Boolean> clientIDMapObserver = new HashMap<>();
    private static ArrayList<GameLobby> lobbyList = new ArrayList<>();

    //TODO starting games (list of gameid + currentplayercount + maxpalyercount)
    //TODO running games (list of gameid + currentplayercount + maxplayercount)
    //TODO completed games (list of gameid + winnerplayerid)

    public static void main(String[] args) {
        startServer(PORT);
    }

    /**
     * Setzt ServerSocket auf und erstellt für eingehende Verbindungen
     * einen Thread und fügt diese zum Threadpool hinzu
     *
     * @param port Der Port mit dem der ServerSocket gestartet wird
     */
    public static void startServer(int port) {
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

    public static int generateGameID() {
        Random ran = new Random();
        int newGameID = 0;

        boolean found = true;
        while(found) {
            found = false;
            newGameID = ran.nextInt();
            for (int i = 0; i < lobbyList.size(); i++) {
		int key = lobbyList.get(i).gameID;
                if (key == newGameID) {
                    found = true;
                    break;
                }
            }
        }
        return newGameID;
    }

    public static int generateClientID() {
        Random ran = new Random();
        int newClientID = 0;

        boolean found = true;
        while(found) {
            found = false;
            newClientID = ran.nextInt();
            for (Integer key : clientIDMapName.keySet()) {
                if (key == newClientID) {
                    found = true;
                    break;
                }
            }
        }
	clientIDMapName.put(newClientID, null);
        return newClientID;
    }

    public static void createNewLobby(ArrayList<Integer> playerIDs, ArrayList<Integer> observerIDs) {
	lobbyList.add(new GameLobby(generateClientID(), playerIDs, observerIDs));
    }

    public static void setUsername(int clientID, String userName) {
	clientIDMapName.put(clientID, userName);
    }

    public static void setObserver(int clientID, boolean isObserver) {
	clientIDMapObserver.put(clientID, isObserver);
    }

    public static String getUsername(int clientID) {
	return clientIDMapName.get(clientID);
    }

    public static boolean getObserver(int clientID) {
	return clientIDMapObserver.get(clientID);
    }

    /*
    public static SpiellogikInstanz getLobby(int lobbyID) {
        // Gebe Spiellogik zurück
    }
     */
}
