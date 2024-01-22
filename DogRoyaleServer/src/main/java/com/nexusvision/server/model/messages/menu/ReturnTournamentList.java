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
    private List<TournamentStart> tournamentsUpcoming;
    private List<TournamentInProgression> tournamentsRunning;
    private List<TournamentFinish> tournamentsFinished;

    @Data
    public static class TournamentStart{
        private int tournamentId;
        private int maxPlayer;
        private int maxGames;
        private int currentPlayerCount;
    }

    @Data
    public static class TournamentInProgression{
        private int tournamentId;
        private int maxPlayer;
        private int maxGames;
        private int gameRunning;
    }

    @Data
    public static class TournamentFinish{
        private int tournamentId;
        private List<WinnerOrderElement> winnerOrder;
    }
}
