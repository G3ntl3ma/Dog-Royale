package Dtos.CustomClasses;

public class FinishedGames extends Games {
    // Game class for finished games in ReturnGameListDto and TournamentInfo
    private int winnerPlayerId;

    public FinishedGames(int gameId, int winnerPlayerId) {
        super(gameId);
        this.winnerPlayerId = winnerPlayerId;
    }

    public int getWinnerPlayerId() {
        return winnerPlayerId;
    }

    public void setWinnerPlayerId(int winnerPlayerId) {
        this.winnerPlayerId = winnerPlayerId;
    }
}
