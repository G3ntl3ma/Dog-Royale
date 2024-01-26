package com.nexusvision.server.handler.message.menu;

import com.google.gson.JsonSyntaxException;
import com.nexusvision.server.handler.HandlerTest;
import com.nexusvision.server.handler.HandlingException;
import com.nexusvision.server.model.messages.menu.RequestGameList;
import com.nexusvision.server.model.messages.menu.ReturnGameList;
import com.nexusvision.server.model.messages.menu.TypeMenue;
import com.nexusvision.server.testutils.LobbyExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

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
        request.setGamesUpcomingCount(1);
        request.setGamesRunningCount(1);
        request.setGamesFinishedCount(3);

        try {
            handler.handle(request, clientId);
            String response = messageListener.getLastMessage();

            try  {
                ReturnGameList returnGameList = gson.fromJson(response, ReturnGameList.class);
                assertNotNull(returnGameList.getGamesUpcoming());
                assertNotNull(returnGameList.getGamesRunning());
                assertNotNull(returnGameList.getGamesFinished());
                assertEquals(returnGameList.getType(), TypeMenue.returnGameList.getOrdinal());
                assertEquals(returnGameList.getGamesUpcoming().size(), 1);
                assertEquals(returnGameList.getGamesRunning().size(), 1);
                assertEquals(returnGameList.getGamesFinished().size(), 1);
            } catch (JsonSyntaxException e) {
                fail("Response string has wrong format: " + e.getMessage());
            }
        } catch (HandlingException e) {
            fail("Handling exception thrown during test: " + e.getMessage());
        }
    }
}







