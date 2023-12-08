package com.nexusvision.server.handler.message.game;

import com.nexusvision.server.controller.ServerController;
import com.nexusvision.server.handler.Handler;
import com.nexusvision.server.model.enums.CardType;
import com.nexusvision.server.model.messages.game.Move;
import com.nexusvision.server.model.enums.GameState;
import com.nexusvision.server.controller.GameLobby;
import com.nexusvision.server.model.messages.game.MoveValid;
import com.nexusvision.server.model.messages.game.*;

import java.util.ArrayList;
import java.util.Arrays;


//respond with 3.8 (validity of move to all)
//also kind of needs to respond with 3.4 to one client
public class MoveHandler extends Handler implements GameMessageHandler<Move> {
    @Override
    public String handle(Move message,int clientID) {
	ServerController serverController = ServerController.getInstance();
	//find game corresponding to clientID
	GameLobby game = serverController.getGameOfPlayer(clientID);
	//TODO if game null then error

	//try move only if game running and unpaused
	if(game.getGameState() == GameState.IN_PROGRESS && !game.isPaused()) {
	    boolean moveLegal = game.tryMove(message.isSkip(), message.getCard().ordinal(), message.getSelectedValue(), message.getPieceId(),
					     message.isStarter(), message.getOpponentPieceId());
		CardType drawnCard = null; //TODO get the drawn card
	    MoveValid moveVal = new MoveValid();
	    moveVal.setSkip(message.isSkip());
	    moveVal.setCard(message.getCard());
	    moveVal.setPieceId(message.getPieceId());
	    moveVal.setStarter(message.isStarter());
	    moveVal.setOpponentPieceId(message.getOpponentPieceId());
	    serverController.sendToAllLobbyMembers(game, gson.toJson(moveVal));
		DrawCards drawnCards = new DrawCards();
		drawnCards.setType(TypeGame.drawCards.getOrdinal());
		if(drawnCard == null) {
			drawnCards.setDrawnCards(new ArrayList<CardType>());
		}
		else {
			drawnCards.setDrawnCards(new ArrayList<CardType>(Arrays.asList(drawnCard)));
		}
		return gson.toJson(drawnCards);
	}
        return null;
    }
}
