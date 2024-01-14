package Dtos.CustomClasses;

public class TournamentFinished extends Tournament {
    // Tournament object for finished tournaments
    private PlayerName[] winnerOrder;

    public TournamentFinished(int tournamentId, PlayerName[] winnerOrder) {
        super(tournamentId);
        this.winnerOrder = winnerOrder;
    }

    public PlayerName[] getWinnerOrder() {
        return winnerOrder;
    }

    public void setWinnerOrder(PlayerName[] winnerOrder) {
        this.winnerOrder = winnerOrder;
    }
}
