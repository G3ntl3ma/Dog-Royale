package com.nexusvision.server.handler.message.menu;

import com.nexusvision.server.controller.ServerController;
import com.nexusvision.server.handler.Handler;
import com.nexusvision.server.model.messages.menu.JoinGameAsObserver;
import com.nexusvision.server.model.messages.menu.ConnectedToGame;
import com.nexusvision.server.model.messages.menu.Error;
import com.nexusvision.server.model.messages.menu.TypeMenue;
import lombok.Data;
@Data
public class JoinGameAsObserverHandler extends Handler implements MenuMessageHandler<JoinGameAsObserver> {

    @Override
    public String handle(JoinGameAsObserver message, int clientID) {

        ServerController serverController = ServerController.getInstance();

        if(message.getClientId() == null ||  message.getGameId() == null ) {
            return handleError("Joining game failed, request malformed",
                    TypeMenue.connectToServer.getOrdinal());
        }

        boolean success =  serverController.addObserver(message.getGameId(), message.getClientId());

        ConnectedToGame connectedToGame = new ConnectedToGame();
        connectedToGame.setType(TypeMenue.joinGameAsParticipant.getOrdinal());
        connectedToGame.setSuccess(success);

        return gson.toJson(connectedToGame);
    }
}
