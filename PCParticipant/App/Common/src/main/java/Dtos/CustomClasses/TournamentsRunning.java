package Dtos.CustomClasses;

public class TournamentsRunning extends Tournament {
    // Tournament object for starting tournaments or tournaments in progress
    private int maxPlayer;
    private int maxGames;
    private GameRunningTournament gameRunning;

    public TournamentsRunning(int tournamentId, int maxPlayer, int maxGames, GameRunningTournament gameRunning) {
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

    public GameRunningTournament getGameRunning() {
        return gameRunning;
    }

    public void setGameRunning(GameRunningTournament gameRunning) {
        this.gameRunning = gameRunning;
    }
}
