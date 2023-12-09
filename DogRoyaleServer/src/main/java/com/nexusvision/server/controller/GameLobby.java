package com.nexusvision.server.controller;

import com.nexusvision.server.model.enums.Colors;
import com.nexusvision.server.model.enums.GameState;
import com.nexusvision.server.model.gamelogic.Game;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The GameLobby represents a lobby and handles the mangement of players, observers and game-related information
 *
 * @author dgehse, felixwr
 */
@Data
public class GameLobby {

    private final int id;
    private Game game;
    private GameState gameState;
    private boolean isPaused;

    private int maxPlayerCount;
    private int winnerID;

    private ArrayList<Integer> playerOrderList;
    private HashMap<Integer, Colors> playerColorMap;
    private ArrayList<Integer> observerList;

    private ArrayList<Integer> receivedResponses = new ArrayList<>();

    /**
     * Creates a GameLobby with the given parameters
     *
     * @param playerOrderList The player order list
     * @param observerList    The observer ID list
     * @param playerColorMap  The player color list
     */
    public GameLobby(int id, ArrayList<Integer> playerOrderList, HashMap<Integer, Colors> playerColorMap,
                     ArrayList<Integer> observerList) {
        this.id = id;
        this.playerOrderList = playerOrderList;
        this.playerColorMap = playerColorMap;
        this.observerList = observerList;
        gameState = GameState.STARTING;
        isPaused = false;
    }

    /**
     * Receives and tracks responses from client
     *
     * @param clientId An Integer representing the Id of the client
     */
    public void receiveResponse(int clientId) {
        for (int i = 0; i < this.receivedResponses.size(); i++) {
            if (clientId == this.receivedResponses.get(i)) return;
        }
        this.receivedResponses.add(clientId);
    }

    /**
     * Resets List of received responses
     *
     */
    public void resetResponseList() {
        this.receivedResponses = new ArrayList<>();
    }

    /**
     * Checks if it is the turn of the player with a certain clientId
     *
     * @param clientId An Integer representing the Id of the player to check if it is their turn
     * @return A Boolean true if its the player's turn otherwise false
     */
    public boolean checkPlayerTurn(int clientId) {
        for (int playerId = 0; playerId < this.playerOrderList.size(); playerId++) {
            if (this.playerOrderList.get(playerId) == clientId) {
                return this.game.getPlayerToMoveColor() == playerId;
            }
        }
        //client not a player
        return false;
    }

    //check if playerorderlist + observerlist is subset of received responses
    /**
     * Checks whether responses have been received from every player and observer in the game lobby
     *
     * @return A Boolean indicating if responses from every player and observer have been received or not
     */
    public boolean receivedFromEveryone() {
        for (int i = 0; i < this.playerOrderList.size(); i++) {
            //check if this id is in the list of responses
            int idToFind = this.playerOrderList.get(i);
            boolean found = false;
            for (int j = 0; j < this.receivedResponses.size(); j++) {
                if (this.receivedResponses.get(j) == idToFind) {
                    found = true;
                    break;
                }
            }
            if (!found) return false;
        }
        for (int i = 0; i < this.observerList.size(); i++) {
            //check if this id is in the list of reponses
            int idToFind = this.observerList.get(i);
            boolean found = false;
            for (int j = 0; j < this.receivedResponses.size(); j++) {
                if (this.receivedResponses.get(j) == idToFind) {
                    found = true;
                    break;
                }
            }
            if (!found) return false;
        }
        return true;
    }

    /**
     * Adds an observer to the lobby
     *
     * @param clientId The client Id of the observer being added
     */
    public void addObserver(int clientId) {
        observerList.add(clientId);
    }

    /**
     * Removes an observer from the lobby
     *
     * @param clientId The client Id of the observer being removed
     */
    public void removeObserver(int clientId) {
        observerList.remove(clientId);
    }

    /**
     * Adds a player to the lobby
     *
     * @param clientId The client Id of the player being added
     * @param color    The color of the player being added
     */
    public void addPlayer(int clientId, Colors color) {
        playerOrderList.add(clientId);
        playerColorMap.put(clientId, color);
        // TODO: What if more players get added then maxPlayerCount allows?
        if (playerOrderList.size() == this.maxPlayerCount) {
            gameState = GameState.IN_PROGRESS; // TODO: Ausrichter decides when game gets started
        }
    }

    /**
     * Removes a player from the lobby
     *
     * @param clientId The client Id of the player being removed
     */
    public void removePlayer(int clientId) {
        playerOrderList.remove(clientId);
        playerColorMap.remove(clientId);
        if (playerOrderList.isEmpty()) {
            gameState = GameState.FINISHED;
        }
    }

    // TODO
    public void getColorOfPlayer(int clientId) { //return colortype

    }

    /**
     * The current placer count
     *
     * @return The current player count
     */
    public int getCurrentPlayerCount() {
        return playerOrderList.size();
    }

    /**
     * Sets GameState to <code>IN_PROGRESS</code>
     *
     */
    // TODO: Sending board states to all clients (somehow, maybe not inside this class)
    public void runGame() {
        gameState = GameState.IN_PROGRESS;
    }

    /**
     * Unpauses the game
     *
     */
    public void unpauseGame() {
        isPaused = false;
    }

    /**
     * Pause the game
     *
     */
    public void pauseGame() {
        isPaused = true;
    }

    //success boolean
    /**
     * Attempts to make a move
     *
     * @param skip A Boolean indicating whether to skip the move or not
     * @param card An Integer representing the card used for the move
     * @param selectedValue An Integer representing a selected value for the move
     * @param pieceId An Integer representing the Id of the figure involved in the move
     * @param isStarter A Boolean indicating if the move is a starter move
     * @param opponentPieceId An Integer representing the Id of the opponents figure
     * @return A Boolean indicating if the move was successful
     */
    public boolean tryMove(boolean skip, int card, int selectedValue,
                           int pieceId, boolean isStarter, Integer opponentPieceId) {
        boolean success = this.game.tryMove(skip, card, selectedValue, pieceId, isStarter, opponentPieceId);
	boolean gameOver = this.game.nextPlayer(); //if no nextPlayer exists game is over
	return success;
    }

    /**
     * Sets the configuration parameters for a game
     *
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
    //success boolean
    public boolean setConfiguration(int playerCount, int fieldSize, int figuresPerPlayer, List<Integer> drawFieldpositions,
                                    List<Integer> startFields, int initialCardsPerPlayer, int thinkingTimePerMove,
                                    int consequencesForInvalidMove, int maxGameDuration, int maxTotalMoves) {
        //constructing a string that can be parsed by the game
        this.maxPlayerCount = playerCount;
        StringBuilder fieldStringBuild = new StringBuilder();
        for (int i = 0; i < fieldSize; i++) {
            fieldStringBuild.append('n');
        }

        for (int i = 0; i < startFields.size(); i++) {
            fieldStringBuild.setCharAt(i, 's');
        }

        for (int i = 0; i < drawFieldpositions.size(); i++) {
            fieldStringBuild.setCharAt(i, 'k');
        }

        String fieldString = fieldStringBuild.toString();

        if (playerCount == playerOrderList.size()) {
            gameState = GameState.IN_PROGRESS;
            isPaused = true;
        }

        this.game = new Game(fieldStringBuild.toString(), figuresPerPlayer, initialCardsPerPlayer, maxTotalMoves, consequencesForInvalidMove);

        return true;
    }

}
