package com.nexusvision.server.testutils;

import com.nexusvision.server.common.MessageListener;
import com.nexusvision.server.controller.GameLobby;
import com.nexusvision.server.controller.MessageBroker;
import com.nexusvision.server.controller.ServerController;
import com.nexusvision.server.handler.ClientHandler;
import com.nexusvision.server.model.enums.Colors;
import com.nexusvision.server.model.enums.GameState;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class LobbyExtension implements BeforeAllCallback {

    MessageBroker messageBroker = MessageBroker.getInstance();

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        ServerController serverController = ServerController.getInstance();

        int lobbyID1 = serverController.createNewLobby();
        int lobbyID2 = serverController.createNewLobby();
        int lobbyID3 = serverController.createNewLobby();
        int lobbyID4 = serverController.createNewLobby();

        GameLobby lobby1 = serverController.getLobbyById(lobbyID1); // UPCOMING
        GameLobby lobby2 = serverController.getLobbyById(lobbyID2); // RUNNING
        GameLobby lobby3 = serverController.getLobbyById(lobbyID3); // RUNNING
        GameLobby lobby4 = serverController.getLobbyById(lobbyID4);

        runLobby(lobby2, "lobby2config.json", 1, 4);
        lobby2.runGame();

        runLobby(lobby3, "lobby3config.json", 5, 10);
        lobby3.runGame();

        runLobby(lobby4, "lobby4config.json", 11, 13);
        lobby4.finishGame();
    }

    private void runLobby(GameLobby lobby, String fileName, int startNumber, int endNumber) {
        lobby.setConfiguration("src/test/java/com/nexusvision/server/testutils/" + fileName);
        for (int clientId = startNumber; clientId <= endNumber; clientId++) {
            messageBroker.addIdentifier(clientId, new MessageListener());
            lobby.addPlayer("clientId:" + String.valueOf(clientId), clientId);
        }
        lobby.runGame();
    }
}
