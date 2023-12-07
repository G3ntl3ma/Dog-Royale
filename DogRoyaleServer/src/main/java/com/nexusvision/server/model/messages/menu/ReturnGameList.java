package com.nexusvision.server.model.messages.menu;

import lombok.Data;

import java.util.List;

/**
 * Returning starting, in progress and finished games
 *
 * @author kellerb
 */
@Data
public class ReturnGameList extends AbstractMenuMessage {
    private List<StartingGame> startingGames;
    private List<RunningGame> runningGames;
    private List<FinishingGame> finishedGames;

    @Data
    public static class StartingGame{
        private int gameId;
        private int currentPlayerCount;
        private int maxPlayerCount;
    }

    @Data
    public static class RunningGame{
        private int gameId;
        private int currentPlayerCount;
        private int maxPlayerCount;
    }

    @Data
    public static class FinishingGame{
        private int gameId;
        private int winnerPlayerId;
    }
}
