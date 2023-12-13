package com.example.DogRoyalClient.handler.messageHandler.game;

import com.example.DogRoyalClient.handler.Handler;
import com.example.DogRoyalClient.handler.HandlingException;
import com.example.DogRoyalClient.handler.messageHandler.menu.MenuMessageHandler;
import com.example.DogRoyalClient.messages.game.Cancel;

public class CancelHandler extends Handler implements MenuMessageHandler<Cancel> {

    @Override
    public String handle(Cancel message) throws HandlingException {
        //TODO show winners message.winnerOrder
        //TODO return to GameList
        return null;
    }
}
