package com.nexusvision.server.handler.message.game;

import com.nexusvision.server.common.ChannelType;
import com.nexusvision.server.controller.GameLobby;
import com.nexusvision.server.controller.MessageBroker;
import com.nexusvision.server.controller.ServerController;
import com.nexusvision.server.handler.message.MessageHandler;
import com.nexusvision.server.model.enums.Card;
import com.nexusvision.server.service.CardService;
import com.nexusvision.server.model.gamelogic.Game;
import com.nexusvision.server.model.messages.game.DrawCards;
import com.nexusvision.server.model.messages.game.Move;
import com.nexusvision.server.model.messages.game.TypeGame;

import java.util.ArrayList;

//get drawn cards at the start of the game
//this is not a response to a message
//TODO move this code to where it belongs
public class ThrowAwayClass extends MessageHandler<Move> {
    @Override
    protected void performHandle(Move message, int clientID) {
        ServerController serverController = ServerController.getInstance();
        GameLobby gameLobby = serverController.getGameOfPlayer(clientID);
        Game game = gameLobby.getGame();
        ArrayList<Integer> _drawnCards = new ArrayList<>();
        for(int playerId = 0; playerId < gameLobby.getPlayerOrderList().size(); playerId++) {
            if(clientID == gameLobby.getPlayerOrderList().get(playerId)) {
                for(Card card : game.getPlayerList().get(playerId).getCardList()) {
                    _drawnCards.add(card.ordinal());
                }
            }
        }

        DrawCards drawCards = new DrawCards();
        drawCards.setType(TypeGame.drawCards.getOrdinal());
        drawCards.setDroppedCards(new ArrayList<>());
        drawCards.setDrawnCards(_drawnCards);
        String response = gson.toJson(drawCards);
        MessageBroker.getInstance().sendMessage(ChannelType.SINGLE, clientID, response);
    }
}
