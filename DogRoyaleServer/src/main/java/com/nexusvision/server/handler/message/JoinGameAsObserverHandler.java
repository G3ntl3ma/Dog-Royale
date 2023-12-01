package com.nexusvision.server.handler.message;

import com.nexusvision.messages.menu.JoinGameAsObserver;
import com.nexusvision.messages.menu.ConnectedToGame;
import com.nexusvision.messages.menu.Error;
import com.nexusvision.messages.menu.TypeMenue;
import com.nexusvision.server.controller.ServerController;
import lombok.Data;
@Data
public class JoinGameAsObserverHandler implements MessageHandler<JoinGameAsObserver> {


    @Override
    public String handle(JoinGameAsObserver message, int clientID) {

        if (false) {
            Error error = new Error();
            error.setType(TypeMenue.error);
            error.setDataId(TypeMenue.joinGameAsObserver.ordinal() + 100);
            error.setMessage("Failed to Join the game");
            return gson.toJson(error);
        }

        ConnectedToGame connectedToGame = new ConnectedToGame();
        connectedToGame.setType(TypeMenue.joinGameAsObserver);
        connectedToGame.setSuccess();
        return gson.toJson(connectedToGame);
    }
}
