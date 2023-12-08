package com.nexusvision.server.handler.message.game;

import com.nexusvision.server.controller.ServerController;
import com.nexusvision.server.handler.Handler;
import com.nexusvision.server.model.enums.CardType;
import com.nexusvision.server.model.enums.Penalty;
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
	GameLobby gameLobby = serverController.getGameOfPlayer(clientID);
	//TODO if game null then error

	//try move only if game running and unpaused
	if(gameLobby.getGameState() == GameState.IN_PROGRESS && !gameLobby.isPaused()) {
	    boolean moveLegal = gameLobby.tryMove(message.isSkip(), message.getCard().ordinal(), message.getSelectedValue(), message.getPieceId(),
						  message.isStarter(), message.getOpponentPieceId());

	    if(!moveLegal && gameLobby.getGame().getConsequences() == Penalty.kickFromGame) {
		//3.14 send kick message to everyone
		//remove clientId from the arraylist in the lobby
		gameLobby.removePlayer(clientID);
		int playerId = gameLobby.getGame().getPlayerToMoveColor();
		int clientId = gameLobby.getPlayerOrderList().get(playerId);
		String reason = "illegal move";
		Kick kick = new Kick();
		kick.setType(TypeGame.kick.getOrdinal());
		//kick.setReason(reason); //TODO doesnt work
		serverController.sendToAllLobbyMembers(gameLobby, gson.toJson(kick));
	    }
	    CardType drawnCard = gameLobby.getGame().getDrawnCard().getType();
	    MoveValid moveVal = new MoveValid();
	    moveVal.setSkip(message.isSkip());
	    moveVal.setCard(message.getCard());
	    moveVal.setPieceId(message.getPieceId());
	    moveVal.setStarter(message.isStarter());
	    moveVal.setOpponentPieceId(message.getOpponentPieceId());
	    serverController.sendToAllLobbyMembers(gameLobby, gson.toJson(moveVal));
	    DrawCards drawnCards = new DrawCards();
	    drawnCards.setType(TypeGame.drawCards.getOrdinal());
	    drawnCards.setDroppedCards(new ArrayList<CardType>()); //empty array vs null???
	    if(drawnCard == null) {
		drawnCards.setDrawnCards(new ArrayList<CardType>()); //empty array vs null???
	    }
	    else {
		drawnCards.setDrawnCards(new ArrayList<CardType>(Arrays.asList(drawnCard)));
	    }
	    return gson.toJson(drawnCards);
	}
        return null;
    }
}
