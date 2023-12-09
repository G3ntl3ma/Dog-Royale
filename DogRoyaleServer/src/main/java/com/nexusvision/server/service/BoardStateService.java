package com.nexusvision.server.service;

import com.nexusvision.server.controller.GameLobby;
import com.nexusvision.server.model.enums.CardType;
import com.nexusvision.server.model.gamelogic.Figure;
import com.nexusvision.server.model.gamelogic.Game;
import com.nexusvision.server.model.gamelogic.Player;
import com.nexusvision.server.model.messages.game.BoardState;
import com.nexusvision.server.model.messages.game.TypeGame;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BoardStateService {

    private BoardState getBoardState(GameLobby gameLobby) {
        Game game = gameLobby.getGame();
        BoardState boardState = new BoardState();
        boardState.setType(TypeGame.boardState.getOrdinal());

        ArrayList<Integer> playerOrderList = gameLobby.getPlayerOrderList();

        //pieces position
        ArrayList<BoardState.Piece> _pieces = new ArrayList<>();
        for (int playerId = 0; playerId < playerOrderList.size(); playerId++) {
            int clientId = playerOrderList.get(playerId);
            Player player = game.getPlayers().get(playerId);

            for (int pieceId = 0; pieceId < player.getFigures().size(); pieceId++) {
                BoardState.Piece piece = new BoardState.Piece();

                Figure figure = player.getFigures().get(pieceId);
                int position = figure.getField().getVal();
                boolean isOnBench = figure.isOnBench();
                Integer inHousePosition = game.getHousePosition(playerId, pieceId);
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
            _card.setClientId(42); //fake clientId because it doesnt matter
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
        List<Integer> playerWinOrderId = playerWinOrder.stream().map(Player::getPlayerId).collect(Collectors.toList());
        boardState.setWinnerOrder(playerWinOrderId);
        return boardState;
    }
}
