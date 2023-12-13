package com.example.DogRoyalClient.handler.messageHandler.game;


import com.example.DogRoyalClient.BoardUpdater;
import com.example.DogRoyalClient.handler.Handler;
import com.example.DogRoyalClient.handler.HandlingException;
import com.example.DogRoyalClient.messages.game.TypeGame;
import com.example.DogRoyalClient.messages.game.BoardState;

public class BoardStateHandler extends Handler implements GameMessageHandler<BoardState> {


    @Override
    public String handle(BoardState message) throws HandlingException {

        BoardUpdater boardUpdater = new BoardUpdater();// TODO make sure it works like this
        boardUpdater.UpdateBoard(message);

        try {
            com.example.DogRoyalClient.messages.game.Response response = new com.example.DogRoyalClient.messages.game.Response();
            response.setUpdated(true);

            return gson.toJson(response);
        } catch (Exception e) {
            throw new HandlingException("Exception while handling BoardState",
                    e, TypeGame.response.getOrdinal());
        }
    }
}
