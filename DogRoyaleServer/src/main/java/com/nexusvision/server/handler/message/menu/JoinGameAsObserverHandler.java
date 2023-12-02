package com.nexusvision.server.handler.message.menu;

import com.nexusvision.server.model.messages.menu.JoinGameAsObserver;
import com.nexusvision.server.model.messages.menu.ConnectedToGame;
import com.nexusvision.server.model.messages.menu.Error;
import com.nexusvision.server.model.messages.menu.TypeMenue;
import lombok.Data;
@Data
public class JoinGameAsObserverHandler implements MenuMessageHandler<JoinGameAsObserver> {

    @Override
    public String handle(JoinGameAsObserver message, int clientID) {

        if (false) {
            Error error = new Error();
            error.setType(TypeMenue.error.getOrdinal());
            error.setDataId(TypeMenue.joinGameAsObserver.getOrdinal());
            error.setMessage("Failed to Join the game");

            return gson.toJson(error);
        }

        ConnectedToGame connectedToGame = new ConnectedToGame();
        connectedToGame.setType(TypeMenue.joinGameAsObserver.getOrdinal());
        connectedToGame.setSuccess(true); //TODO replace true

        return gson.toJson(connectedToGame);
    }
}
