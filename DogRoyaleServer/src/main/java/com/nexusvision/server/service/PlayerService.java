package com.nexusvision.server.service;

import com.google.gson.Gson;
import com.nexusvision.server.common.ChannelType;
import com.nexusvision.server.controller.GameLobby;
import com.nexusvision.server.controller.MessageBroker;
import com.nexusvision.server.controller.ServerController;
import com.nexusvision.server.model.entities.Client;
import com.nexusvision.server.model.gamelogic.Game;
import com.nexusvision.server.model.gamelogic.Player;
import com.nexusvision.server.model.messages.menu.ConnectedToGame;
import com.nexusvision.server.model.messages.menu.ReturnLobbyConfig;
import com.nexusvision.server.model.messages.menu.TypeMenue;

public class PlayerService {

    private static final ServerController serverController = ServerController.getInstance();
    private static final MessageBroker messageBroker = MessageBroker.getInstance();
    private static final Gson gson = new Gson();

    /**
     * Moves the player with <code>clientId</code> into the provided lobby
     *
     * @param clientId The <code>clientId</code> of the player to move
     * @param lobby The destination lobby
     * @return <code>true</code> if successful else <code>false</code>
     */
    public boolean movePlayerToLobby(int clientId, GameLobby lobby) {
        Client client = serverController.getClientById(clientId);
        boolean successful = lobby.addPlayer(client.getName(), clientId);
        if (!successful) return false;
        serverController.moveIntoGame(clientId);
        ReturnLobbyConfig returnLobbyConfig = lobby.getLobbyConfig().getReturnLobbyConfig();
        messageBroker.sendMessage(ChannelType.SINGLE, clientId, gson.toJson(returnLobbyConfig));
        return true;
    }
}
