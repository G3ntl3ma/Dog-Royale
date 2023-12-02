package com.nexusvision.server.controller;

import java.util.ArrayList;

public class GameLobby {
    int gameID;
    //Game game;
    //clientid list (not observer) sorted by color (index 1 = color 1)
    ArrayList<Integer> playerIDs;
    ArrayList<Integer> observerIDs;

    public GameLobby(int gameID, ArrayList<Integer> playerIDs, ArrayList<Integer> observerIDs) {
        this.gameID = gameID;
        this.playerIDs = playerIDs;
        this.observerIDs = observerIDs;
    }

    public void setConfiguration(int playerCount, int fieldSize, int figuresPerPlayer, int[] drawFieldpositions,
                                 int[] startFields, int initialCardsPerPlayer, int thinkingTimePerMove,
                                 int consequencesForInvalidMove, int maxGameDuration, int maxTotalMoves) {
        // TODO create game
    }

}
