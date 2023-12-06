package com.example.myapplication.messages.menu;

import java.util.List;
import java.util.Observer;

import lombok.Data;

/**
 * Konfiguration des Spiels
 *
 * @author Mattes
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

    private Integer playerCount;
    private Integer fieldsize;
    private Integer figuresPerPlayer;
    private List<Color> colors;
    private DrawCardFields drawCardFields;
    private StartFields startFields;
    private Integer initialCardsPerPlayer;
    private PlayerOrder playerOrder;
    private List<Observer> observer;
    private Integer thinkTimePerMove;
    private Integer visualizationTimePerMove;
    private Integer consequencesForInvalidMove;
    private Integer maximumGameDuration;
    private Integer maximumTotalMoves;


    @Data
    public static class Color{
        private Integer clientId;
        private Integer color;

    }

    @Data
    public static class DrawCardFields{
        private Integer count;
        private List<Integer> positions;
    }

    @Data
    public static class StartFields{
        private Integer count;
        private List<Integer> positions;
    }

    @Data
    public class PlayerOrder{
        private OrderType type;
        private List<Order> order;

        @Data
        public class Order{
            private Integer clientId;
            private String name;
        }

    }

    @Data
    public static class Observer{
        private Integer clientId;
        private String name;
    }



}


