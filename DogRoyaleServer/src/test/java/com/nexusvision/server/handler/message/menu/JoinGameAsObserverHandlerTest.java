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
import com.nexusvision.server.testutils.LobbyExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * @author felixwr
 */
@ExtendWith(LobbyExtension.class)
class JoinGameAsObserverHandlerTest extends HandlerTest {

    JoinGameAsObserverHandler handler = new JoinGameAsObserverHandler();

    @Test
    void testHandleUpcomingLobby() {
        GameLobby upcomingLobby = serverController.getLobbyOfPlayer(-101);

        JoinGameAsObserver request = getJoinGameAsObserverWithLobby(upcomingLobby.getId());

        ConnectedToGame connectedToGame = handleAndRetrieve(request);

        assertEquals(connectedToGame.getType(), TypeMenue.connectedToGame.getOrdinal());
        assertTrue(connectedToGame.isSuccess());
        assertEquals(upcomingLobby.getLobbyConfig().getObserverList().get(0).getClientId(), clientId1);
    }

    @Test
    void testHandleRunningLobby() {
        GameLobby runningLobby = serverController.getLobbyOfPlayer(-103);

        JoinGameAsObserver request = getJoinGameAsObserverWithLobby(runningLobby.getId());

        ConnectedToGame connectedToGame = handleAndRetrieve(request);

        assertEquals(connectedToGame.getType(), TypeMenue.connectedToGame.getOrdinal());
        assertTrue(connectedToGame.isSuccess());
        assertEquals(runningLobby.getLobbyConfig().getObserverList().get(0).getClientId(), clientId1);
    }

    @Test
    void testHandleFinishedLobby() {
        GameLobby finishedLobby = serverController.getLobbyOfPlayer(-105);

        JoinGameAsObserver request = getJoinGameAsObserverWithLobby(finishedLobby.getId());

        ConnectedToGame connectedToGame = handleAndRetrieve(request);

        assertEquals(connectedToGame.getType(), TypeMenue.connectedToGame.getOrdinal());
        assertTrue(connectedToGame.isSuccess());
        assertEquals(finishedLobby.getLobbyConfig().getObserverList().get(0).getClientId(), clientId1);
    }

    @Test
    void testHandleWrongLobby() {
        JoinGameAsObserver request = getJoinGameAsObserverWithLobby(-1000);

        ConnectedToGame connectedToGame = handleAndRetrieve(request);

        assertEquals(connectedToGame.getType(), TypeMenue.connectedToGame.getOrdinal());
        assertFalse(connectedToGame.isSuccess());
    }

    private static JoinGameAsObserver getJoinGameAsObserverWithLobby(int lobbyId) {
        JoinGameAsObserver request = new JoinGameAsObserver();
        request.setType(TypeMenue.joinGameAsObserver.getOrdinal());
        request.setGameId(lobbyId);
        request.setClientId(clientId1);
        return request;
    }

    private ConnectedToGame handleAndRetrieve(JoinGameAsObserver request) {
        return handleAndRetrieve(request, handler, clientId1, messageListener1, ConnectedToGame.class);
    }
}
