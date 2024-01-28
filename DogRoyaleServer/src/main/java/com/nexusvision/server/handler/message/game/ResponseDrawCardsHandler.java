package com.nexusvision.server.handler.message.game;

import com.nexusvision.server.controller.GameLobby;
import com.nexusvision.server.controller.ServerController;
import com.nexusvision.server.handler.message.MessageHandler;
import com.nexusvision.server.model.gamelogic.Game;
import com.nexusvision.server.model.messages.game.*;
import com.nexusvision.server.model.messages.game.UpdateDrawCards;

import java.util.ArrayList;

//3.5
public class ResponseDrawCardsHandler extends MessageHandler<Response> {
    @Override
    protected void performHandle(Response message, int clientId) {
        //update draw card
        // TODO: Implement
//        ServerController serverController = ServerController.getInstance();
//        GameLobby gameLobby = serverController.getGameOfPlayer(clientID);
//        Game game = gameLobby.getGame();
//
//        ArrayList<UpdateDrawCards.HandCard> _handCards = new ArrayList<>();
//        ArrayList<Integer> clientIds = gameLobby.getPlayerOrderList();
//        for (int playerId = 0; playerId < clientIds.size(); playerId++) {
//            int clientId = clientIds.get(playerId);
//            int count = game.getPlayerList().get(playerId).getCardList().size();
//            UpdateDrawCards.HandCard _handCard = new UpdateDrawCards.HandCard();
//            _handCard.setClientId(clientId);
//            _handCard.setCount(count);
//            _handCards.add(_handCard);
//        }
//        UpdateDrawCards updateDrawCards = new UpdateDrawCards();
//        updateDrawCards.setHandCards(_handCards);
//        return gson.toJson(updateDrawCards);

    }
}
