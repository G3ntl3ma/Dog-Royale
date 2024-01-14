package Dtos.CustomClasses;

import java.util.ArrayList;

public class CurrentGame extends Games {
    // Game object for the current tournament game in TournamentInfo
    private int playerCount;
    private int maxPlayerCount;
    private int currentRound;
    private ArrayList<PlayerPoints> players;

    public CurrentGame(int gameId, int playerCount, int maxPlayerCount, int currentRound, ArrayList<PlayerPoints> players) {
        super(gameId);
        this.playerCount = playerCount;
        this.maxPlayerCount = maxPlayerCount;
        this.currentRound = currentRound;
        this.players = players;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }

    public int getMaxPlayerCount() {
        return maxPlayerCount;
    }

    public void setMaxPlayerCount(int maxPlayerCount) {
        this.maxPlayerCount = maxPlayerCount;
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public void setCurrentRound(int currentRound) {
        this.currentRound = currentRound;
    }

    public ArrayList<PlayerPoints> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<PlayerPoints> players) {
        this.players = players;
    }
}
