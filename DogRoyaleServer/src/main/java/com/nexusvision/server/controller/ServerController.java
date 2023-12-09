package com.nexusvision.server.controller;

import com.nexusvision.server.handler.ClientHandler;
import com.nexusvision.server.model.entities.Client;
import com.nexusvision.server.model.enums.Colors;
import com.nexusvision.server.model.enums.GameState;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The Server-Controller that can start up the server
 *
 * @author felixwr
 */
public class ServerController {
    @Getter
    private static final ServerController instance = new ServerController();
    private  static final Logger logger = LogManager.getLogger(ServerController.class);

    private final ExecutorService executorService = Executors.newFixedThreadPool(100);

    private final HashMap<Integer, Client> clientMap = new HashMap<>();
    private final HashMap<Integer, GameLobby> lobbyMap = new HashMap<>();

    // ArrayList<Socket> clientSockets = new ArrayList<>();
    private final HashMap<Integer, ClientHandler> handlerMap = new HashMap<>();

    private ServerController() {
    }

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
                int newClientID = this.createNewClient();
                ClientHandler clientHandler = new ClientHandler(clientSocket, newClientID);
                handlerMap.put(newClientID, clientHandler);
                executorService.submit(clientHandler);
            }
        } catch (IOException e) {
            logger.error(e.getStackTrace());
        }
    }

    /**
     * Searching for a game lobby associated with a specific player
     *
     * @param clientID An Integer representing the Id of the player for whom the associated game lobby is being searched
     * @return An object representing the game the player is participating otherwise null
     */
    public GameLobby getGameOfPlayer(int clientID) {
        for (int key : lobbyMap.keySet()) {
            GameLobby g = lobbyMap.get(key);
            //see if player in players of game
            for (int i = 0; i < g.getPlayerOrderList().size(); i++) {
                if (g.getPlayerOrderList().get(i) == clientID) {
                    return g;
                }
            }
        }
        return null;
    }

    /**
     * Gets a specific number of game lobbies in a particular game state
     *
     * @param gameCount An Integer representing the maximum number of games to retrieve
     * @param state An Enum representing the game state for filtering
     * @return An ArrayList containing gameCount amount (or less) of Game lobbies
     */
    public ArrayList<GameLobby> getStateGames(int gameCount, GameState state) {
        int foundCount = 0;
        ArrayList<GameLobby> gameLobbys = new ArrayList<>();
        for (int lobbyID : lobbyMap.keySet()) {
            GameLobby lobby = lobbyMap.get(lobbyID);
            if (lobby.getGameState() == state) {
                gameLobbys.add(lobby);
                foundCount++;
            }
            if (foundCount == gameCount) break;
        }
        return gameLobbys;
    }

    /**
     * Sends a message to all members of a game lobby
     *
     * @param lobby An Object representing the lobby to which the message should be sent
     * @param message A String representing the message to be broadcasted to all players in the lobby
     */
    public void sendToAllLobbyMembers(GameLobby lobby, String message) {
        for (int clientId : lobby.getPlayerOrderList()) {
            //get the client
            ClientHandler handler = handlerMap.get(clientId);
            try {
                handler.broadcast(message);
            } catch (Exception e) {
            }
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
     * @param playerColorMap  The player color map
     * @param observerList    The observer id list
     * @return The lobby id
     */
    public int createNewLobby(ArrayList<Integer> playerOrderList, HashMap<Integer, Colors> playerColorMap,
                              ArrayList<Integer> observerList) {
        Random random = new Random();
        int newLobbyID;

        do {
            newLobbyID = random.nextInt(Integer.MAX_VALUE);
        } while (lobbyMap.containsKey(newLobbyID));

        lobbyMap.put(newLobbyID, new GameLobby(newLobbyID, playerOrderList, playerColorMap, observerList));
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

    /**
     * Sets the configuration parameters for a game
     *
     * @param gameID An Integer representing the Id of the game
     * @param playerCount An Integer representing the number of players in the game
     * @param fieldSize An Integer representing the size of the gamefield
     * @param figuresPerPlayer An Integer representing the number of figures each player has
     * @param drawFieldpositions A List of Integers representing the position of the drawfields in the game
     * @param startFields A List of Integers representing the positions of start fields on the game board
     * @param initialCardsPerPlayer An Integer representing the initial number of cards each player recieves
     * @param thinkingTimePerMove An Integer representing the maximum time a player has to make a move
     * @param consequencesForInvalidMove An Integer representing the consequences for an invalid move
     * @param maxGameDuration An Integer representing the maximum duration of a game
     * @param maxTotalMoves An Integer representing the maximum total number of moves allowed in the game
     */
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

    /**
     * Gets the gamecount
     *
     * @return An Integer representing the count of games
     */
    public int getGameCount() {
        return lobbyMap.size();
    }

    //    public boolean getObserver(int clientID) {
//        return clientIDMapObserver.get(clientID);
//    }
//

    /**
     * Checks if a ClientId is registered
     *
     * @param clientId An Integer representing the Id of the client
     * @return A Boolean if true the clientId is registered
     */
    public boolean clientIdRegistered(int clientId) {
        return clientMap.containsKey(clientId);
    }


    /*
    public SpiellogikInstanz getLobby(int lobbyID) {
        // Gebe Spiellogik zurück
    }
     */
}
