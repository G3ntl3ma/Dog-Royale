package com.nexusvision.server.service;

import com.nexusvision.server.model.messages.game.TypeGame;
import com.nexusvision.server.model.messages.game.UpdateDrawCards;

import java.util.ArrayList;

public class UpdateDrawCardsService {

    public UpdateDrawCards generateClientWithNoCards(int clientId) {
        UpdateDrawCards updateDrawCards = new UpdateDrawCards();
        updateDrawCards.setType(TypeGame.updateDrawCards.getOrdinal());
        UpdateDrawCards.HandCard handCard = new UpdateDrawCards.HandCard();
        handCard.setClientId(clientId);
        handCard.setCount(0);
        ArrayList<UpdateDrawCards.HandCard> handCardList = new ArrayList<>();
        handCardList.add(handCard);
        updateDrawCards.setHandCards(handCardList);
        return updateDrawCards;
    }
}
