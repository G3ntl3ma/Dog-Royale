package com.example.myapplication.handler.messageHandler.game;

import com.example.myapplication.handler.Handler;
import com.example.myapplication.handler.HandlingException;
import com.example.myapplication.handler.messageHandler.menu.MenuMessageHandler;
import com.example.myapplication.messages.game.Cancel;

public class CancelHandler extends Handler implements MenuMessageHandler<Cancel> {

    @Override
    public String handle(Cancel message) throws HandlingException {
        //TODO show winners message.winnerOrder
        //TODO return to GameList
        return null;
    }
}
