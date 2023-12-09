package com.example.myapplication.messages.game;

import lombok.Data;

import java.util.List;

@Data
public class BoardState extends AbstractGameMessage {
    private List<Piece> pieces;
    private List<DiscardItem> discardPile;
    private Card lastPlayedCard;
    private int round;
    private int moveCount;
    private int nextPlayer;
    private boolean gameOver;
    private List<Integer> winnerOrder;



    @Data
    public static class Piece {
        private int pieceId;
        private int clientId;
        private int position;
        private boolean isOnBench;
        private int inHousePosition;
    }

    @Data
    public static class DiscardItem {
        private int clientId;
        private Card card;
    }
}
