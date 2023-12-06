package com.example.myapplication.messages.menu;

import com.example.myapplication.messages.game.AbstractGameMessage;
import lombok.Data;

import java.util.List;

/**
 * Rückgabe der gestarteten, laufenden und beendeten Spielen
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
