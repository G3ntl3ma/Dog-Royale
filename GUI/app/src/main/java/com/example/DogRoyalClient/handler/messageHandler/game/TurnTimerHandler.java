package com.example.DogRoyalClient.handler.messageHandler.game;

import com.example.DogRoyalClient.handler.Handler;
import com.example.DogRoyalClient.handler.HandlingException;
import com.example.DogRoyalClient.messages.sync.TurnTimer;

public class TurnTimerHandler extends Handler implements GameMessageHandler<TurnTimer> {
    @Override
    public String handle(TurnTimer message) throws HandlingException {
        return null;
    }
}
