package com.nexusvision.server.model.messages.menu;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Rückgabe der gestarteten, laufenden und beendeten Spielen
 *
 * @author kellerb
 */
@Data
@Builder
public class ReturnGameList extends AbstractMenuMessage {
    private List<StartingGame> startingGames;
    private List<RunningGame> runningGames;
    private List<FinishingGame> finishedGames;

    @Data
    @Builder
    public static class StartingGame{
        private int gameId;
        private int currentPlayerCount;
        private int maxPlayerCount;
    }

    @Data
    @Builder
    public static class RunningGame{
        private int gameId;
        private int currentPlayerCount;
        private int maxPlayerCount;
    }

    @Data
    @Builder
    public static class FinishingGame{
        private int gameId;
        private int winnerPlayerId;
    }
}
