package com.example.myapplication.handler.messageHandler.game;

import com.example.myapplication.handler.Handler;
import com.example.myapplication.handler.HandlingException;
import com.example.myapplication.handler.messageHandler.menu.MenuMessageHandler;
import com.example.myapplication.messages.game.Response;
import com.example.myapplication.messages.game.TypeGame;
import com.example.myapplication.messages.game.MoveValid;

public class MoveValidHandler extends Handler implements MenuMessageHandler<MoveValid> {

    @Override
    public String handle(MoveValid message) throws HandlingException {
        //TODO just show message invalid move if it isn't
        try {//send Updated
            Response response = new Response();
            response.setUpdated(true);

            return gson.toJson(response);
        } catch (Exception e) {
            throw new HandlingException("Exception while handling ValidMove",
                    e, TypeGame.response.getOrdinal());
        }
    }
}
