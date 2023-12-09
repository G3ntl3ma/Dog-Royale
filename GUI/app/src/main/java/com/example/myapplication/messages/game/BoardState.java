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
        private Integer position;
        private boolean isOnBench;
        private Integer inHousePosition;

        public Piece(int pieceId, int clientId, Integer position, boolean isOnBench, Integer inHousePosition)
        {
            this.pieceId = pieceId;
            this.clientId = clientId;
            this.position = position;
            this.isOnBench = isOnBench;
            this.inHousePosition = inHousePosition;
        }

        public int getPieceId()
        {
            return pieceId;
        }

        public int getClientId()
        {
            return clientId;
        }

        public Integer getPosition()
        {
            return position;
        }

        public boolean getIsOnBench()
        {
            return isOnBench;
        }

        public Integer getInHousePosition()
        {
            return inHousePosition;
        }

    }

    @Data
    public static class DiscardItem {
        private int clientId;
        private Card card;
    }

    public List<Piece> getPieces()
    {
        return pieces;
    }

    public List<DiscardItem> getDiscardPile()
    {
        return discardPile;
    }

    public Card getLastPlayedCard()
    {
        return lastPlayedCard;
    }

    public int getRound()
    {
        return round;
    }

    public int getMoveCount()
    {
        return moveCount;
    }

    public int getNextPlayer()
    {
        return nextPlayer;
    }

    public boolean getGameOver()
    {
        return gameOver;
    }

    public List<Integer> getWinnerOrder()
    {
        return winnerOrder;
    }


    public BoardState(List<Piece> pieces, List<DiscardItem> discardPile, Card lastPlayedCard, int round, int moveCount, int nextPlayer, boolean gameOver, List<Integer> winnerOrder)
    {
        this.pieces = pieces;
        this.discardPile = discardPile;
        this.lastPlayedCard = lastPlayedCard;
        this.round = round;
        this.moveCount = moveCount;
        this.nextPlayer = nextPlayer;
        this.gameOver = gameOver;
        this.winnerOrder = winnerOrder;
    }

}
