package com.nexusvision.server.handler.message.menu;

import com.google.gson.JsonSyntaxException;
import com.nexusvision.server.common.MessageListener;
import com.nexusvision.server.controller.GameLobby;
import com.nexusvision.server.handler.HandlerTest;
import com.nexusvision.server.handler.HandlingException;
import com.nexusvision.server.model.messages.menu.ConnectedToGame;
import com.nexusvision.server.model.messages.menu.JoinGameAsPlayer;
import com.nexusvision.server.model.messages.menu.TypeMenue;
import com.nexusvision.server.testutils.LobbyExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author felixwr
 */
@ExtendWith(LobbyExtension.class)
class JoinGameAsPlayerHandlerTest extends HandlerTest {

    JoinGameAsPlayerHandler handler = new JoinGameAsPlayerHandler();

    @Test
    void testHandleSuccessful() {
        GameLobby upcomingLobby = serverController.getLobbyOfPlayer(-101);

        JoinGameAsPlayer request = getJoinGameAsPlayerWithLobby(clientId1, upcomingLobby.getId());

        ConnectedToGame connectedToGame = handleAndRetrieve(request, clientId1, messageListener1);

        assertEquals(connectedToGame.getType(), TypeMenue.connectedToGame.getOrdinal());
        assertTrue(connectedToGame.isSuccess());
        assertTrue(upcomingLobby.getLobbyConfig().getPlayerOrder().getOrder().stream().anyMatch(
                playerElement -> playerElement.getClientId() == clientId1
        ));
    }

    @Test
    void testHandleWrongLobby() {
        JoinGameAsPlayer joinGameAsPlayer = getJoinGameAsPlayerWithLobby(clientId1, -1000);

        ConnectedToGame connectedToGame = handleAndRetrieve(joinGameAsPlayer, clientId1, messageListener1);

        assertEquals(connectedToGame.getType(), TypeMenue.connectedToGame.getOrdinal());
        assertFalse(connectedToGame.isSuccess());
    }

    @Test
    void testHandleFullLobby() {
        GameLobby upcomingLobby = serverController.getLobbyOfPlayer(-102); // maxPlayerCount=2 for this lobby

        JoinGameAsPlayer joinGameAsPlayer1 = getJoinGameAsPlayerWithLobby(clientId1, upcomingLobby.getId());
        JoinGameAsPlayer joinGameAsPlayer2 = getJoinGameAsPlayerWithLobby(clientId2, upcomingLobby.getId());

        ConnectedToGame connectedToGame1 = handleAndRetrieve(joinGameAsPlayer1, clientId1, messageListener1);
        ConnectedToGame connectedToGame2 = handleAndRetrieve(joinGameAsPlayer2, clientId2, messageListener2);

        assertEquals(connectedToGame1.getType(), TypeMenue.connectedToGame.getOrdinal());
        assertTrue(connectedToGame1.isSuccess());

        assertEquals(connectedToGame2.getType(), TypeMenue.connectedToGame.getOrdinal());
        assertFalse(connectedToGame2.isSuccess());
    }

    @Test
    void testHandleRunningLobby() {
        GameLobby runningLobby = serverController.getLobbyOfPlayer(-103);

        JoinGameAsPlayer joinGameAsPlayer = getJoinGameAsPlayerWithLobby(clientId1, runningLobby.getId());

        ConnectedToGame connectedToGame = handleAndRetrieve(joinGameAsPlayer, clientId1, messageListener1);

        assertEquals(connectedToGame.getType(), TypeMenue.connectedToGame.getOrdinal());
        assertFalse(connectedToGame.isSuccess());
    }

    @Test
    void testHandleFinishedLobby() {
        GameLobby runningLobby = serverController.getLobbyOfPlayer(-105);

        JoinGameAsPlayer joinGameAsPlayer = getJoinGameAsPlayerWithLobby(clientId1, runningLobby.getId());

        ConnectedToGame connectedToGame = handleAndRetrieve(joinGameAsPlayer, clientId1, messageListener1);

        assertEquals(connectedToGame.getType(), TypeMenue.connectedToGame.getOrdinal());
        assertFalse(connectedToGame.isSuccess());
    }

    private JoinGameAsPlayer getJoinGameAsPlayerWithLobby(int clientId, int lobbyId) {
        JoinGameAsPlayer joinGameAsPlayer = new JoinGameAsPlayer();
        joinGameAsPlayer.setType(TypeMenue.joinGameAsPlayer.getOrdinal());
        joinGameAsPlayer.setGameId(lobbyId);
        joinGameAsPlayer.setClientId(clientId);
        joinGameAsPlayer.setPlayerName("clientId:" + clientId);

        return joinGameAsPlayer;
    }

    private ConnectedToGame handleAndRetrieve(JoinGameAsPlayer request, int clientId, MessageListener messageListener) {
        return super.handleAndRetrieve(request, handler, clientId, messageListener, ConnectedToGame.class);
    }
}
