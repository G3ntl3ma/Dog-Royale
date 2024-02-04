package Dtos.CustomClasses;

import java.util.ArrayList;

public class GamesFinishedTournament extends GamesTournament {
    // Game class for finished games in TournamentInfo
    private ArrayList<PlayerPoints> winnerOrder;
    private boolean wasCanceled;

    public GamesFinishedTournament(int gameId, boolean wasCanceled, ArrayList<PlayerPoints> winnerOrder) {
        super(gameId);
        this.winnerOrder = winnerOrder;
        this.wasCanceled = wasCanceled;
    }

    public ArrayList<PlayerPoints> getWinnerOrder() {
        return winnerOrder;
    }

    public void setWinnerOrder(ArrayList<PlayerPoints> winnerOrder) {
        this.winnerOrder = winnerOrder;
    }

    public boolean isWasCanceled() {
        return wasCanceled;
    }

    public void setWasCanceled(boolean wasCanceled) {
        this.wasCanceled = wasCanceled;
    }


}
