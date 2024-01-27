package Dtos;

import Dtos.CustomClasses.DiscardedCard;
import Dtos.CustomClasses.PlayerPiece;
import Dtos.CustomClasses.PlayerPoints;
import Enums.TypeGame;
import com.google.gson.Gson;
import java.util.ArrayList;

public class BoardStateDto extends Dto {
    public final int type = TypeGame.boardState.ordinal() + 200;
    private ArrayList<PlayerPiece> pieces;
    private ArrayList<DiscardedCard> discardPile;
    private int lastPlayedCard;
    private int round;
    private int moveCount;
    private int nextPlayer;
    private boolean gameOver;



    private boolean wasCanceled;
    private ArrayList<PlayerPoints> winnerOrder;

    public BoardStateDto(int lastPlayedCard, ArrayList<PlayerPiece> pieces,ArrayList<DiscardedCard> discardPile,
    int round, int moveCount, int nextPlayer, boolean gameOver, boolean wasCanceled, ArrayList<PlayerPoints> winnerOrder) {
        this.pieces = pieces;
        this.discardPile = discardPile;
        this.lastPlayedCard = lastPlayedCard;
        this.round = round;
        this.moveCount = moveCount;
        this.nextPlayer = nextPlayer;
        this.gameOver = gameOver;
        this.wasCanceled = wasCanceled;
        this.winnerOrder = winnerOrder;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public int getType() {
        return type;
    }

    public ArrayList<PlayerPiece> getPieces() {
        return pieces;
    }

    public void setPieces(ArrayList<PlayerPiece> pieces) {
        this.pieces = pieces;
    }

    public ArrayList<DiscardedCard> getDiscardPile() {
        return discardPile;
    }

    public void setDiscardPile(ArrayList<DiscardedCard> discardPile) {
        this.discardPile = discardPile;
    }

    public int getLastPlayedCard() {
        return lastPlayedCard;
    }

    public void setLastPlayedCard(int lastPlayedCard) {
        this.lastPlayedCard = lastPlayedCard;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public int getNextPlayer() {
        return nextPlayer;
    }

    public void setNextPlayer(int nextPlayer) {
        this.nextPlayer = nextPlayer;
    }

    public boolean isGameOver() {
        return gameOver;
    }
    public boolean isWasCanceled() {
        return wasCanceled;
    }

    public void setWasCanceled(boolean wasCanceled) {
        this.wasCanceled = wasCanceled;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public ArrayList<PlayerPoints> getWinnerOrder() {
        return winnerOrder;
    }

    public void setWinnerOrder(ArrayList<PlayerPoints> winnerOrder) {
        this.winnerOrder = winnerOrder;
    }

    public void setMoveCount(int moveCount) {
        this.moveCount = moveCount;
    }
}