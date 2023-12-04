package com.nexusvision.server.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.nexusvision.server.model.enums.GameState;
import com.nexusvision.server.model.enums.Colors;
import lombok.Data;

@Data
public class GameLobby {

    private GameState gameState;
    private boolean isPaused;
    private int maxPlayerCount;

    private ArrayList<Integer> playerOrderList;
    private HashMap<Integer, Colors> playerColorMap;
    private ArrayList<Integer> observerList;

    /**
     * Creates a GameLobby with the given parameters
     *
     * @param playerOrderList The player order list
     * @param observerList The observer ID list
     * @param playerColorMap The player color list
     */
    public GameLobby(ArrayList<Integer> playerOrderList, HashMap<Integer, Colors> playerColorMap,
                     ArrayList<Integer> observerList) {
        this.playerOrderList = playerOrderList;
        this.playerColorMap = playerColorMap;
        this.observerList = observerList;
        gameState = GameState.STARTING;
        isPaused = false;
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
     * @param color The color of the player being added
     */
    public void addPlayer(int clientID, Colors color) {
        playerOrderList.add(clientID);
        playerColorMap.put(clientID, color);
        if (playerOrderList.size() == this.maxPlayerCount) {
            gameState = GameState.IN_PROGRESS;
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
    public boolean setConfiguration(int playerCount, int fieldSize, int figuresPerPlayer, List<Integer> drawFieldpositions,
                                    List<Integer> startFields, int initialCardsPerPlayer, int thinkingTimePerMove,
                                    int consequencesForInvalidMove, int maxGameDuration, int maxTotalMoves) {

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

        // TODO create game


        return true;
    }

}
