package Dtos.CustomClasses;

public class TournamentsUpcoming extends Tournament {
    // Tournament object for starting tournaments or tournaments in progress
    private int maxPlayer;
    private int maxGames;
    private int currentPlayerCount;

    public TournamentsUpcoming(int tournamentId, int maxPlayer, int maxGames, int currentPlayerCount) {
        super(tournamentId);
        this.maxPlayer = maxPlayer;
        this.maxGames = maxGames;
        this.currentPlayerCount = currentPlayerCount;
    }

    public int getMaxPlayer() {
        return maxPlayer;
    }

    public void setMaxPlayer(int maxPlayer) {
        this.maxPlayer = maxPlayer;
    }

    public int getMaxGames() {
        return maxGames;
    }

    public void setMaxGames(int maxGames) {
        this.maxGames = maxGames;
    }

    public int getCurrentPlayerCount() {
        return currentPlayerCount;
    }

    public void setCurrentPlayerCount(int currentPlayerCount) {
        this.currentPlayerCount = currentPlayerCount;
    }
}
