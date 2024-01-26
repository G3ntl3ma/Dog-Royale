package com.nexusvision.server.testutils;

import com.nexusvision.server.controller.GameLobby;
import com.nexusvision.server.controller.ServerController;
import com.nexusvision.server.model.enums.Colors;
import com.nexusvision.server.model.enums.GameState;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.ArrayList;
import java.util.HashMap;

public class LobbyExtension implements BeforeAllCallback {

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

        lobby2.setConfiguration("src/test/java/com/nexusvision/server/testutils/lobby2config.json");
        lobby2.addPlayer("clientId:1", 1);
        lobby2.addPlayer("clientId:2", 2);
        lobby2.addPlayer("clientId:3", 3);
        lobby2.addPlayer("clientId:4", 4);
        lobby2.runGame();

        lobby3.setConfiguration("src/test/java/com/nexusvision/server/testutils/lobby3config.json");
        lobby3.addPlayer("clientId:5", 5);
        lobby3.addPlayer("clientId:6", 6);
        lobby3.addPlayer("clientId:7", 7);
        lobby3.addPlayer("clientId:8", 8);
        lobby3.addPlayer("clientId:9", 9);
        lobby3.addPlayer("clientId:10", 10);
        lobby3.runGame();

        lobby4.setConfiguration("src/test/java/com/nexusvision/server/testutils/lobby4config.json");
        lobby4.addPlayer("clientId:11", 11);
        lobby4.addPlayer("clientId:12", 12);
        lobby4.addPlayer("clientId:13", 13);
        lobby4.runGame();
        lobby4.finishGame();
    }
}
