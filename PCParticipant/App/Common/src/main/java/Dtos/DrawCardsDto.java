package Dtos;

import Enums.TypeGame;
import com.google.gson.Gson;

public class DrawCardsDto extends Dto {

    private int[] droppedCards;
    private int[] drawnCards;

    public DrawCardsDto(int[] droppedCards, int[] drawnCards) {
        super(TypeGame.drawCards.ordinal() + 200);
        this.droppedCards = droppedCards;
        this.drawnCards = drawnCards;
    }

    public DrawCardsDto(){
        super(TypeGame.drawCards.ordinal() + 200);
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
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
