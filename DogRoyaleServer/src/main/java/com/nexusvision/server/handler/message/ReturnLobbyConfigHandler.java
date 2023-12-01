package com.nexusvision.server.handler.message;

import com.nexusvision.messages.menu.ReturnLobbyConfig;

public class ReturnLobbyConfigHandler implements MessageHandler<ReturnLobbyConfig> {
    @Override
    public String handle(ReturnLobbyConfig message, int clientID) {

	ReturnLobbyConfig.PlayerOrder playerOrder = message.getPlayerOrder();

    return "sea";
    }
}
