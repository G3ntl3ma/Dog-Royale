package com.example.myapplication.handler.messageHandler.game;

import com.example.myapplication.handler.Handler;
import com.example.myapplication.handler.HandlingException;
import com.example.myapplication.messages.sync.JoinObs;

public class JoinObsHandler extends Handler implements GameMessageHandler<JoinObs> {

    @Override
    public String handle(JoinObs message) throws HandlingException {
        return null;
    }
}
