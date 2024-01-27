package Dtos;

import Dtos.CustomClasses.HandCards;
import Enums.TypeGame;
import com.google.gson.Gson;

public class UpdateDrawCardsDto extends Dto {

    private HandCards[] handCards;

    public UpdateDrawCardsDto(HandCards[] handCards) {
        super(TypeGame.updateDrawCards.ordinal() + 200);
        this.handCards = handCards;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public HandCards[] getHandCards() {
        return handCards;
    }

    public void setHandCards(HandCards[] handCards) {
        this.handCards = handCards;
    }
}
