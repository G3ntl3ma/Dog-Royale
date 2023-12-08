package com.nexusvision.server.handler.message.menu;

import com.google.gson.JsonSyntaxException;
import com.nexusvision.server.controller.GameLobby;
import com.nexusvision.server.controller.ServerController;
import com.nexusvision.server.handler.HandlerTest;
import com.nexusvision.server.handler.HandlingException;
import com.nexusvision.server.handler.message.menu.RequestGameListHandler;
import com.nexusvision.server.handler.HandlingException;
import com.nexusvision.server.controller.GameLobby;
import com.nexusvision.server.model.enums.Colors;
import com.nexusvision.server.model.enums.GameState;
import com.nexusvision.server.model.messages.menu.RequestGameList;
import com.nexusvision.server.model.messages.menu.ReturnGameList;
import com.nexusvision.server.model.messages.menu.TypeMenue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * @author felixwr
 */
public class RequestGameListHandlerTest extends HandlerTest {

    @Test
    public void testHandle() {
        RequestGameListHandler handler = new RequestGameListHandler();
        ServerController serverController = ServerController.getInstance();
        int clientID = serverController.createNewClient();

        RequestGameList request = new RequestGameList();
        request.setType(TypeMenue.requestGameList.getOrdinal());
        request.setClientID(clientID);
        request.setGameCountStarting(1);
        request.setGameCountInProgress(2);
        request.setGameCountFinished(1);

        ArrayList<Integer> playerOrderList = new ArrayList<>();
        HashMap<Integer, Colors> playerColorMap = new HashMap<>();
        ArrayList<Integer> observerList = new ArrayList<>();

        playerOrderList.add(3);
        playerOrderList.add(2);
        playerOrderList.add(4);
        playerOrderList.add(1);

        playerColorMap.put(1, Colors.farbe2);
        playerColorMap.put(2, Colors.farbe1);
        playerColorMap.put(3, Colors.farbe4);
        playerColorMap.put(4, Colors.farbe3);

        playerOrderList.add(5);
        playerOrderList.add(6);

        int lobbyID1 = serverController.createNewLobby(playerOrderList, playerColorMap, observerList);
        int lobbyID2 = serverController.createNewLobby(playerOrderList, playerColorMap, observerList);
        int lobbyID3 = serverController.createNewLobby(playerOrderList, playerColorMap, observerList);
        int lobbyID4 = serverController.createNewLobby(playerOrderList, playerColorMap, observerList);

        GameLobby lobby1 = serverController.getLobbyById(lobbyID1);
        GameLobby lobby2 = serverController.getLobbyById(lobbyID2);
        GameLobby lobby3 = serverController.getLobbyById(lobbyID3);
        GameLobby lobby4 = serverController.getLobbyById(lobbyID4);

        lobby1.setGameState(GameState.IN_PROGRESS);
        lobby2.setGameState(GameState.FINISHED);
        lobby3.setGameState(GameState.IN_PROGRESS);
        lobby4.setGameState(GameState.IN_PROGRESS);

        try {
            String response = handler.handle(request, clientID);

            try  {
                ReturnGameList returnGameList = gson.fromJson(response, ReturnGameList.class);
                assertNotNull(returnGameList.getType());
                assertNotNull(returnGameList.getStartingGames());
                assertNotNull(returnGameList.getRunningGames());
                assertNotNull(returnGameList.getFinishedGames());
                assertEquals(returnGameList.getType(), TypeMenue.returnGameList.getOrdinal());
                assertEquals(returnGameList.getStartingGames().size(), 0);
                assertEquals(returnGameList.getRunningGames().size(), 2);
                assertEquals(returnGameList.getFinishedGames().size(), 1);
            } catch (JsonSyntaxException e) {
                fail("Response string has wrong format: " + e.getMessage());
            }
        } catch (HandlingException e) {
            fail("Handling exception thrown during test: " + e.getMessage());
        }
    }
}







