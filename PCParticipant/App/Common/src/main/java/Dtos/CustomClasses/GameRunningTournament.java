package Dtos.CustomClasses;

import java.util.ArrayList;

public class GameRunningTournament extends GamesTournament {
    // Game object for upcoming tournament games in TournamentInfo
    private ArrayList<PlayerPoints> players;

    public GameRunningTournament(int gameId, ArrayList<PlayerPoints> players) {
        super(gameId);
        this.players = players;
    }

    public ArrayList<PlayerPoints> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<PlayerPoints> players) {
        this.players = players;
    }
}
