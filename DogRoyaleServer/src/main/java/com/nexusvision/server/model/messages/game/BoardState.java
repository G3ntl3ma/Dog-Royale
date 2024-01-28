package com.nexusvision.server.model.messages.game;

import com.nexusvision.server.model.enums.Card;
import com.nexusvision.server.model.messages.AbstractMessage;
import com.nexusvision.server.model.utils.WinnerOrderElement;
import lombok.Data;

import java.util.List;

@Data
public class BoardState extends AbstractMessage {
    private List<Piece> pieces;
    private List<DiscardItem> discardPile;
    private int lastPlayedCard;
    private int round;
    private int moveCount;
    private int nextPlayer;
    private boolean gameOver;
    private boolean wasCanceled;
    private List<WinnerOrderElement> winnerOrder;

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
        private int card;
    }
}
