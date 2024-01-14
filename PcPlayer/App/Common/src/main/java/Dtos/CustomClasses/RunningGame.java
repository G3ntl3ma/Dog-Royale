package Dtos.CustomClasses;

public class RunningGame extends Games {
    // Game objects starting und running games in ReturnGameListDto
    private int currentPlayerCount;
    private int maxPlayerCount;

    public RunningGame(int gameId, int currentPlayerCount, int maxPlayerCount) {
        super(gameId);
        this.currentPlayerCount = currentPlayerCount;
        this.maxPlayerCount = maxPlayerCount;
    }

    public int getCurrentPlayerCount() {
        return currentPlayerCount;
    }

    public void setCurrentPlayerCount(int currentPlayerCount) {
        this.currentPlayerCount = currentPlayerCount;
    }

    public int getMaxPlayerCount() {
        return maxPlayerCount;
    }

    public void setMaxPlayerCount(int maxPlayerCount) {
        this.maxPlayerCount = maxPlayerCount;
    }
}
