package com.nexusvision.server.handler.message.menu;

import com.nexusvision.server.controller.ServerController;
import com.nexusvision.server.model.messages.menu.JoinGameAsObserver;
import com.nexusvision.server.model.messages.menu.ConnectedToGame;
import com.nexusvision.server.model.messages.menu.Error;
import com.nexusvision.server.model.messages.menu.TypeMenue;
import lombok.Data;
@Data
public class JoinGameAsObserverHandler implements MenuMessageHandler<JoinGameAsObserver> {

    @Override
    public String handle(JoinGameAsObserver message, int clientID) {

        ServerController serverController = ServerController.getInstance();

        if(message.getClientId() == null ||  message.getGameId() == null ) {
            Error error = new Error();
            error.setType(TypeMenue.error.getOrdinal());
            error.setDataId(TypeMenue.connectToServer.getOrdinal());
            error.setMessage("joining game failed, request malformed");
            return gson.toJson(error);
        }

        boolean success =  serverController.addObserver(message.getGameId(), message.getClientId());

        ConnectedToGame connectedToGame = new ConnectedToGame();
        connectedToGame.setType(TypeMenue.joinGameAsParticipant.getOrdinal());
        connectedToGame.setSuccess(success); 

        return gson.toJson(connectedToGame);
    }
}
