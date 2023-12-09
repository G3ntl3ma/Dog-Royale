package com.nexusvision.server.handler.message.menu;

import com.nexusvision.server.controller.GameLobby;
import com.nexusvision.server.controller.ServerController;
import com.nexusvision.server.handler.Handler;
import com.nexusvision.server.handler.HandlingException;
import com.nexusvision.server.handler.message.MessageHandler;
import com.nexusvision.server.model.enums.Colors;
import com.nexusvision.server.model.messages.menu.Error;
import com.nexusvision.server.model.messages.menu.JoinGameAsParticipant;
import com.nexusvision.server.model.messages.menu.ConnectedToGame;
import com.nexusvision.server.model.messages.menu.TypeMenue;
import lombok.Data;

public class JoinGameAsParticipantHandler extends MessageHandler<JoinGameAsParticipant> {

    @Override
    protected String performHandle(JoinGameAsParticipant message, int clientID) throws HandlingException {

        verifyClientID(clientID, message.getClientId());
        ServerController serverController = ServerController.getInstance();

        GameLobby lobby = serverController.getLobbyById(message.getGameId());
        lobby.addPlayer(message.getClientId(), Colors.farbe1); // TODO: What if player can't join because lobby is full, should maybe receive success = false

        ConnectedToGame connectedToGame = new ConnectedToGame();
        connectedToGame.setType(TypeMenue.connectedToGame.getOrdinal());
        connectedToGame.setSuccess(true);

        return gson.toJson(connectedToGame);
    }
}
