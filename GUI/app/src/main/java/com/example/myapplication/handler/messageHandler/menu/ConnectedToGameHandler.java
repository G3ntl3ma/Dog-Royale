package com.example.myapplication.handler.messageHandler.menu;

import com.example.myapplication.handler.Handler;
import com.example.myapplication.handler.HandlingException;
import com.example.myapplication.messages.menu.ConnectedToGame;

public class ConnectedToGameHandler extends Handler implements MenuMessageHandler<ConnectedToGame> {
    public String handle(ConnectedToGame message) throws HandlingException {
        return null;
        }

}
