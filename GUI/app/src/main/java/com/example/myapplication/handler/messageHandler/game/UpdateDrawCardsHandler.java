package com.example.myapplication.handler.messageHandler.game;

import com.example.myapplication.handler.Handler;
import com.example.myapplication.handler.HandlingException;
import com.example.myapplication.handler.messageHandler.menu.MenuMessageHandler;
import com.example.myapplication.messages.game.TypeGame;
import com.example.myapplication.messages.game.Response;
import com.example.myapplication.messages.game.UpdateDrawCards;

public class UpdateDrawCardsHandler extends Handler implements MenuMessageHandler<UpdateDrawCards> {

    @Override
    public String handle(UpdateDrawCards message) throws HandlingException {
        //TODO update GUI
        try {
            Response response = new Response();
            response.setUpdated(true);

            return gson.toJson(response);
        } catch (Exception e) {
            throw new HandlingException("Exception while handling UpdateDrawCards",
                    e, TypeGame.response.getOrdinal());
        }


    }
}
