package com.nexusvision.server.handler.message.game;

import com.nexusvision.server.controller.GameLobby;
import com.nexusvision.server.controller.ServerController;
import com.nexusvision.server.handler.HandlingException;
import com.nexusvision.server.handler.message.MessageHandler;
import com.nexusvision.server.model.messages.game.LeavePlayer;

public class LeavePlayerHandler extends MessageHandler<LeavePlayer> {

    @Override
    protected void performHandle(LeavePlayer message, int clientID) throws HandlingException {
        ServerController serverController = ServerController.getInstance();
        GameLobby lobby = serverController.getGameOfPlayer(clientID);

        lobby.removePlayer(clientID);
    }
}
