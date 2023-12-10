package com.example.myapplication.messages.game;

import com.example.myapplication.DiscardPileViewModel;
import com.example.myapplication.MainActivity;

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
    private DiscardPileViewModel discardPileViewModel;



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

        public DiscardItem(int clientId, Card card){
            this.clientId = clientId;
            this.card = card;
        }

        public int getClientId() {
            return clientId;
        }

        public Card getCard() {
            return card;
        }
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
        this.discardPileViewModel = MainActivity.getDiscardPileViewModel();

    }

    /** Add a new Item to the Discard Pile, which will be shown in the Game History
     *
     * @param item
     */
    public void addDiscardItem(DiscardItem item){
        discardPile.add(item);
        this.discardPileViewModel.set_DiscardPile(discardPile);
        }

    /** Remove all items from the DiscardPile
     *
     */
    void clearDiscardPile(){
        discardPile.clear();
        this.discardPileViewModel.set_DiscardPile(discardPile);
        }

        /*
        The getCard methods are created for Testing
         */
        public static Card getCard12(){
            return Card.card12;
        }
        public static Card getCardMagnet(){
            return Card.magnetCard;
        }
        public static Card getCard9(){
            return Card.card9;
        }
        public static Card getCard3(){
            return Card.card3;
        }
        public static Card getCardCopy(){
            return Card.copyCard;
        }
}
