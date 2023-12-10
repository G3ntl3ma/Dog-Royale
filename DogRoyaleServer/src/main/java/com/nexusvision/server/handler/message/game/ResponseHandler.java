package com.nexusvision.server.handler.message.game;

import com.nexusvision.server.handler.message.MessageHandler;
import com.nexusvision.server.model.messages.game.Response;

public class ResponseHandler extends MessageHandler<Response> {

    @Override
    protected String performHandle(Response message, int clientID) {
        return null;
    }
}
