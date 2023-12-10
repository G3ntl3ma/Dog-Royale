package com.example.myapplication.messages.menu;



import lombok.Data;

import java.util.HashMap;
import java.util.List;

/**
 * Rückgabe von aktuellen Turnieren
 *
 * @author kellerb
 */
@Data
public class ReturnFindTournament extends AbstractMenuMessage {
    private List<TournamentStart> tournamentStarting;
    private List<TournamentInProgress> tournamentInProgress;
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
    public static class TournamentInProgress{
        private int tournamentId;
        private int maxPlayer;
        private int maxRounds;
        private int currentPlayer;
    }

    @Data
    public static class TournamentFinish{
        private int tournamentId;

        private List<WinnerOrder> winnerOrder;
    }

    @Data
    public static class WinnerOrder{
        private String playerId;
        private String name;
    }

    // TODO: getResponse()-Methode schreiben
}
