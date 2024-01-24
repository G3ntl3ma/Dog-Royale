package Dtos.CustomClasses;

import java.util.ArrayList;

public class GamesFinishedTournament extends Games {
    // Game class for finished games in ReturnGameListDto and TournamentInfo
    private ArrayList<PlayerPoints> winnerOrder;
    private boolean wasCanceled;

    public GamesFinishedTournament(int gameId, String gameName, ArrayList<PlayerPoints> winnerOrder, boolean wasCanceled) {
        super(gameId, gameName);
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
