package com.nexusvision.server.handler.message.game;

import com.nexusvision.server.handler.Handler;
import com.nexusvision.server.model.messages.game.Move;

public class MoveHandler extends Handler implements GameMessageHandler<Move> {
    @Override
    public String handle(Move message) {
        return null;
    }
}
