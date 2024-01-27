package com.nexusvision.server.handler.message.menu;

import com.google.gson.JsonSyntaxException;
import com.nexusvision.server.common.MessageListener;
import com.nexusvision.server.controller.GameLobby;
import com.nexusvision.server.handler.HandlerTest;
import com.nexusvision.server.handler.HandlingException;
import com.nexusvision.server.model.gamelogic.LobbyConfig;
import com.nexusvision.server.model.messages.menu.*;
import com.nexusvision.server.testutils.LobbyExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

/**
 * @author felixwr
 */
@ExtendWith(LobbyExtension.class)
class RequestGameListHandlerTest extends HandlerTest {

    RequestGameListHandler handler = new RequestGameListHandler();

    @Test
    void testHandle() {
        RequestGameList request = new RequestGameList();
        request.setType(TypeMenue.requestGameList.getOrdinal());
        request.setClientId(clientId1);
        request.setGamesUpcomingCount(1);
        request.setGamesRunningCount(1);
        request.setGamesFinishedCount(3);

        ReturnGameList returnGameList = handleAndRetrieve(request, clientId1, messageListener1);

        assertNotNull(returnGameList.getGamesUpcoming());
        assertNotNull(returnGameList.getGamesRunning());
        assertNotNull(returnGameList.getGamesFinished());
        assertEquals(returnGameList.getType(), TypeMenue.returnGameList.getOrdinal());
        assertEquals(1, returnGameList.getGamesUpcoming().size());
        assertEquals(1, returnGameList.getGamesRunning().size());
        assertEquals(1, returnGameList.getGamesFinished().size());

        ReturnGameList.NotFinishedGame notFinishedGame = returnGameList.getGamesUpcoming().get(0);
        checkNotFinishedGame(notFinishedGame);

        notFinishedGame = returnGameList.getGamesRunning().get(0);
        checkNotFinishedGame(notFinishedGame);

        ReturnGameList.FinishedGame finishedGame = returnGameList.getGamesFinished().get(0);
        checkFinishedGame(finishedGame);
    }

    private static void checkNotFinishedGame(ReturnGameList.NotFinishedGame game) {
        GameLobby lobby = serverController.getLobbyById(game.getGameId());
        assertNotNull(lobby);
        LobbyConfig lobbyConfig = lobby.getLobbyConfig();
        assertEquals(lobbyConfig.getGameName(), game.getGameName());
        assertEquals(lobbyConfig.getWinnerOrder(), game.getWinnerOrder());
        assertEquals(lobbyConfig.getPlayerOrder().getOrder(), game.getPlayerOrder());
        assertEquals(lobbyConfig.getMaxPlayerCount(), game.getMaxPlayerCount());
    }

    private static void checkFinishedGame(ReturnGameList.FinishedGame game) {
        GameLobby lobby = serverController.getLobbyById(game.getGameId());
        assertNotNull(lobby);
        LobbyConfig lobbyConfig = lobby.getLobbyConfig();
        assertEquals(lobbyConfig.getGameName(), game.getGameName());
        assertEquals(lobbyConfig.getWinnerOrder(), game.getWinnerOrder());
        assertEquals(lobby.isCanceled(), game.isWasCanceled());
    }

    private ReturnGameList handleAndRetrieve(RequestGameList request, int clientId, MessageListener messageListener) {
        return super.handleAndRetrieve(request, handler, clientId, messageListener, ReturnGameList.class);
    }
}







