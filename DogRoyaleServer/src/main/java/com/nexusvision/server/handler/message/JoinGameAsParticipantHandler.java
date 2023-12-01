package com.nexusvision.server.handler.message;

import com.nexusvision.messages.menu.JoinGameAsParticipant;
import com.nexusvision.messages.menu.ConnectedToGame;
import com.nexusvision.messages.menu.Error;
import com.nexusvision.messages.menu.TypeMenue;
import com.nexusvision.server.controller.ServerController;
import lombok.Data;
@Data
public class JoinGameAsParticipantHandler implements MessageHandler<JoinGameAsParticipant>{
    @Override
    public String handle(JoinGameAsParticipant message, int clientID) {

        ConnectedToGame connectedToGame = new ConnectedToGame();
        connectedToGame.setType(TypeMenue.joinGameAsParticipant);
        connectedToGame.setSuccess();
        return gson.toJson(connectedToGame);

    }
}
