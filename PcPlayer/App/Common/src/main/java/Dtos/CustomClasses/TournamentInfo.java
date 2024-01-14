package Dtos.CustomClasses;

import java.util.ArrayList;

public class TournamentInfo extends Tournament {
    // TournamentInfo object for ReturnTournamentInfoDto
    private CurrentGame currentGame;
    private ArrayList<UpcomingGames> upcomingGames;
    private ArrayList<FinishedGames> completedGames;
    private ArrayList<PlayerPoints> currentRankings;

    public TournamentInfo(int tournamentId, CurrentGame currentGame, ArrayList<UpcomingGames> upcomingGames,
        ArrayList<FinishedGames> completedGames, ArrayList<PlayerPoints> currentRankings) {
        super(tournamentId);
        this.currentGame = currentGame;
        this.upcomingGames = upcomingGames;
        this.completedGames = completedGames;
        this.currentRankings = currentRankings;
    }

    public CurrentGame getCurrentGame() {
        return currentGame;
    }

    public void setCurrentGame(CurrentGame currentGame) {
        this.currentGame = currentGame;
    }

    public ArrayList<UpcomingGames> getUpcomingGames() {
        return upcomingGames;
    }

    public void setUpcomingGames(ArrayList<UpcomingGames> upcomingGames) {
        this.upcomingGames = upcomingGames;
    }

    public ArrayList<FinishedGames> getCompletedGames() {
        return completedGames;
    }

    public void setCompletedGames(ArrayList<FinishedGames> completedGames) {
        this.completedGames = completedGames;
    }

    public ArrayList<PlayerPoints> getCurrentRankings() {
        return currentRankings;
    }

    public void setCurrentRankings(ArrayList<PlayerPoints> currentRankings) {
        this.currentRankings = currentRankings;
    }
}
