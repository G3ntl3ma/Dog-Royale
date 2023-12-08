package com.nexusvision.server.handler.message.menu;

import com.nexusvision.server.controller.GameLobby;
import com.nexusvision.server.controller.ServerController;
import com.nexusvision.server.handler.HandlingException;
import com.nexusvision.server.handler.message.MessageHandler;
import com.nexusvision.server.model.messages.menu.ConnectedToGame;
import com.nexusvision.server.model.messages.menu.JoinGameAsObserver;
import com.nexusvision.server.model.messages.menu.TypeMenue;

public class JoinGameAsObserverHandler extends MessageHandler<JoinGameAsObserver> {

    @Override
    protected String performHandle(JoinGameAsObserver message, int clientID) throws HandlingException {

        verifyClientID(clientID, message.getClientId());
        ServerController serverController = ServerController.getInstance();

        GameLobby lobby = serverController.getLobbyById(message.getGameId());
        lobby.addObserver(message.getClientId());

        ConnectedToGame connectedToGame = new ConnectedToGame();
        connectedToGame.setType(TypeMenue.joinGameAsObserver.getOrdinal());
        connectedToGame.setSuccess(true);

        return gson.toJson(connectedToGame);
    }
}
