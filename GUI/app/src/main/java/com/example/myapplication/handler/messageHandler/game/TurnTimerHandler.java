package com.example.myapplication.handler.messageHandler.game;

import com.example.myapplication.handler.Handler;
import com.example.myapplication.handler.HandlingException;
import com.example.myapplication.messages.sync.TurnTimer;

public class TurnTimerHandler extends Handler implements GameMessageHandler<TurnTimer> {
    @Override
    public String handle(TurnTimer message) throws HandlingException {
        return null;
    }
}
