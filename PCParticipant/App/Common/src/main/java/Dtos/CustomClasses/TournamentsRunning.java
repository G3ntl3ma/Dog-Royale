package Dtos.CustomClasses;

public class TournamentsRunning extends Tournament {
    // Tournament object for starting tournaments or tournaments in progress
    private int maxPlayer;
    private int maxGames;
    private int gameRunning;

    public TournamentsRunning(int tournamentId, int maxPlayer, int maxGame, int gameRunning) {
        super(tournamentId);
        this.maxPlayer = maxPlayer;
        this.maxGames = maxGames;
        this.gameRunning = gameRunning;
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

    public int getGameRunning() {
        return gameRunning;
    }

    public void setGameRunning(int gameRunning) {
        this.gameRunning = gameRunning;
    }
}
