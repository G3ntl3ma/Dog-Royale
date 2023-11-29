package com.nexusvision.messages.menu;


import com.nexusvision.messages.game.BoardState;
import lombok.Data;

import java.util.List;

/**
 * Rückgabe von aktuellen Turnieren
 *
 * @author kellerb
 */
@Data
public class ReturnFindTournament extends AbstractMenuMessage {
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
        //TODO Variable fertigstellen
    }
}
