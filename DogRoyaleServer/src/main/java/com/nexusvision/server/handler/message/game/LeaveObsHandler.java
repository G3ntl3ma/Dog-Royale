package com.nexusvision.server.handler.message.game;

import com.nexusvision.server.controller.GameLobby;
import com.nexusvision.server.controller.ServerController;
import com.nexusvision.server.handler.message.MessageHandler;
import com.nexusvision.server.model.messages.game.LeaveObs;

public class LeaveObsHandler extends MessageHandler<LeaveObs> {

    @Override
    protected void performHandle(LeaveObs message, int clientId) {
        ServerController serverController = ServerController.getInstance();
        GameLobby gameLobby = serverController.getLobbyOfPlayer(clientId);

        gameLobby.removeObserver(clientId);
    }
}
