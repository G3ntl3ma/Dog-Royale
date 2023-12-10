package com.example.myapplication.handler.messageHandler.game;


import com.example.myapplication.BoardUpdater;
import com.example.myapplication.handler.Handler;
import com.example.myapplication.handler.HandlingException;
import com.example.myapplication.messages.game.TypeGame;
import com.example.myapplication.messages.game.BoardState;

public class BoardStateHandler extends Handler implements GameMessageHandler<BoardState> {


    @Override
    public String handle(BoardState message) throws HandlingException {

        BoardUpdater boardUpdater = new BoardUpdater();// TODO make sure it works like this
        boardUpdater.UpdateBoard(message);

        try {
            com.example.myapplication.messages.game.Response response = new com.example.myapplication.messages.game.Response();
            response.setUpdated(true);

            return gson.toJson(response);
        } catch (Exception e) {
            throw new HandlingException("Exception while handling BoardState",
                    e, TypeGame.response.getOrdinal());
        }
    }
}
