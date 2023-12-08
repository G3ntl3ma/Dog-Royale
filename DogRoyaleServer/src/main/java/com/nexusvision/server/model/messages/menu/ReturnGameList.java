package com.nexusvision.server.model.messages.menu;

import com.nexusvision.server.model.messages.AbstractMessage;
import lombok.Data;

import java.util.List;

/**
 * Returning starting, in progress and finished games
 *
 * @author kellerb, felixwr
 */
@Data
public class ReturnGameList extends AbstractMessage {
    private List<Game> startingGames;
    private List<Game> runningGames;
    private List<Game> finishedGames;

    @Data
    public static class Game {
        private int gameId;
        private int currentPlayerCount;
        private int maxPlayerCount;
        private int winnerPlayerId;
    }
//
//    @Data
//    public static class RunningGame{
//        private int gameId;
//        private int currentPlayerCount;
//        private int maxPlayerCount;
//    }
//
//    @Data
//    public static class FinishingGame{
//        private int gameId;
//        private int winnerPlayerId;
//    }
}
