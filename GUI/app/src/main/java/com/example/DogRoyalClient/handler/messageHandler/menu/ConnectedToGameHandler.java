package com.example.DogRoyalClient.handler.messageHandler.menu;

import com.example.DogRoyalClient.handler.Handler;
import com.example.DogRoyalClient.handler.HandlingException;
import com.example.DogRoyalClient.messages.menu.ConnectedToGame;

public class ConnectedToGameHandler extends Handler implements MenuMessageHandler<ConnectedToGame> {
    public String handle(ConnectedToGame message) throws HandlingException {
        return null;
        }

}
