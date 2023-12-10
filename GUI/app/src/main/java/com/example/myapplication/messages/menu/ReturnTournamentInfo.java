package com.example.myapplication.messages.menu;


import java.util.List;

import lombok.Data;

/**
 * Server gibt angefragte Turnierinformationen
 *
 * @author kellerb
 */
@Data
public class ReturnTournamentInfo extends AbstractMenuMessage {
    //TODO implement variables
    private List<TournamentInfo> tournamentInfo;

    @Data
    public static class TournamentInfo{
        private int tournamentId;
        private List<CurrentGame> currentGame;
        private List<UpcomingGames> upcomingGames;
        private List<CompletedGames> completedGames;
        private List<CurrentRankings> currentRankings;

        @Data
        public static class CurrentGame{
            private int gameId;
            private int playerCount;
            private int maxPlayerCount;
            private int currentRound;
            private List<Player> players;

            @Data
            public static class Player{
                private int clientId;
                private String name;
                private int points;
            }
        }

        @Data
        public static class UpcomingGames{
            private int gameId;
            private int startInGames;
            private List<Player> players;

            @Data
            public static class Player{
                private int clientId;
                private String name;
                private int points;
            }
        }

        @Data
        public static class CompletedGames{
            private int gameId;
            private int winnerPlayerId;
        }

        @Data
        public static class CurrentRankings{
            private int clientId;
            private String name;
            private int points;
        }
    }
}

