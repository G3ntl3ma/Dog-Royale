package com.example.myapplication.handler.messageHandler.game;

import com.example.myapplication.handler.Handler;
import com.example.myapplication.handler.HandlingException;
import com.example.myapplication.messages.game.Response;
import com.example.myapplication.messages.game.TypeGame;
import com.example.myapplication.messages.game.BoardState;

public class FirstBoardStateHandler extends Handler implements GameMessageHandler<BoardState>{
    @Override
    public String handle(BoardState message) throws HandlingException {
        // TODO create Game Board
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
