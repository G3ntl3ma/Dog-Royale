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
 * Die Server-Controller that can start up the server
 *
 * @author felixwr
 */
public class ServerController {
    private static final ServerController instance = new ServerController();

    private final Logger logger = LogManager.getLogger(ServerController.class);
    private final ExecutorService executorService = Executors.newFixedThreadPool(100);

    private final HashMap<Integer, String> clientIDMapName = new HashMap<>();
    private final HashMap<Integer, Boolean> clientIDMapObserver = new HashMap<>();
    private final ArrayList<GameLobby> lobbyList = new ArrayList<>();

    //TODO starting games (list of gameid + currentplayercount + maxpalyercount)
    //TODO running games (list of gameid + currentplayercount + maxplayercount)
    //TODO completed games (list of gameid + winnerplayerid)

    private ServerController() {}

    public static ServerController getInstance() {
        return instance;
    }

    /**
     * Setup <code>ServerSocket</code>, create threads for incoming connections
     * and add those to the thread pool
     *
     * @param port The port being used to start the <code>ServerSocket</code>
     */
    public void startServer(int port) {
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

    public int generateGameID() {
        Random ran = new Random();
        int newGameID = 0;

        boolean found = true;
        while (found) {
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

    public int generateClientID() {
        Random ran = new Random();
        int newClientID = 0;

        boolean found = true;
        while (found) {
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

    public void createNewLobby(ArrayList<Integer> playerIDs, ArrayList<Integer> observerIDs) {
        lobbyList.add(new GameLobby(generateClientID(), playerIDs, observerIDs));
    }

    public void setUsername(int clientID, String userName) {
        clientIDMapName.put(clientID, userName);
    }

    public void setObserver(int clientID, boolean isObserver) {
        clientIDMapObserver.put(clientID, isObserver);
    }

    public String getUsername(int clientID) {
        return clientIDMapName.get(clientID);
    }

    public int getGameCount() {
	return lobbyList.size();
    }

    public boolean getObserver(int clientID) {
	    return clientIDMapObserver.get(clientID);
    }

    public boolean clientIdRegistered(int clientId) {
	for (Integer key : clientIDMapName.keySet()) {
	    if (key == clientId) {
		return true;
	    }
	}
	return false;
    }


    /*
    public SpiellogikInstanz getLobby(int lobbyID) {
        // Gebe Spiellogik zurück
    }
     */
}
