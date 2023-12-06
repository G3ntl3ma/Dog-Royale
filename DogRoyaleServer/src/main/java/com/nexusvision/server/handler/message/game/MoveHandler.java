package com.nexusvision.server.handler.message.game;

import com.nexusvision.server.controller.ServerController;
import com.nexusvision.server.handler.Handler;
import com.nexusvision.server.model.messages.game.Move;
import com.nexusvision.server.model.enums.GameState;
import com.nexusvision.server.controller.GameLobby;
import com.nexusvision.server.model.messages.game.MoveValid;

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

	    MoveValid moveVal = new MoveValid();
	    moveVal.setSkip(message.isSkip());
	    moveVal.setCard(message.getCard());
	    //moveVal.setSelectedValue(message.getSelectedValue());
	    moveVal.setPieceId(message.getPieceId());
	    //moveVal.setIsStarter(message.isStarter()); //TODO doesnt work
	    moveVal.setOpponentPieceId(message.getOpponentPieceId());
	    //send to all clients
		//TODO only send to all clients in a lobby
	    serverController.sendToAllClients(gson.toJson(moveVal)); //idk if this method works
	}
        return null;
    }
}
