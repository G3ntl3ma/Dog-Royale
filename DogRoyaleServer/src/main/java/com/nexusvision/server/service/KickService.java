package com.nexusvision.server.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nexusvision.server.common.ChannelType;
import com.nexusvision.server.controller.MessageBroker;
import com.nexusvision.server.model.gamelogic.LobbyConfig;
import com.nexusvision.server.model.messages.game.Kick;
import com.nexusvision.server.model.messages.game.TypeGame;
import com.nexusvision.utils.NewLineAppendingSerializer;

public class KickService {

    protected static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(Object.class, new NewLineAppendingSerializer<>())
            .create();

    MessageBroker messageBroker;

    public KickService() {
        messageBroker = MessageBroker.getInstance();
    }

    public void kick(LobbyConfig lobbyConfig, int clientId, int lobbyId) {
        lobbyConfig.removeColor(clientId);
        messageBroker.unregisterSubscriber(clientId, lobbyId);

        Kick kick = new Kick();
        kick.setType(TypeGame.kick.getOrdinal());
        kick.setClientId(clientId);
        kick.setReason("TournamentPlayer got kicked");

        messageBroker.sendMessage(ChannelType.LOBBY, lobbyId, gson.toJson(kick));
    }
}
