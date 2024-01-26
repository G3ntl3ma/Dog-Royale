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

        ArrayList<Integer> playerOrderList = new ArrayList<>();
        HashMap<Integer, Colors> playerColorMap = new HashMap<>();
        ArrayList<Integer> observerList = new ArrayList<>();

        playerOrderList.add(3); // 3 is the clientID
        playerOrderList.add(2);
        playerOrderList.add(4);
        playerOrderList.add(1);

        playerColorMap.put(1, Colors.farbe2);
        playerColorMap.put(2, Colors.farbe1);
        playerColorMap.put(3, Colors.farbe4);
        playerColorMap.put(4, Colors.farbe3);

        playerOrderList.add(5);
        playerOrderList.add(6);

//        int lobbyID1 = serverController.createNewLobby(playerOrderList, playerColorMap, observerList); // TODO: Fix
//        int lobbyID2 = serverController.createNewLobby(playerOrderList, playerColorMap, observerList);
//        int lobbyID3 = serverController.createNewLobby(playerOrderList, playerColorMap, observerList);
//        int lobbyID4 = serverController.createNewLobby(playerOrderList, playerColorMap, observerList);

//        GameLobby lobby1 = serverController.getLobbyById(lobbyID1); // TODO: Fix
//        GameLobby lobby2 = serverController.getLobbyById(lobbyID2);
//        GameLobby lobby3 = serverController.getLobbyById(lobbyID3);
//        GameLobby lobby4 = serverController.getLobbyById(lobbyID4);

        // TODO: Game needs to be set as well!!! (null currently)

//        lobby1.setGameState(GameState.IN_PROGRESS); // TODO: This shouldn't even be possible
//        lobby2.setGameState(GameState.FINISHED);
//        lobby3.setGameState(GameState.IN_PROGRESS);
//        lobby4.setGameState(GameState.IN_PROGRESS);
    }
}
