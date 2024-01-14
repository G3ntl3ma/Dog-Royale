package Dtos.CustomClasses;

public class TournamentProgressing extends Tournament {
    // Tournament object for starting tournaments or tournaments in progress
    private int maxPlayer;
    private int maxRounds;
    private int currentPlayer;

    public TournamentProgressing(int tournamentId, int maxPlayer, int maxRounds, int currentPlayer) {
        super(tournamentId);
        this.maxPlayer = maxPlayer;
        this.maxRounds = maxRounds;
        this.currentPlayer = currentPlayer;
    }

    public int getMaxPlayer() {
        return maxPlayer;
    }

    public void setMaxPlayer(int maxPlayer) {
        this.maxPlayer = maxPlayer;
    }

    public int getMaxRounds() {
        return maxRounds;
    }

    public void setMaxRounds(int maxRounds) {
        this.maxRounds = maxRounds;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
}
