package Dtos.CustomClasses;

import java.util.ArrayList;

public class GameRunningTournament extends Games {
    // Game object for upcoming tournament games in TournamentInfo
    private int startInGames;
    private ArrayList<PlayerPoints> players;

    public GameRunningTournament(int gameId, String gameName, int startInGames , ArrayList<PlayerPoints> players) {
        super(gameId, gameName);
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
