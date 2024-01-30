package com.nexusvision.server.controller;

import com.nexusvision.server.handler.ClientHandler;
import com.nexusvision.server.handler.ConsistencyException;
import com.nexusvision.server.model.entities.Client;
import com.nexusvision.server.model.enums.GameState;
import com.nexusvision.server.model.messages.menu.ReturnLobbyConfig;
import com.nexusvision.server.model.utils.ColorMapping;
import com.nexusvision.server.model.utils.DrawCardFields;
import com.nexusvision.server.model.utils.PlayerElement;
import com.nexusvision.server.model.utils.StartFields;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The Server-Controller that can start up the server
 *
 * @author felixwr
 */
@Log4j2
public class ServerController {
    private static ServerController instance;

    private final ExecutorService executorService = Executors.newFixedThreadPool(100);

    private final HashMap<Integer, Client> clientMap = new HashMap<>();
    private final HashMap<Integer, GameLobby> lobbyMap = new HashMap<>();
    private final HashMap<Integer, Tournament> tournamentMap = new HashMap<>();

    private final HashMap<Integer, ClientHandler> handlerMap = new HashMap<>();

    private int clientIdCounter = 0;
    private int lobbyIdCounter = 0;
    private int tournamentIdCounter = 0;

    private ServerController() {
    }

    public static ServerController getInstance() {
        if (instance == null) { // can be set to null by test methods
            instance = new ServerController();
        }
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
        for (GameLobby lobby : lobbyMap.values()) {
            for (int player : lobby.getLobbyConfig().getPlayerOrder().getClientIdList()) {
                if (player == clientId) return lobby;
            }
        }
        return null;
    }

    /**
     * Searching for a tournament associated with a specific player
     *
     * @param clientId An integer representing the id of the play for whom the associated tournament is being searched
     * @return An object representing the first found tournament of the player or null if it doesn't exist
     */
    public Tournament getTournamentOfPlayer(int clientId) {
        for (Tournament tournament : tournamentMap.values()) {
            for (PlayerElement playerElement : tournament.getPlayerElements()) {
                if (playerElement.getClientId() == clientId) return tournament;
            }
        }
        return null;
    }

    /**
     * Gets a specific number of game lobbies in a particular game state
     *
     * @param gameCount An <code>Integer</code> representing the maximum number of games to retrieve
     * @param state An <code>Enum</code> representing the game state for filtering
     * @return A <code>List</code> containing gameCount amount (or less) of game lobbies
     */
    public List<GameLobby> getStateGames(int gameCount, GameState state) {
        List<GameLobby> gameLobbyList = new ArrayList<>();
        for (int lobbyId : lobbyMap.keySet()) {
            GameLobby lobby = lobbyMap.get(lobbyId);
            if (lobby.getGameState() == state) gameLobbyList.add(lobby);
            if (gameLobbyList.size() == gameCount) break;
        }
        return gameLobbyList;
    }

    /**
     * Gets a specific number of tournaments in a particular game state
     *
     * @param tournamentCount An <code>Integer</code> representing the maximum number of tournaments to retrieve
     * @param state An <code>Enum</code> representing the tournament state for filtering
     * @return A <code>List</code> containing tournamentCount amount (or less) of tournaments
     */
    public List<Tournament> getStateTournaments(int tournamentCount, GameState state) {
        List<Tournament> tournamentList = new ArrayList<>();
        for (int tournamentId : tournamentMap.keySet()) {
            Tournament tournament = tournamentMap.get(tournamentId);
            if (tournament.getTournamentState() == state) tournamentList.add(tournament);
            if (tournamentList.size() == tournamentCount) break;
        }
        return tournamentList;
    }

    /**
     * Notifies the client handler that he needs to wait for a move
     *
     * @param clientId The client handler getting notified
     * @return <code>true</code> if successful
     */
    public boolean setWaitingForMove(int clientId) {
        ClientHandler handler = handlerMap.get(clientId);
        return handler.setWaitingForMove();
    }

    /**
     * Moves the client handler into a game (should only be used by Ausrichter)
     *
     * @param clientId The client id of the client handler to move
     */
    public void moveIntoGame(int clientId) {
        ClientHandler handler = handlerMap.get(clientId);
        handler.moveIntoGame();
    }

    /**
     * Starts the game for all clients of the specified lobby
     *
     * @param lobby The lobby whose clients should get their game started
     */
    public void startGameForLobby(GameLobby lobby) {
        for (int clientId : lobby.getLobbyConfig().getPlayerOrder().getClientIdList()) {
            ClientHandler handler = handlerMap.get(clientId);
            if (handler == null) {
                continue;
            }
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
        int newClientId;

        do {
            clientIdCounter++;
            if (clientIdCounter < 0) clientIdCounter = 0;
            newClientId = clientIdCounter;
        } while (clientMap.containsKey(newClientId));

        clientMap.put(newClientId, new Client());
        return newClientId;
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
        int newLobbyId;

        do {
            lobbyIdCounter++;
            if (lobbyIdCounter < 0) lobbyIdCounter = 0;
            newLobbyId = lobbyIdCounter;
        } while (lobbyMap.containsKey(newLobbyId));

        lobbyMap.put(newLobbyId, new GameLobby(newLobbyId));
        return newLobbyId;
    }

    /**
     * Gets the lobby object linked to lobbyId
     *
     * @param lobbyId The search parameter to find the lobby object
     * @return The lobby object
     */
    public GameLobby getLobbyById(int lobbyId) {
        return lobbyMap.get(lobbyId);
    }

    /**
     * Generates a tournament id that doesn't exist yet and saves a new tournament object
     * linked to that id
     *
     * @param maxPlayer Max Players for this tournament
     * @return The tournament id
     */
    public int createNewTournament(int maxPlayer) {
        int tournamentId;

        do {
            tournamentIdCounter++;
            if (tournamentIdCounter < 0) tournamentIdCounter = 0;
            tournamentId = tournamentIdCounter;
        } while (tournamentMap.containsKey(tournamentId));

        tournamentMap.put(tournamentId, new Tournament(tournamentId, maxPlayer));
        return tournamentId;
    }

    /**
     * Gets the tournament object linked to <code>tournamentId</code>>
     *
     * @param tournamentId The search parameter to find the tournament object
     * @return The tournament object
     */
    public Tournament getTournamentById(int tournamentId) {
        return tournamentMap.get(tournamentId);
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
        lobbyMap.get(gameId).setConfiguration(
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

    /**
     * Sets the configuration parameters for a game with the given id
     *
     * @param gameId The game to set the config for
     * @param returnLobbyConfig The config that will be set
     */
    public void setConfiguration(int gameId, ReturnLobbyConfig returnLobbyConfig) {
        lobbyMap.get(gameId).setConfiguration(returnLobbyConfig);
    }

    /**
     * Gets the game count
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
}
