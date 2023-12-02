package com.nexusvision.server.controller;

import java.util.ArrayList;
import java.util.List;

public class GameLobby {
    int gameID;
    //Game game;
    ArrayList<Integer> playerColorList;
    //clientid list (not observer) sorted by color (index 1 = color 1)
    ArrayList<Integer> playerOrderList;
    ArrayList<Integer> observerIDs;

    public GameLobby(int gameID, ArrayList<Integer> playerOrderList, ArrayList<Integer> observerIDs, ArrayList<Integer> playerColorList) {
        this.gameID = gameID;
        this.playerOrderList = playerOrderList;
        this.playerColorList = playerColorList;
        this.observerIDs = observerIDs;
    }

    public void setConfiguration(int playerCount, int fieldSize, int figuresPerPlayer, List<Integer> drawFieldpositions,
                                 List<Integer>  startFields, int initialCardsPerPlayer, int thinkingTimePerMove,
                                 int consequencesForInvalidMove, int maxGameDuration, int maxTotalMoves) {
        // TODO create game
    }

}
