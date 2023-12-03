package com.nexusvision.server.model.messages.menu;


import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Rückgabe von aktuellen Turnieren
 *
 * @author kellerb
 */
@Data
@Builder
public class ReturnFindTournament extends AbstractMenuMessage {
    private List<TournamentStart> tournamentStarting;
    private List<TournamentInProgression> tournamentInProgress;
    private List<TournamentFinish> tournamentFinished;
    private int clientId;

    @Data
    @Builder
    public static class TournamentStart{
        private int tournamentId;
        private int maxPlayer;
        private int maxRounds;
        private int currentPlayer;
    }

    @Data
    @Builder
    public static class TournamentInProgression{
        private int tournamentId;
        private int maxPlayer;
        private int maxRounds;
        private int currentPlayer;
    }

    @Data
    @Builder
    public static class TournamentFinish{
        private int tournamentId;
        private List<Winnerorder> winnerOrder;

        @Data
        @Builder
        public static class Winnerorder{
            private int playerId;
            private String name;
        }
        //TODO Variable fertigstellen
    }
}
