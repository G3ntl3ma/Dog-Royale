package com.example.DogRoyalClient.handler.messageHandler.game;

import com.example.DogRoyalClient.handler.Handler;
import com.example.DogRoyalClient.handler.HandlingException;
import com.example.DogRoyalClient.messages.game.Response;
import com.example.DogRoyalClient.messages.game.TypeGame;
import com.example.DogRoyalClient.messages.game.BoardState;

public class FirstBoardStateHandler extends Handler implements GameMessageHandler<BoardState>{
    @Override
    public String handle(BoardState message) throws HandlingException {

        try {
            Response response = new Response();
            response.setUpdated(true);

            return gson.toJson(response);
        } catch (Exception e) {
            throw new HandlingException("Exception while handling FirstBoardState",
                    e, TypeGame.response.getOrdinal());
        }

    }
}
