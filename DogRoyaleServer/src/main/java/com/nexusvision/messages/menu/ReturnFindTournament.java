package com.nexusvision.messages.menu;


import lombok.Data;

/**
 * Rückgabe von aktuellen Turnieren
 *
 * @author kellerb
 */
@Data
public class ReturnFindTournament extends AbstractMenuMessage {
    private int clientId;

    @Data
    public static class tournamentStarting{
        private int tournamentId;
        private int maxPlayer;
        private int maxRounds;
        private int currentPlayer;
    }

    @Data
    public static class tournamentInProgress{
        private int tournamentId;
        private int maxPlayer;
        private int maxRounds;
        private int currentPlayer;
    }

    @Data
    public static class tournamentFinished{
        private int tournamentId;
        //TODO Variable fertigstellen
    }
}
