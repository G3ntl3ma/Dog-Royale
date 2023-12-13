package com.example.DogRoyalClient.handler.messageHandler.game;

import com.example.DogRoyalClient.handler.Handler;
import com.example.DogRoyalClient.handler.HandlingException;
import com.example.DogRoyalClient.messages.sync.JoinObs;

public class JoinObsHandler extends Handler implements GameMessageHandler<JoinObs> {

    @Override
    public String handle(JoinObs message) throws HandlingException {
        return null;
    }
}
