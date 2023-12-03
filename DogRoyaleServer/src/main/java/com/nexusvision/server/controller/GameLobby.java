package com.nexusvision.server.controller;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class GameLobby {
    private int gameID;
    //TODO lombok doesnt generate getters if private???
    public boolean gamePaused = false;
    public boolean gameRunning = false;
    public boolean gameCompleted = false;
    public int playerCount;
    //Game game;
    //index = ordinal value of color
    ArrayList<Integer> playerColorList;
    ArrayList<Integer> playerOrderList;
    ArrayList<Integer> observerIDs;


    public GameLobby(int gameID, ArrayList<Integer> playerOrderList, ArrayList<Integer> observerIDs, ArrayList<Integer> playerColorList) {
        this.gameID = gameID;
        this.playerOrderList = playerOrderList;
        this.playerColorList = playerColorList;
        this.observerIDs = observerIDs;
    }

    public void addObserver(int clientID) {
	observerIDs.add(clientID);
    }

    public void addPlayer(int clientID) {
	//server decides color
	playerColorList.add(clientID);
	playerOrderList.add(clientID);
	if(playerOrderList.size() == this.playerCount) {
	    gamePaused = false;
	    gameRunning = true;
	}
    }

    public void removePlayer(int clientID) {
	playerColorList.remove(clientID);
	playerOrderList.remove(clientID);
	if(playerOrderList.size() == 0) {
	    gameCompleted = true;
	}
    }

    public void removeObserver(int clientID) {
	observerIDs.remove(clientID);
    }

    public void getColorOfPlayer(int clientID) { //return colortype
	
    }

    public int getCurrentPlayerCount() {
	return playerColorList.size();
    }
    
    public void runGame() {
        gameRunning = true;
    }

    public void unpauseGame() {
        gamePaused = true;
    }

    public void pauseGame() {
        gamePaused = true;
    }

    //success boolean
    public boolean setConfiguration(int playerCount, int fieldSize, int figuresPerPlayer, List<Integer> drawFieldpositions,
                                 List<Integer>  startFields, int initialCardsPerPlayer, int thinkingTimePerMove,
                                 int consequencesForInvalidMove, int maxGameDuration, int maxTotalMoves) {

	this.playerCount = playerCount;
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

	if(playerCount == playerOrderList.size()) {
	    gameRunning = true;
	    gamePaused = true;
	}
	
        // TODO create game
	

	
        return true;
    }

}
