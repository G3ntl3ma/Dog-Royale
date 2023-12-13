package com.example.DogRoyalClient.handler.messageHandler.game;

import com.example.DogRoyalClient.handler.Handler;
import com.example.DogRoyalClient.handler.HandlingException;
import com.example.DogRoyalClient.messages.sync.LiveTimer;


public class LiveTimerHandler extends Handler implements GameMessageHandler<LiveTimer> {
    @Override
    public String handle(LiveTimer message) throws HandlingException {
        //TODO update GUI accordingly using message.getLiveTimer()
        return null;
    }
}
