package com.nexusvision.server.handler.message.game;

import com.nexusvision.server.controller.ServerController;
import com.nexusvision.server.handler.Handler;
import com.nexusvision.server.model.messages.game.BoardState;
import com.nexusvision.server.model.messages.game.Move;
import com.nexusvision.server.model.messages.game.TypeGame;
import com.nexusvision.server.model.enums.GameState;
import com.nexusvision.server.controller.GameLobby;
import com.nexusvision.server.model.messages.game.MoveValid;

import java.util.ArrayList;

public class ResponseHandler extends Handler implements GameMessageHandler<Move> {
    @Override
    public String handle(Move message, int clientID) { //maybe add a boolean send boardstate
        ServerController serverController = ServerController.getInstance();
        //message.isUpdated() is always true

        //if we have received the last message of the expected messages for this lobby
        //(some sort of counter int needed for each lobby)
        //and are in the state where we will be responding with boardstate
        //send the board state
        GameLobby gameLobby = serverController.getGameOfPlayer(clientID);
        ArrayList<Integer> clientIds = gameLobby.getPlayerOrderList();
        BoardState boardState = new BoardState();
        boardState.setType(TypeGame.boardState.getOrdinal());
        ArrayList<BoardState.Piece> _pieces = new ArrayList<>();
        //for client in clients in the current game
	    for (int i = 0; i < clientIds.size(); i++) {
	        clientIds.get(i);
	    }
        BoardState.Piece piece = new BoardState.Piece();
        return null;

    }
}
