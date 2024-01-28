package com.nexusvision.server.model.messages.menu;


import com.nexusvision.server.model.messages.AbstractMessage;
import com.nexusvision.server.model.utils.WinnerOrderElement;
import lombok.Data;

import java.util.List;

/**
 * Returns up-to-date tournaments
 *
 * @author felixwr
 */
@Data
public class ReturnTournamentList extends AbstractMessage {
    private int clientId;
    private List<UpcomingTournament> tournamentsUpcoming;
    private List<RunningTournament> tournamentsRunning;
    private List<FinishedTournament> tournamentsFinished;

    @Data
    public static class UpcomingTournament {
        private int tournamentId;
        private int maxPlayer;
        private int maxGames;
        private int currentPlayerCount;
    }

    @Data
    public static class RunningTournament {
        private int tournamentId;
        private int maxPlayer;
        private int maxGames;
        private int gameRunning;
    }

    @Data
    public static class FinishedTournament {
        private int tournamentId;
        private List<WinnerOrderElement> winnerOrder;
    }
}
