package com.example.myapplication.messages.menu;


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
    private List<FinishedGame> finishedGames;


    @Data
    public static class StartingGame{
        private int gameId;
        private int currentPlayerCount;
        private int maxPlayerCount;

        public StartingGame(int gameId, int currentPlayerCount, int maxPlayerCount) {
            this.gameId = gameId;
            this.currentPlayerCount = currentPlayerCount;
            this.maxPlayerCount = maxPlayerCount;
        }

        public int getGameId() {
            return gameId;
        }

        public int getCurrentPlayerCount() {
            return currentPlayerCount;
        }

        public int getMaxPlayerCount() {
            return maxPlayerCount;
        }


    }

    @Data
    public static class RunningGame{
        private int gameId;
        private int currentPlayerCount;
        private int maxPlayerCount;

        public RunningGame(int gameId, int currentPlayerCount, int maxPlayerCount) {
            this.gameId = gameId;
            this.currentPlayerCount = currentPlayerCount;
            this.maxPlayerCount = maxPlayerCount;
        }

        public int getGameId() {
            return gameId;
        }

        public int getCurrentPlayerCount() {
            return currentPlayerCount;
        }

        public int getMaxPlayerCount() {
            return maxPlayerCount;
        }
    }

    @Data
    public static class FinishedGame{
        private int gameId;
        private int winnerPlayerId;

        public FinishedGame(int gameId, int winnerPlayerId) {
            this.gameId = gameId;
            this.winnerPlayerId = winnerPlayerId;
        }

        public int getGameId() {
            return gameId;
        }

        public int getWinnerPlayerId() {
            return winnerPlayerId;
        }
    }

    public ReturnGameList(List<StartingGame> startingGames, List<RunningGame> runningGames, List<FinishedGame> finishedGames) {
        this.startingGames = startingGames;
        this.runningGames = runningGames;
        this.finishedGames = finishedGames;
    }

    public List<StartingGame> getStartingGames() {
        return startingGames;
    }

    public void setStartingGames(List<StartingGame> startingGames) {
        this.startingGames = startingGames;
    }

    public List<RunningGame> getRunningGames() {
        return runningGames;
    }

    public void setRunningGames(List<RunningGame> runningGames) {
        this.runningGames = runningGames;
    }

    public List<FinishedGame> getFinishedGames() {
        return finishedGames;
    }

    public void setFinishedGames(List<FinishedGame> finishedGames) {
        this.finishedGames = finishedGames;
    }






}
