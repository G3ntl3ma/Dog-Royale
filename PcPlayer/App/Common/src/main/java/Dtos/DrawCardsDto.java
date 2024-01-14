package Dtos;

import Enums.TypeGame;
import com.google.gson.Gson;

public class DrawCardsDto extends Dto {

    public final int type = TypeGame.drawCards.ordinal() + 200;
    private int[] droppedCards;
    private int[] drawnCards;

    public DrawCardsDto(int[] droppedCards, int[] drawnCards) {
        this.droppedCards = droppedCards;
        this.drawnCards = drawnCards;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public int getType() {
        return type;
    }

    public int[] getDroppedCards() {
        return droppedCards;
    }

    public void setDroppedCards(int[] droppedCards) {
        this.droppedCards = droppedCards;
    }

    public int[] getDrawnCards() {
        return drawnCards;
    }

    public void setDrawnCards(int[] drawnCards) {
        this.drawnCards = drawnCards;
    }
}
