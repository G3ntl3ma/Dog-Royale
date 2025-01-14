package com.nexusvision.server.model.messages.menu;


import com.nexusvision.server.model.messages.AbstractMessage;
import lombok.Data;

import java.util.List;

/**
 * Returns up-to-date tournaments
 *
 * @author kellerb
 */
@Data
public class ReturnFindTournament extends AbstractMessage {
    private List<TournamentStart> tournamentStarting;
    private List<TournamentInProgression> tournamentInProgress;
    private List<TournamentFinish> tournamentFinished;
    private int clientId;

    @Data
    public static class TournamentStart{
        private int tournamentId;
        private int maxPlayer;
        private int maxRounds;
        private int currentPlayer;
    }

    @Data
    public static class TournamentInProgression{
        private int tournamentId;
        private int maxPlayer;
        private int maxRounds;
        private int currentPlayer;
    }

    @Data
    public static class TournamentFinish{
        private int tournamentId;
        private List<Winnerorder> winnerOrder;

        @Data
        public static class Winnerorder{
            private int playerId;
            private String name;
        }
        //TODO Variable fertigstellen
    }
}
