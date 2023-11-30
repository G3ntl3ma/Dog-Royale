package com.nexusvision.messages.menu;

import lombok.Data;

import java.util.List;

/**
 * Konfiguration des Spiels
 *
 * @author kellerb
 */
@Data
public class ReturnLobbyConfig extends AbstractMenuMessage{
    private enum Penalty {
        excludeFromRound,
        kickFromGame
    }

    private enum Colors{
        farbe1,
        farbe2,
        farbe3,
        farbe4,
        farbe5,
        farbe6
    }

    private enum OrderType{
        fixed,
        random
    }

    private int playerCount;
    private int fieldsize;
    private int figuresPerPlayer;
    private List<Color> colors;
    private List<DrawCardFields> drawCardFields;
    private List<StartFields> startFields;
    private int initialCardsPerPlayer;
    private List<PlayerOrder> playerOrder;
    private List<Observer> observer;
    private int thinkTimePerMove;
    private int visualizationTimePerMove;
    private int consequencesForInvalidMove;
    private int maximumGameDuration;
    private int maximumTotalMoves;


    @Data
    public static class Color{
        private int clientId;
        private int color;

    }

    @Data
    public static class DrawCardFields{
        private int count;
        private List<Integer> positions;
    }

    @Data
    public static class StartFields{
        private int count;
        private List<Integer> positions;
    }

    @Data
    public static class PlayerOrder{
        private OrderType type;
        private List<Order> order;

        @Data
        public static class Order{
            private int clientId;
            private String name;
        }

    }

    @Data
    public static class Observer{
        private int clientId;
        private String name;
    }



}
