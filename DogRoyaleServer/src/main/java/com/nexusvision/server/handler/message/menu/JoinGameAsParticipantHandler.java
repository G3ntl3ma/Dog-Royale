package com.nexusvision.server.handler.message.menu;

import com.nexusvision.server.model.messages.menu.JoinGameAsParticipant;
import com.nexusvision.server.model.messages.menu.ConnectedToGame;
import com.nexusvision.server.model.messages.menu.TypeMenue;
import lombok.Data;
@Data
public class JoinGameAsParticipantHandler implements MenuMessageHandler<JoinGameAsParticipant> {
    @Override
    public String handle(JoinGameAsParticipant message, int clientID) {

        ConnectedToGame connectedToGame = new ConnectedToGame();
        connectedToGame.setType(TypeMenue.joinGameAsParticipant.getOrdinal());
        connectedToGame.setSuccess(true); //TODO replace true

        return gson.toJson(connectedToGame);

    }
}
