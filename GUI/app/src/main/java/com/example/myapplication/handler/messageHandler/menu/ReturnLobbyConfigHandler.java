package com.example.myapplication.handler.messageHandler.menu;

import com.example.myapplication.handler.Handler;
import com.example.myapplication.handler.HandlingException;
import com.example.myapplication.messages.menu.ReturnLobbyConfig;

public class ReturnLobbyConfigHandler extends Handler implements MenuMessageHandler<ReturnLobbyConfig>{
    @Override
    public String handle(ReturnLobbyConfig message) throws HandlingException {
        return null;//TODO
    }
}
