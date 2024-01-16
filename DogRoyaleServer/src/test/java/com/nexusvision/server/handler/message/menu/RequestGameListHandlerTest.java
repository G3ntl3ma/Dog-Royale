package com.nexusvision.server.handler.message.menu;

import com.google.gson.JsonSyntaxException;
import com.nexusvision.server.controller.GameLobby;
import com.nexusvision.server.handler.HandlerTest;
import com.nexusvision.server.handler.HandlingException;
import com.nexusvision.server.model.enums.Colors;
import com.nexusvision.server.model.enums.GameState;
import com.nexusvision.server.model.messages.menu.RequestGameList;
import com.nexusvision.server.model.messages.menu.ReturnGameList;
import com.nexusvision.server.model.messages.menu.TypeMenue;
import com.nexusvision.server.testutils.LobbyExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

/**
 * @author felixwr
 */
@ExtendWith(LobbyExtension.class)
class RequestGameListHandlerTest extends HandlerTest {

    @Test
    void testHandle() {
        RequestGameListHandler handler = new RequestGameListHandler();

        RequestGameList request = new RequestGameList();
        request.setType(TypeMenue.requestGameList.getOrdinal());
        request.setClientId(clientId);
        request.setGameCountStarting(1);
        request.setGameCountInProgress(2);
        request.setGameCountFinished(1);

        try {
            handler.handle(request, clientId);
            String response = messageListener.getLastMessage();

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







