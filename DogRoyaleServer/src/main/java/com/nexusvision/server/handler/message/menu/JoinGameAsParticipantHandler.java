package com.nexusvision.server.handler.message.menu;

import com.nexusvision.server.controller.ServerController;
import com.nexusvision.server.handler.Handler;
import com.nexusvision.server.model.enums.Colors;
import com.nexusvision.server.model.messages.menu.Error;
import com.nexusvision.server.model.messages.menu.JoinGameAsParticipant;
import com.nexusvision.server.model.messages.menu.ConnectedToGame;
import com.nexusvision.server.model.messages.menu.TypeMenue;
import lombok.Data;
@Data
public class JoinGameAsParticipantHandler extends Handler implements MenuMessageHandler<JoinGameAsParticipant> {

    @Override
    public String handle(JoinGameAsParticipant message, int clientID) {

        ServerController serverController = ServerController.getInstance();

        if(message.getClientId() == null ||  message.getGameId() == null ) {
            return handleError("Joining game failed, request malformed",
                    TypeMenue.connectToServer.getOrdinal());
        }

        serverController.getLobbyById(message.getGameId()).addPlayer(message.getClientId(), Colors.farbe1); // TODO: felix check!!!

        //TODO handle playerName
        ConnectedToGame connectedToGame = new ConnectedToGame();
        connectedToGame.setType(TypeMenue.joinGameAsParticipant.getOrdinal());
        connectedToGame.setSuccess(true); // TODO

        return gson.toJson(connectedToGame);
    }
}
