package com.nexusvision.server.service;

import com.google.gson.Gson;
import com.nexusvision.server.model.messages.menu.ConnectedToGame;
import com.nexusvision.server.model.messages.menu.TypeMenue;

public class ConnectedToGameService {

    private static final Gson gson = new Gson();

    public String getConnectedToGame(boolean successful) {
        ConnectedToGame connectedToGame = new ConnectedToGame();
        connectedToGame.setType(TypeMenue.connectedToGame.getOrdinal());
        connectedToGame.setSuccess(successful);
        return gson.toJson(connectedToGame);
    }
}
