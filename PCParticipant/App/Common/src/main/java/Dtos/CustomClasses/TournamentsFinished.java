package Dtos.CustomClasses;

import java.util.ArrayList;

public class TournamentsFinished extends Tournament {
    // Tournament object for finished tournaments
    private ArrayList<PlayerPoints> winnerOrder;

    public TournamentsFinished(int tournamentId, ArrayList<PlayerPoints> winnerOrder) {
        super(tournamentId);
        this.winnerOrder = winnerOrder;
    }

    public ArrayList<PlayerPoints> getWinnerOrder() {
        return winnerOrder;
    }

    public void setWinnerOrder(ArrayList<PlayerPoints> winnerOrder) {
        this.winnerOrder = winnerOrder;
    }
}
