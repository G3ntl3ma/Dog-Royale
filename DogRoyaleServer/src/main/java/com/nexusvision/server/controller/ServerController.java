package com.nexusvision.server.controller;

import com.nexusvision.server.handler.ClientHandler;
import com.nexusvision.server.handler.ConsistencyException;
import com.nexusvision.server.model.entities.Client;
import com.nexusvision.server.model.enums.GameState;
import com.nexusvision.server.model.utils.ColorMapping;
import com.nexusvision.server.model.utils.DrawCardFields;
import com.nexusvision.server.model.utils.StartFields;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

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
@Log4j2
public class ServerController {
    @Getter
    private static final ServerController instance = new ServerController();

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
            log.info("ServerSocket started successfully on port " + port);
            log.info("Waiting for connections...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                log.info("New connection request from " + clientSocket.getInetAddress());
                int newClientID = this.createNewClient();
                ClientHandler clientHandler = new ClientHandler(clientSocket, newClientID);
                handlerMap.put(newClientID, clientHandler);
                executorService.submit(clientHandler);
            }
        } catch (IOException e) {
            log.error(e.getStackTrace());
        }
    }

    /**
     * Searching for a game lobby associated with a specific player
     *
     * @param clientId An Integer representing the id of the player for whom the associated game lobby is being searched
     * @return An object representing the game the player is participating otherwise null
     */
    public GameLobby getLobbyOfPlayer(int clientId) {
        for (int key : lobbyMap.keySet()) {
            GameLobby lobby = lobbyMap.get(key);
            //see if player in players of game
            for (int player : lobby.getLobbyConfig().getPlayerOrder().getClientIdList()) {
                if (player == clientId) return lobby;
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
     * Notifies the client handler that he needs to wait for a move
     *
     * @param clientId The client handler getting notified
     * @return true if successful
     */
    public boolean setWaitingForMove(int clientId) {
        ClientHandler handler = handlerMap.get(clientId);
        return handler.setWaitingForMove();
    }

    /**
     * Starts the game for all clients of the specified lobby
     *
     * @param lobby The lobby whose clients should get their game started
     */
    public void startGameForLobby(GameLobby lobby) {
        for (int clientId : lobby.getLobbyConfig().getPlayerOrder().getClientIdList()) {
            ClientHandler handler = handlerMap.get(clientId);
            try {
                handler.startGame();
            } catch (ConsistencyException e) {
                log.error(e.getMessage());
            }
        }
    }

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
     * Gets the client object linked to clientId
     *
     * @param clientId The search parameter to find the client object
     * @return The client object
     */
    public Client getClientById(int clientId) {
        return clientMap.get(clientId);
    }

    /**
     * Generates a lobby id that doesn't exist yet and saves a new lobby object
     * linked to that id
     *
     * @return The lobby id
     */
    public int createNewLobby() {
        Random random = new Random();
        int newLobbyID;

        do {
            newLobbyID = random.nextInt(Integer.MAX_VALUE);
        } while (lobbyMap.containsKey(newLobbyID));

        lobbyMap.put(newLobbyID, new GameLobby(newLobbyID));
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
     * @param gameId An Integer representing the id of the game
     * @param gameName                   The game name
     * @param maxPlayerCount             An Integer representing the number of maximum players in the game
     * @param fieldSize                  An Integer representing the size of the game field
     * @param figuresPerPlayer           An Integer representing the number of figures each player has
     * @param colorMap                   A mapping from colors to clientIds
     * @param drawCardFields             A List of Integers representing the position of the draw fields in the game
     * @param startFields                A List of Integers representing the positions of start fields on the game board
     * @param initialCardsPerPlayer      An Integer representing the initial number of cards each player receives
     * @param thinkTimePerMove           An Integer representing the maximum time a player has to make a move
     * @param visualizationTimePerMove   The time provided to visualize the move
     * @param consequencesForInvalidMove An Integer representing the consequences for an invalid move
     * @param maximumGameDuration        An Integer representing the maximum duration of a game
     * @param maximumTotalMoves          An Integer representing the maximum total number of moves allowed in the game
     */
    public void setConfiguration(int gameId,
                                 String gameName,
                                 int maxPlayerCount,
                                 int fieldSize,
                                 int figuresPerPlayer,
                                 List<ColorMapping> colorMap,
                                 DrawCardFields drawCardFields,
                                 StartFields startFields,
                                 int initialCardsPerPlayer,
                                 int thinkTimePerMove,
                                 int visualizationTimePerMove,
                                 int consequencesForInvalidMove,
                                 int maximumGameDuration,
                                 int maximumTotalMoves) {
        for (int key : lobbyMap.keySet()) {
            if (key == gameId) {
                lobbyMap.get(key).setConfiguration(
                        gameName,
                        maxPlayerCount,
                        fieldSize,
                        figuresPerPlayer,
                        colorMap,
                        drawCardFields,
                        startFields,
                        initialCardsPerPlayer,
                        thinkTimePerMove,
                        visualizationTimePerMove,
                        consequencesForInvalidMove,
                        maximumGameDuration,
                        maximumTotalMoves
                );
            }
        }
    }

    /**
     * Gets the gamecount
     *
     * @return An Integer representing the count of games
     */
    public int getGameCount() {
        return lobbyMap.size();
    }

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
