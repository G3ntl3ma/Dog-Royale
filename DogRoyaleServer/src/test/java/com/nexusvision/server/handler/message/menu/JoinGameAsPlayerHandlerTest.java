package com.nexusvision.server.handler.message.menu;

import com.google.gson.JsonSyntaxException;
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
public class JoinGameAsPlayerHandlerTest extends HandlerTest {

    JoinGameAsPlayerHandler handler = new JoinGameAsPlayerHandler();

    @Test
    public void testHandleSuccessful() {
        GameLobby upcomingLobby = serverController.getLobbyOfPlayer(-101);

        JoinGameAsPlayer joinGameAsPlayer = getJoinGameAsPlayerWithLobby(clientId1, upcomingLobby.getId());

        try {
            handler.handle(joinGameAsPlayer, clientId1);
            String response = messageListener1.getLastMessage();

            try {
                ConnectedToGame connectedToGame = gson.fromJson(response, ConnectedToGame.class);

                assertEquals(connectedToGame.getType(), TypeMenue.connectedToGame.getOrdinal());
                assertTrue(connectedToGame.isSuccess());
            } catch (JsonSyntaxException e) {
                fail("Response string has wrong format: " + e.getMessage());
            }
        } catch (HandlingException e) {
            fail("Handling exception thrown during test: " + e.getMessage());
        }
    }

    @Test
    public void testHandleWrongLobby() {
        JoinGameAsPlayer joinGameAsPlayer = getJoinGameAsPlayerWithLobby(clientId1, -1000);

        try {
            handler.handle(joinGameAsPlayer, clientId1);
            String response = messageListener1.getLastMessage();

            try {
                ConnectedToGame connectedToGame = gson.fromJson(response, ConnectedToGame.class);

                assertEquals(connectedToGame.getType(), TypeMenue.connectedToGame.getOrdinal());
                assertFalse(connectedToGame.isSuccess());
            } catch (JsonSyntaxException e) {
                fail("Response string has wrong format: " + e.getMessage());
            }
        } catch (HandlingException e) {
            fail("Handling exception thrown during test: " + e.getMessage());
        }
    }

    @Test
    public void testHandleFullLobby() {
        GameLobby upcomingLobby = serverController.getLobbyOfPlayer(-102); // maxPlayerCount=2 for this lobby

        JoinGameAsPlayer joinGameAsPlayer1 = getJoinGameAsPlayerWithLobby(clientId1, upcomingLobby.getId());
        JoinGameAsPlayer joinGameAsPlayer2 = getJoinGameAsPlayerWithLobby(clientId2, upcomingLobby.getId());

        try {
            handler.handle(joinGameAsPlayer1, clientId1);
            handler.handle(joinGameAsPlayer2, clientId2);
            String response1 = messageListener1.getLastMessage();
            String response2 = messageListener2.getLastMessage();

            try {
                ConnectedToGame connectedToGame1 = gson.fromJson(response1, ConnectedToGame.class);
                ConnectedToGame connectedToGame2 = gson.fromJson(response2, ConnectedToGame.class);

                assertEquals(connectedToGame1.getType(), TypeMenue.connectedToGame.getOrdinal());
                assertTrue(connectedToGame1.isSuccess());

                assertEquals(connectedToGame2.getType(), TypeMenue.connectedToGame.getOrdinal());
                assertFalse(connectedToGame2.isSuccess());
            } catch (JsonSyntaxException e) {
                fail("Response string has wrong format: " + e.getMessage());
            }
        } catch (HandlingException e) {
            fail("Handling exception thrown during test: " + e.getMessage());
        }
    }

    private JoinGameAsPlayer getJoinGameAsPlayerWithLobby(int clientId, int lobbyId) {
        JoinGameAsPlayer joinGameAsPlayer = new JoinGameAsPlayer();
        joinGameAsPlayer.setType(TypeMenue.joinGameAsPlayer.getOrdinal());
        joinGameAsPlayer.setGameId(lobbyId);
        joinGameAsPlayer.setClientId(clientId);
        joinGameAsPlayer.setPlayerName("clientId:" + clientId);

        return joinGameAsPlayer;
    }
}
