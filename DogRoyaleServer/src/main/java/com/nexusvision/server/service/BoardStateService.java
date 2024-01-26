package com.nexusvision.server.service;

import com.nexusvision.server.model.enums.Card;
import com.nexusvision.server.model.gamelogic.Figure;
import com.nexusvision.server.model.gamelogic.Game;
import com.nexusvision.server.model.gamelogic.Player;
import com.nexusvision.server.model.messages.game.BoardState;
import com.nexusvision.server.model.messages.game.TypeGame;
import com.nexusvision.server.model.utils.PlayerOrder;
import com.nexusvision.server.model.utils.PlayerElement;

import java.util.ArrayList;
import java.util.List;

public class BoardStateService {

    public BoardState generateBoardState(Game game, PlayerOrder playerOrder) { // TODO: What about random order?
        BoardState boardState = new BoardState();
        boardState.setType(TypeGame.boardState.getOrdinal());

        //pieces position
        List<BoardState.Piece> piecesList = new ArrayList<>();
        List<PlayerElement> order = playerOrder.getOrder();
        for (int playerOrderIndex = 0; playerOrderIndex < order.size(); playerOrderIndex++) {
            int clientId = order.get(playerOrderIndex).getClientId();
            Player player = game.getPlayerList().get(playerOrderIndex);

            for (int pieceId = 0; pieceId < player.getFigureList().size(); pieceId++) {
                BoardState.Piece piece = new BoardState.Piece();

                Figure figure = player.getFigureList().get(pieceId);
                int position = figure.getField() == null ? -1 : figure.getField().getFieldId();
                boolean isOnBench = figure.isOnBench();
                int inHousePosition = game.getHousePosition(playerOrderIndex, pieceId);
                piece.setPieceId(pieceId);
                piece.setClientId(clientId);
                piece.setPosition(position);
                piece.setOnBench(isOnBench);
                piece.setInHousePosition(inHousePosition);
                piecesList.add(piece);
            }
        }
        boardState.setPieces(piecesList);

        List<BoardState.DiscardItem> cardList = new ArrayList<>();
        for (int i = 0; i < game.getPile().size(); i++) {
            Card card = game.getPile().get(i);
            BoardState.DiscardItem discardItem = new BoardState.DiscardItem();
            discardItem.setClientId(42); //fake clientId because it doesn't matter
            discardItem.setCard(card.getOrdinal());
            cardList.add(discardItem);
        }
        boardState.setDiscardPile(cardList);

        //get last played card
        boardState.setLastPlayedCard(game.getLastCardOnPile() == null ? -1 : game.getLastCardOnPile().getOrdinal());
        boardState.setRound(game.getRound());
        boardState.setMoveCount(game.getMovesMade());
        boardState.setNextPlayer(game.getPlayerToMoveId());
        boardState.setGameOver(game.checkGameOver());
        boardState.setWinnerOrder(game.getWinnerOrder());
        return boardState;
    }
}
