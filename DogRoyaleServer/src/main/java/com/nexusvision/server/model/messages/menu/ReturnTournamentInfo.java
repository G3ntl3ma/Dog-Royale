package com.nexusvision.server.model.messages.menu;

import com.nexusvision.server.model.messages.AbstractMessage;
import lombok.Data;

import java.util.List;

/**
 * Server returns tournament information
 *
 * @author felixwr
 */
@Data
public class ReturnTournamentInfo extends AbstractMessage {
    private List<TournamentInfo> tournamentInfo;

    @Data
    public static class TournamentInfo{
        private int tournamentId;
        private RunningGame gameRunning;
        private List<UpcomingGames> gamesUpcoming;
        private List<FinishedGames> gamesFinished;
        private List<TournamentPlayer> currentRankings;

        @Data
        public static class RunningGame {
            private int gameId;
            private int playerCount;
            private int maxPlayerCount;
            private int currentRound;
            private List<TournamentPlayer> players;
        }

        @Data
        public static class UpcomingGames{
            private int gameId;
            private List<TournamentPlayer> players;
        }

        @Data
        public static class FinishedGames {
            private int gameId;
            private boolean wasCanceled;
            private List<TournamentPlayer> winnerOrder;
        }

        @Data
        public static class TournamentPlayer {
            private int clientId;
            private String name;
            private int points;
        }
    }
}
