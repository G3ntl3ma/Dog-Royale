package com.nexusvision.messages.game;


import lombok.Data;

/**
 * Rückgabe von aktuellen Turnieren
 *
 * @author kellerb
 */
@Data
public class ReturnFindTournament {
    private int clientId;

    @Data
    public static class tournamentStarting{
        private int tournamentId;
        private int maxPlayer;
        private int maxRounds;
        private int currentPlayer;
    }
}
