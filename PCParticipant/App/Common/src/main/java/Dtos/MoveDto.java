package Dtos;

import Enums.Card;
import Enums.TypeGame;
import com.google.gson.Gson;

public class MoveDto extends Dto {
    private boolean skip;
    private int card;
    private int selectedValue;
    private int pieceId;
    private boolean isStarter;
    private int opponentPieceId;

    public MoveDto(boolean skip, int card, int selectedValue, int pieceId, boolean isStarter, int opponentPieceId) {
        super(TypeGame.move.ordinal() + 200);
        this.skip = skip;
        this.card = card;
        this.selectedValue = selectedValue;
        this.pieceId = pieceId;
        this.isStarter = isStarter;
        this.opponentPieceId = opponentPieceId;
    }

    public MoveDto(){super(TypeGame.move.ordinal() + 200);}

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public boolean getSkip() {
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

    public boolean getStarter() {
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
}
