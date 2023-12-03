package com.nexusvision.server.model.messages.game;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
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
    @Builder
    public static class Piece {
        private int pieceId;
        private int clientId;
        private int position;
        private boolean isOnBench;
        private int inHousePosition;
    }

    @Data
    @Builder
    public static class DiscardItem {
        private int clientId;
        private Card card;
    }
}
