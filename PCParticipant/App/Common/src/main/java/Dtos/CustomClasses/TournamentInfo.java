package Dtos.CustomClasses;

import java.util.ArrayList;

public class TournamentInfo extends Tournament {
    // TournamentInfo object for ReturnTournamentInfoDto
    private CurrentGame gameRunning;
    private ArrayList<GameRunningTournament> gamesUpcoming;
    private ArrayList<GamesFinishedTournament> gamesFinished;
    private ArrayList<PlayerPoints> currentRankings;

    public TournamentInfo(int tournamentId, CurrentGame gameRunning, ArrayList<GameRunningTournament> gamesUpcoming,
                          ArrayList<GamesFinishedTournament> gamesFinished, ArrayList<PlayerPoints> currentRankings) {
        super(tournamentId);
        this.gameRunning = gameRunning;
        this.gamesUpcoming = gamesUpcoming;
        this.gamesFinished = gamesFinished;
        this.currentRankings = currentRankings;
    }

    public CurrentGame getGameRunning() {
        return gameRunning;
    }

    public void setGameRunning(CurrentGame gameRunning) {
        this.gameRunning = gameRunning;
    }

    public ArrayList<GameRunningTournament> getUpcomingGames() {
        return gamesUpcoming;
    }

    public void setUpcomingGames(ArrayList<GameRunningTournament> gameUpcomingGamesTournaments) {
        this.gamesUpcoming = gameUpcomingGamesTournaments;
    }

    public ArrayList<GamesFinishedTournament> getGamesFinished() {
        return gamesFinished;
    }

    public void setGamesFinished(ArrayList<GamesFinishedTournament> gamesFinished) {
        this.gamesFinished = gamesFinished;
    }

    public ArrayList<PlayerPoints> getCurrentRankings() {
        return currentRankings;
    }

    public void setCurrentRankings(ArrayList<PlayerPoints> currentRankings) {
        this.currentRankings = currentRankings;
    }
}
