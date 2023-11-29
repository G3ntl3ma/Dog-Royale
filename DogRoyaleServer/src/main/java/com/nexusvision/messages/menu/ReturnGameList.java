package com.nexusvision.messages.menu;

import com.nexusvision.messages.game.AbstractGameMessage;
import lombok.Data;

/**
 * Rückgabe der gestarteten, laufenden und beendeten Spielen
 *
 * @author kellerb
 */
@Data
public class ReturnGameList extends AbstractMenuMessage {

    @Data
    public static class startingGames{
        private int gameId;
        private int currentPlayerCount;
        private int maxPlayerCount;
    }

    @Data
    public static class runningGames{
        private int gameId;
        private int currentPlayerCount;
        private int maxPlayerCount;
    }

    @Data
    public static class finishedGames{
        private int gameId;
        private int winnerPlayerId;
    }
}
