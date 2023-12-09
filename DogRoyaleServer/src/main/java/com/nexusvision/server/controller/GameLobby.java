package com.nexusvision.server.controller;

import com.nexusvision.server.model.enums.Colors;
import com.nexusvision.server.model.enums.GameState;
import com.nexusvision.server.model.gamelogic.Game;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    public void receiveResponse(int clientId) {
        for (int i = 0; i < this.receivedResponses.size(); i++) {
            if (clientId == this.receivedResponses.get(i)) return;
        }
        this.receivedResponses.add(clientId);
    }

    public void resetResponseList() {
        this.receivedResponses = new ArrayList<>();
    }

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
    public boolean receivedFromEveryone() {
        for (int i = 0; i < this.playerOrderList.size(); i++) {
            //check if this id is in the list of reponses
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
     * @param clientID The client ID of the observer being added
     */
    public void addObserver(int clientID) {
        observerList.add(clientID);
    }

    /**
     * Removes an observer from the lobby
     *
     * @param clientID The client ID of the observer being removed
     */
    public void removeObserver(int clientID) {
        observerList.remove(clientID);
    }

    /**
     * Adds a player to the lobby
     *
     * @param clientID The client ID of the player being added
     * @param color    The color of the player being added
     */
    public void addPlayer(int clientID, Colors color) {
        playerOrderList.add(clientID);
        playerColorMap.put(clientID, color);
        // TODO: What if more players get added then maxPlayerCount allows?
        if (playerOrderList.size() == this.maxPlayerCount) {
            gameState = GameState.IN_PROGRESS; // TODO: Ausrichter decides when game gets started
        }
    }

    /**
     * Removes a player from the lobby
     *
     * @param clientID The client ID of the player being removed
     */
    public void removePlayer(int clientID) {
        playerOrderList.remove(clientID);
        playerColorMap.remove(clientID);
        if (playerOrderList.isEmpty()) {
            gameState = GameState.FINISHED;
        }
    }

    // TODO
    public void getColorOfPlayer(int clientID) { //return colortype

    }

    /**
     * The current placer count
     *
     * @return The current player count
     */
    public int getCurrentPlayerCount() {
        return playerOrderList.size();
    }

    // TODO: Sending board states to all clients (somehow, maybe not inside this class)
    public void runGame() {
        gameState = GameState.IN_PROGRESS;
    }

    public void unpauseGame() {
        isPaused = false;
    }

    public void pauseGame() {
        isPaused = true;
    }

    //success boolean
    public boolean tryMove(boolean skip, int card, int selectedValue,
                           int pieceId, boolean isStarter, Integer opponentPieceId) {
        boolean success = this.game.tryMove(skip, card, selectedValue, pieceId, isStarter, opponentPieceId);
	boolean gameOver = this.game.nextPlayer(); //if no nextPlayer exists game is over
	return success;
    }

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
