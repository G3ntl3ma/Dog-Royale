package com.nexusvision.server.controller;

import com.nexusvision.server.handler.ClientHandler;
import com.nexusvision.server.model.entities.Client;
import com.nexusvision.server.model.enums.Colors;
import com.nexusvision.server.model.enums.GameState;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * The Server-Controller that can start up the server
 *
 * @author felixwr
 */
public class ServerController {
    @Getter
    private static final ServerController instance = new ServerController();

    private final Logger logger = LogManager.getLogger(ServerController.class);
    private final ExecutorService executorService = Executors.newFixedThreadPool(100);

    private final HashMap<Integer, Client> clientMap = new HashMap<>();
    private final HashMap<Integer, GameLobby> lobbyMap = new HashMap<>();

    ArrayList<Socket> clientSockets = new ArrayList<>();

    private ServerController() {}

    /**
     * Setup <code>ServerSocket</code>, create threads for incoming connections
     * and add those to the thread pool
     *
     * @param port The port being used to start the <code>ServerSocket</code>
     */
    public void startServer(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("ServerSocket started successfully on port " + port);
            logger.info("Waiting for connections...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                logger.info("New connection request from " + clientSocket.getInetAddress());
		clientSockets.add(clientSocket);

                executorService.submit(new ClientHandler(clientSocket));
            }
        } catch (IOException e) {
            logger.error(e.getStackTrace());
        }
    }

    public GameLobby getGameOfPlayer(int clientID) {
        for (int key : lobbyMap.keySet()) {
            GameLobby g = lobbyMap.get(key);
	    //see if player in players of game
	    for (int i = 0; i < g.getPlayerOrderList().size(); i++) {
		if(g.getPlayerOrderList().get(i) == clientID) {
		    return g;
		}
	    }

        }
	return null;
    }

    public ArrayList<GameLobby> getStateGames(int gameCount, GameState state) {
        int foundCount = 0;
        ArrayList<GameLobby> gameLobbys = new ArrayList<>();
        for (int key : lobbyMap.keySet()) {
            GameLobby g = lobbyMap.get(key);
            if (g.getGameState() == state) {
                gameLobbys.add(g);
                foundCount++;
            }
            if (foundCount == gameCount) break;
        }
        return gameLobbys;
    }

    //TODO figure out if this actually works in practice
    public void sendToAllClients(String message) {
	for(Socket socket : clientSockets) {
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(
                    socket.getOutputStream(), false);
            writer.write(message);
            writer.flush();
        }
        catch(Exception e){};
	}
    }

    // ALLE NICHT NÖTIG DA EINE GAME LOBBY DAS GANZE ANBIETET
//    public ArrayList<GameLobby> getStartingGames(int gameCount) {
//        int foundCount = 0;
//        ArrayList<GameLobby> gameLobbys = new ArrayList<>();
//        for (int key : lobbyMap.keySet()) {
//            GameLobby g = lobbyMap.get(key);
//            if (g.getGameState() == GameState.STARTING) {
//                gameLobbys.add(g);
//                foundCount++;
//            }
//            if (foundCount == gameCount) break;
//        }
//        return gameLobbys;
//    }
//
//    public ArrayList<GameLobby> getRunningGames(int gameCount) {
//        int foundCount = 0;
//        ArrayList<GameLobby> gameLobbys = new ArrayList<>();
//        for (int key : lobbyMap.keySet()) {
//            GameLobby g = lobbyMap.get(key);
//            if (g.getGameState() == GameState.IN_PROGRESS) {
//                gameLobbys.add(g);
//                foundCount++;
//            }
//            if (foundCount == gameCount) break;
//        }
//        return gameLobbys;
//    }
//
//    public boolean addPlayer(int gameId, int clientId) {
//        for (int i = 0; i < lobbyList.size(); i++) {
//            if (lobbyList.get(i).getGameID() == gameId) {
//                lobbyList.get(i).addPlayer(clientId);
//                return true;
//            }
//        }
//        return false;
//    }
//
//    public boolean addObserver(int gameId, int clientId) {
//        for (int i = 0; i < lobbyList.size(); i++) {
//            if (lobbyList.get(i).getGameID() == gameId) {
//                lobbyList.get(i).addObserver(clientId);
//                return true;
//            }
//        }
//        return false;
//    }

//    public ArrayList<GameLobby> getFinishedGames(int gameCount) {
//        int foundCount = 0;
//        ArrayList<GameLobby> gameLobbys = new ArrayList<>();
//        for (int i = 0; i < lobbyList.size(); i++) {
//            GameLobby g = lobbyList.get(i);
//            if (g.gameCompleted == true) {
//                gameLobbys.add(g);
//                foundCount++;
//            }
//            if (foundCount == gameCount) break;
//        }
//        return gameLobbys;
//    }

//    public int getGameId(GameLobby g) {
//        return g.getGameID();
//    }

//    public int getCurrentPlayerCount(GameLobby g) {
//        return g.getCurrentPlayerCount();
//    }
//
//    public int getMaxPlayerCount(GameLobby g) {
//        return g.playerCount;
//    }

//    public int generateGameID() {
//        // TODO: Sollte besser positiv sein
//        //@author Farah-ey wenn Du "ran.nextInt(Zahl);" verwendest werden die Zahlen von 0 bis Zahl ausgewählt und somit positiv)
//        Random ran = new Random();
//        int newGameID = 0;
//
//        boolean found = true;
//        while (found) {
//            found = false;
//            newGameID = ran.nextInt(Integer.MAX_VALUE);
//            for (int i = 0; i < lobbyList.size(); i++) {
//                int key = lobbyList.get(i).getGameID();
//                if (key == newGameID) {
//                    found = true;
//                    break;
//                }
//            }
//        }
//        return newGameID;
//    }

    /**
     * Generates a client id that doesn't exist yet and saves a new client object
     * linked to that id
     *
     * @return The generated client id
     */
    public int createNewClient() {
        Random random = new Random();
        int newClientID;

        do {
            newClientID = random.nextInt(Integer.MAX_VALUE);
        } while (clientMap.containsKey(newClientID));

        clientMap.put(newClientID, new Client());
        return newClientID;
    }

    /**
     * Gets the client object linked to clientID
     *
     * @param clientID The search parameter to find the client object
     * @return The client object
     */
    public Client getClientById(int clientID) {
        return clientMap.get(clientID);
    }

    /**
     * Generates a lobby id that doesn't exist yet and saves a new lobby object
     * linked to that id
     *
     * @param playerOrderList The player order list
     * @param playerColorMap The player color map
     * @param observerList The observer id list
     * @return The lobby id
     */
    public int createNewLobby(ArrayList<Integer> playerOrderList, HashMap<Integer, Colors> playerColorMap,
                              ArrayList<Integer> observerList) {
        Random random = new Random();
        int newLobbyID;

        do {
            newLobbyID = random.nextInt(Integer.MAX_VALUE);
        } while (lobbyMap.containsKey(newLobbyID));

        lobbyMap.put(newLobbyID, new GameLobby(playerOrderList, playerColorMap, observerList));
        return newLobbyID;
    }

    /**
     * Gets the lobby object linked to lobbyID
     *
     * @param lobbyID The search parameter to find the lobby object
     * @return The lobby object
     */
    public GameLobby getLobbyById(int lobbyID) {
        return lobbyMap.get(lobbyID);
    }

    public void setConfiguration(int gameID, int playerCount, int fieldSize, int figuresPerPlayer, List<Integer> drawFieldpositions,
                                 List<Integer> startFields, int initialCardsPerPlayer, int thinkingTimePerMove,
                                 int consequencesForInvalidMove, int maxGameDuration, int maxTotalMoves) {
        for (int key : lobbyMap.keySet()) {
            if (key == gameID) {
                lobbyMap.get(key).setConfiguration(playerCount, fieldSize, figuresPerPlayer, drawFieldpositions,
                        startFields, initialCardsPerPlayer, thinkingTimePerMove,
                        consequencesForInvalidMove, maxGameDuration, maxTotalMoves);
            }
        }
    }

//    public void setUsername(int clientID, String userName) {
//        clientIDMapName.put(clientID, userName);
//    }
//
//    public void setObserver(int clientID, boolean isObserver) {
//        clientIDMapObserver.put(clientID, isObserver);
//    }
//
//    public String getUsername(int clientID) {
//        return clientIDMapName.get(clientID);
//    }

    public int getGameCount() {
        return lobbyMap.size();
    }

//    public boolean getObserver(int clientID) {
//        return clientIDMapObserver.get(clientID);
//    }
//
    public boolean clientIdRegistered(int clientId) {
        return clientMap.containsKey(clientId);
    }


    /*
    public SpiellogikInstanz getLobby(int lobbyID) {
        // Gebe Spiellogik zurück
    }
     */
}
