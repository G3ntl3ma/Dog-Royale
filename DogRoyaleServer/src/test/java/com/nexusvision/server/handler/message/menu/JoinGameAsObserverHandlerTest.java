package com.nexusvision.server.handler.message.menu;

import com.google.gson.JsonSyntaxException;
import com.nexusvision.server.controller.GameLobby;
import com.nexusvision.server.controller.ServerController;
import com.nexusvision.server.handler.HandlerTest;
import com.nexusvision.server.handler.HandlingException;
import com.nexusvision.server.model.enums.Colors;
import com.nexusvision.server.model.messages.menu.ConnectedToGame;
import com.nexusvision.server.model.messages.menu.JoinGameAsObserver;
import com.nexusvision.server.model.messages.menu.ReturnGameList;
import com.nexusvision.server.model.messages.menu.TypeMenue;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * @author felixwr
 */
public class JoinGameAsObserverHandlerTest extends HandlerTest {

    @Test
    public void testHandle() {
//        JoinGameAsObserverHandler handler = new JoinGameAsObserverHandler();
//        ServerController serverController = ServerController.getInstance();
//        int clientID = serverController.createNewClient();
//
//        ArrayList<Integer> playerOrderList = new ArrayList<>();
//        HashMap<Integer, Colors> playerColorMap = new HashMap<>();
//        ArrayList<Integer> observerList = new ArrayList<>();
//
//        int lobbyID1 = serverController.createNewLobby(playerOrderList, playerColorMap, observerList);
//        GameLobby lobby = serverController.getLobbyById(lobbyID1);
//
//        JoinGameAsObserver request = new JoinGameAsObserver();
//        request.setType(TypeMenue.joinGameAsObserver.getOrdinal());
//        request.setGameId(lobbyID1);
//        request.setClientId(clientID);
//
//        String response = null;
//        try {
//            response = handler.handle(request, clientID);
//        } catch (HandlingException e) {
//            fail("Handling exception thrown during test: " + e.getMessage());
//        }
//
//        assertNotNull(response);
//
//        ConnectedToGame connectedToGame = null;
//        try {
//            connectedToGame = gson.fromJson(response, ConnectedToGame.class);
//        } catch (JsonSyntaxException e) {
//            fail("Response string has wrong format: " + e.getMessage());
//        }
//
//        assertNotNull(connectedToGame);
//        assertNotNull(connectedToGame.getType());
//        assertEquals(connectedToGame.getType(), TypeMenue.connectedToGame.getOrdinal());
//        assertTrue(connectedToGame.isSuccess());
    }
}
