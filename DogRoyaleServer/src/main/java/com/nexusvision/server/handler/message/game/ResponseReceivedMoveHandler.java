package com.nexusvision.server.handler.message.game;

import com.nexusvision.server.controller.GameLobby;
import com.nexusvision.server.controller.ServerController;
import com.nexusvision.server.handler.message.MessageHandler;
import com.nexusvision.server.model.enums.Card;
import com.nexusvision.server.service.CardService;
import com.nexusvision.server.model.gamelogic.Game;
import com.nexusvision.server.model.messages.game.DrawCards;
import com.nexusvision.server.model.messages.game.Response;
import com.nexusvision.server.model.messages.game.TypeGame;

import java.util.ArrayList;

//3.4 throw away all cards
//this is not a response to a message
//TODO move this code to where it belongs
public class ResponseReceivedMoveHandler extends MessageHandler<Response> {
    @Override
    protected String performHandle(Response message, int clientID) {
        ServerController serverController = ServerController.getInstance();
        GameLobby gameLobby = serverController.getGameOfPlayer(clientID);
        Game game = gameLobby.getGame();
        DrawCards drawCards = new DrawCards();
        ArrayList<Integer> _droppedCards = new ArrayList<>();
        for(Card card : game.getDiscardedCardList()) {
            _droppedCards.add(card.ordinal());
        }
        drawCards.setType(TypeGame.drawCards.getOrdinal());
        drawCards.setDroppedCards(_droppedCards);
        drawCards.setDrawnCards(new ArrayList<>());
        return gson.toJson(drawCards);
    }
}
