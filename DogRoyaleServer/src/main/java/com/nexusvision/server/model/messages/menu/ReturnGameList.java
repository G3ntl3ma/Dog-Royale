package com.nexusvision.server.model.messages.menu;

import com.nexusvision.server.model.messages.AbstractMessage;
import com.nexusvision.server.model.utils.PlayerElement;
import com.nexusvision.server.model.utils.WinnerOrderElement;
import lombok.Data;

import java.util.List;

/**
 * Returning starting, in progress and finished games
 *
 * @author felixwr
 */
@Data
public class ReturnGameList extends AbstractMessage {
    private List<NotFinishedGame> gamesUpcoming;
    private List<NotFinishedGame> gamesRunning;
    private List<FinishedGame> gamesFinished;

    @Data
    public static class NotFinishedGame extends Game {
        private List<PlayerElement> playerOrder;
        private int maxPlayerCount;
    }

    @Data
    public static class FinishedGame extends Game {
        private boolean wasCanceled;
    }

    @Data
    private abstract static class Game {
        private int gameId;
        private String gameName;
        private List<WinnerOrderElement> winnerOrder;
    }
}
