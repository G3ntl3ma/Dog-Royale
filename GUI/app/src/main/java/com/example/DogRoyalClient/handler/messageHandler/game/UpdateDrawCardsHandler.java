package com.example.DogRoyalClient.handler.messageHandler.game;

import com.example.DogRoyalClient.handler.Handler;
import com.example.DogRoyalClient.handler.HandlingException;
import com.example.DogRoyalClient.handler.messageHandler.menu.MenuMessageHandler;
import com.example.DogRoyalClient.messages.game.TypeGame;
import com.example.DogRoyalClient.messages.game.Response;
import com.example.DogRoyalClient.messages.game.UpdateDrawCards;

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
