package com.nexusvision.server.handler.message.game;

import com.nexusvision.server.controller.GameLobby;
import com.nexusvision.server.controller.ServerController;
import com.nexusvision.server.handler.message.MessageHandler;
import com.nexusvision.server.model.messages.game.JoinObs;
import com.nexusvision.server.model.messages.game.LeaveObs;
import com.nexusvision.server.model.messages.game.TypeGame;

public class LeaveObsHandler extends MessageHandler<LeaveObs> {

    @Override
    protected String performHandle(LeaveObs message, int clientId) {
        ServerController serverController = ServerController.getInstance();
        GameLobby gameLobby = serverController.getGameOfPlayer(clientId);

        gameLobby.removeObserver(clientId);

        return null;
    }
}
