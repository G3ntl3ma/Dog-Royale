package Dtos;

import Dtos.CustomClasses.HandCards;
import Enums.TypeGame;
import com.google.gson.Gson;

public class UpdateDrawCardsDto extends Dto {

    public final int type = TypeGame.updateDrawCards.ordinal() + 200;
    private HandCards[] handCards;

    public UpdateDrawCardsDto(HandCards[] handCards) {
        this.handCards = handCards;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public int getType() {
        return type;
    }

    public HandCards[] getHandCards() {
        return handCards;
    }

    public void setHandCards(HandCards[] handCards) {
        this.handCards = handCards;
    }
}
