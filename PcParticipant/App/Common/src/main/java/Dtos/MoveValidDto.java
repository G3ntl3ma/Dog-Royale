package Dtos;

import Enums.TypeGame;
import com.google.gson.Gson;

public class MoveValidDto extends Dto{

    public final int type = TypeGame.moveValid.ordinal() + 200;
    private boolean skip;
    private int card;
    private int selectedValue;
    private int pieceId;
    private boolean isStarter;
    private int opponentPieceId;
    private boolean validMove;

    public MoveValidDto(boolean skip, int card, int selectedValue, int pieceId, boolean isStarter, int opponentPieceId, boolean validMove) {
        this.skip = skip;
        this.card = card;
        this.selectedValue = selectedValue;
        this.pieceId = pieceId;
        this.isStarter = isStarter;
        this.opponentPieceId = opponentPieceId;
        this.validMove = validMove;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public int getType() {
        return type;
    }

    public boolean isSkip() {
        return skip;
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
    }

    public int getCard() {
        return card;
    }

    public void setCard(int card) {
        this.card = card;
    }
    public int getSelectedValue() {
        return selectedValue;
    }

    public void setSelectedValue(int selectedValue) {
        this.selectedValue = selectedValue;
    }

    public int getPieceId() {
        return pieceId;
    }
    public void setPieceId(int pieceId) {
        this.pieceId = pieceId;
    }

    public boolean isStarter() {
        return isStarter;
    }

    public void setStarter(boolean starter) {
        isStarter = starter;
    }

    public int getOpponentPieceId() {
        return opponentPieceId;
    }

    public void setOpponentPieceId(int opponentPieceId) {
        this.opponentPieceId = opponentPieceId;
    }

    public boolean isValidMove() {
        return validMove;
    }

    public void setValidMove(boolean validMove) {
        this.validMove = validMove;
    }
}
