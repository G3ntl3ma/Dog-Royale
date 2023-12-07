package com.nexusvision.server.handler.message.game;

import com.nexusvision.server.controller.ServerController;
import com.nexusvision.server.handler.Handler;
import com.nexusvision.server.model.enums.CardType;
import com.nexusvision.server.model.gamelogic.Game;
import com.nexusvision.server.model.gamelogic.Player;
import com.nexusvision.server.model.gamelogic.Figure;
import com.nexusvision.server.model.messages.game.BoardState;
import com.nexusvision.server.model.messages.game.Move;
import com.nexusvision.server.model.messages.game.TypeGame;
import com.nexusvision.server.model.enums.GameState;
import com.nexusvision.server.controller.GameLobby;
import com.nexusvision.server.model.messages.game.MoveValid;

import java.util.List;
import java.util.stream.Collectors;

import java.util.ArrayList;

public class ResponseHandler extends Handler implements GameMessageHandler<Move> {
    @Override
    public String handle(Move message, int clientID) {
	ServerController serverController = ServerController.getInstance();
	//message.isUpdated() is always true

	//if we have received the last message of the expected messages for this lobby
	//(some sort of list of clientIds needed for each lobby)
	//and are in the state where we will be responding with boardstate
	//send the board state
	GameLobby gameLobby = serverController.getGameOfPlayer(clientID);

	gameLobby.receiveResponse(clientID);

	//TODO idk what to do if somebody just loses connection
	if(gameLobby.receivedFromEveryone()) {
	    getBoardState(gameLobby);
	    gameLobby.resetResponseList();
	}
    
	return null;

    }

    private BoardState getBoardState(GameLobby gameLobby) {
	Game game = gameLobby.getGame();
	BoardState boardState = new BoardState();
	boardState.setType(TypeGame.boardState.getOrdinal());

	ArrayList<Integer> playerOrderList = gameLobby.getPlayerOrderList();
	
	//pieces position
	ArrayList<BoardState.Piece> _pieces = new ArrayList<>();
	for (int playerId = 0; playerId < playerOrderList.size(); playerId++) {
	    int clientId = playerOrderList.get(playerId);
	    Player player = game.getPlayers().get(playerId/*aka color in the game logic code*/);
	    
	    for (int pieceId = 0; pieceId < player.getFigures().size(); pieceId++) {
		BoardState.Piece piece = new BoardState.Piece();
		
		Figure figure = player.getFigures().get(pieceId);
		int position = figure.getField().getVal();
		boolean isOnBench  = figure.isInBank();
		Integer inHousePosition  = game.getHousePosition(playerId, pieceId);
		piece.setPieceId(pieceId);
		piece.setClientId(clientId);
		piece.setPosition(position);
		piece.setOnBench(isOnBench);
		piece.setInHousePosition(inHousePosition);
		_pieces.add(piece);
	    }
	}
	boardState.setPieces(_pieces);
	
	ArrayList<BoardState.DiscardItem> _cards = new ArrayList<>();
	for (int i = 0; i < game.getPile().size(); i++) {
	    CardType cardType = game.getPile().get(i).getType();
	    BoardState.DiscardItem _card = new BoardState.DiscardItem();
	    _card.setClientId(666); //fake clientId because it doesnt matter
	    _card.setCard(cardType);
	    _cards.add(_card);
	}
	boardState.setDiscardPile(_cards);
	
	//get last played card
	boardState.setLastPlayedCard(game.getLastCard().getType());
	boardState.setRound(game.getRound());
	boardState.setMoveCount(game.getMovesMade());
	//boardState.setNextPlayer(game.getMovesMade()); //TODO
	boardState.setGameOver(game.checkOver());
	ArrayList<Player> playerWinOrder = new ArrayList<>();
	game.getOrder(playerWinOrder);
	List<Integer> playerWinOrderId = playerWinOrder.stream().map(s -> s.getColor()).collect(Collectors.toList());
	boardState.setWinnerOrder(playerWinOrderId);
	return boardState;
    }
}
