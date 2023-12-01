package com.nexusvision.server.handler.message.menuhandler;

import com.nexusvision.messages.menu.JoinGameAsParticipant;
import com.nexusvision.messages.menu.ConnectedToGame;
import com.nexusvision.messages.menu.TypeMenue;
import lombok.Data;
@Data
public class JoinGameAsParticipantHandler implements MenuMessageHandler<JoinGameAsParticipant> {
    @Override
    public String handle(JoinGameAsParticipant message, int clientID) {

        ConnectedToGame connectedToGame = new ConnectedToGame();
        connectedToGame.setType(TypeMenue.joinGameAsParticipant);
        connectedToGame.setSuccess(true); //TODO replace true

        return gson.toJson(connectedToGame);
    }
}
