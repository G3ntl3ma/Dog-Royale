package com.nexusvision.server.testutils;

import com.nexusvision.server.common.MessageListener;
import com.nexusvision.server.controller.GameLobby;
import com.nexusvision.server.controller.MessageBroker;
import com.nexusvision.server.controller.ServerController;
import com.nexusvision.server.handler.ClientHandler;
import com.nexusvision.server.model.enums.Colors;
import com.nexusvision.server.model.enums.GameState;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class LobbyExtension implements BeforeAllCallback, AfterAllCallback {

    private static ServerController serverController;
    private static MessageBroker messageBroker;
    private final static int CLIENT_ID_LOBBY_1 = -101;
    private final static int CLIENT_ID_LOBBY_2 = -102;
    private final static int CLIENT_ID_LOBBY_3 = -103;
    private final static int CLIENT_ID_LOBBY_4 = -104;
    private final static int CLIENT_ID_LOBBY_5 = -105;
    private final static String JSON_FILE_PATH = "src/test/java/com/nexusvision/server/testutils/";

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        serverController = ServerController.getInstance();
        messageBroker = MessageBroker.getInstance();

        int lobbyID1 = serverController.createNewLobby();
        int lobbyID2 = serverController.createNewLobby();
        int lobbyID3 = serverController.createNewLobby();
        int lobbyID4 = serverController.createNewLobby();
        int lobbyID5 = serverController.createNewLobby();

        GameLobby lobby1 = serverController.getLobbyById(lobbyID1); // UPCOMING
        GameLobby lobby2 = serverController.getLobbyById(lobbyID2); // UPCOMING
        GameLobby lobby3 = serverController.getLobbyById(lobbyID3); // RUNNING
        GameLobby lobby4 = serverController.getLobbyById(lobbyID4); // RUNNING
        GameLobby lobby5 = serverController.getLobbyById(lobbyID5); // FINISHED

        messageBroker.addIdentifier(CLIENT_ID_LOBBY_1, new MessageListener());
        lobby1.addPlayer("clientId:" + CLIENT_ID_LOBBY_1, CLIENT_ID_LOBBY_1); // artificial player to find lobby

        lobby2.setConfiguration(JSON_FILE_PATH + "lobby2config.json");
        messageBroker.addIdentifier(CLIENT_ID_LOBBY_2, new MessageListener());
        lobby2.addPlayer("clientId:" + CLIENT_ID_LOBBY_2, CLIENT_ID_LOBBY_2);

        messageBroker.addIdentifier(CLIENT_ID_LOBBY_3, new MessageListener());
        lobby3.addPlayer("clientId:" + CLIENT_ID_LOBBY_3, CLIENT_ID_LOBBY_3);
        runLobby(lobby3, "lobby3config.json", 3);

        messageBroker.addIdentifier(CLIENT_ID_LOBBY_4, new MessageListener());
        lobby4.addPlayer("clientId:" + CLIENT_ID_LOBBY_4, CLIENT_ID_LOBBY_4);
        runLobby(lobby4, "lobby4config.json", 5);

        messageBroker.addIdentifier(CLIENT_ID_LOBBY_5, new MessageListener());
        lobby5.addPlayer("clientId:" + CLIENT_ID_LOBBY_5, CLIENT_ID_LOBBY_5);
        runLobby(lobby5, "lobby5config.json", 2);
        lobby5.finishGame();
    }

    private void runLobby(GameLobby lobby, String fileName, int playerCount) {
        lobby.setConfiguration(JSON_FILE_PATH + fileName);
        for (int i = 0; i < playerCount; i++) {
            int clientId = serverController.createNewClient();
            messageBroker.addIdentifier(clientId, new MessageListener());
            lobby.addPlayer("clientId:" + clientId, clientId);
        }
        lobby.runGame();
    }

    @Override
    public void afterAll(ExtensionContext extensionContext) throws Exception {
        serverController = null;
        messageBroker = null;
    }
}
