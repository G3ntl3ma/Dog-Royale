package com.nexusvision.server.handler.message;

import com.nexusvision.messages.menu.ReturnLobbyConfig;
import com.nexusvision.server.handler.message.menuhandler.MenuMessageHandler;

public class ReturnLobbyConfigHandler implements MenuMessageHandler<ReturnLobbyConfig> {
    @Override
    public String handle(ReturnLobbyConfig message, int clientID) {

	ReturnLobbyConfig.PlayerOrder playerOrder = message.getPlayerOrder();

    return "sea";
    }
}
