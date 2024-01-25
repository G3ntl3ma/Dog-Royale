package Dtos.CustomClasses;

import java.util.ArrayList;

public class GamesProgressing extends Games {
    // Game objects starting und running games in ReturnGameListDto
    private PlayerOrder playerOrder;
    private int maxPlayerCount;

    private ArrayList<PlayerPoints> winnerOrder;

    public GamesProgressing(int gameId, String gameName, PlayerOrder playerOrder,  ArrayList<PlayerPoints> winnerOrder, int maxPlayerCount) {
        super(gameId, gameName);
        this.playerOrder = playerOrder;
        this.maxPlayerCount = maxPlayerCount;
        this.winnerOrder = winnerOrder;
    }

    public PlayerOrder getPlayerOrder() {
        return playerOrder;
    }
    public void setPlayerOrder(PlayerOrder playerOrder) {
        this.playerOrder = playerOrder;
    }
    public ArrayList<PlayerPoints> getWinnerOrder() {
        return winnerOrder;
    }

    public void setWinnerOrder(ArrayList<PlayerPoints> winnerOrder) {
        this.winnerOrder = winnerOrder;
    }

    public int getMaxPlayerCount() {
        return maxPlayerCount;
    }

    public void setMaxPlayerCount(int maxPlayerCount) {
        this.maxPlayerCount = maxPlayerCount;
    }
}
