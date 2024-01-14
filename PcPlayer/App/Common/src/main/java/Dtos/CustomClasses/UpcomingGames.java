package Dtos.CustomClasses;

import java.util.ArrayList;

public class UpcomingGames extends Games {
    // Game object for upcoming tournament games in TournamentInfo
    private int startInGames;
    private ArrayList<PlayerPoints> players;

    public UpcomingGames(int gameId, int startInGames ,ArrayList<PlayerPoints> players) {
        super(gameId);
        this.startInGames = startInGames;
        this.players = players;
    }

    public int getStartInGames() {
        return startInGames;
    }

    public void setStartInGames(int startInGames) {
        this.startInGames = startInGames;
    }

    public ArrayList<PlayerPoints> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<PlayerPoints> players) {
        this.players = players;
    }
}
