package com.nexusvision.server.handler.message.menu;

import com.nexusvision.server.controller.GameLobby;
import com.nexusvision.server.controller.ServerController;
import com.nexusvision.server.handler.HandlingException;
import com.nexusvision.server.handler.message.menu.RequestGameListHandler;
import com.nexusvision.server.handler.HandlingException;
import com.nexusvision.server.controller.GameLobby;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
/*
public class RequestGameListHandlerTest {

    @Mock
    private ServerController serverController;

    @InjectMocks
    private RequestGameListHandler requestGameListHandler;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }
    //TODO throws still a Nullpointer Exception, needs to be fixed
    @Test
    public void testHandle() throws HandlingException {
        // mocking of gamelist
        RequestGameList message = mock(RequestGameList.class);
        int clientID = 123;
        when(serverController.createNewClient()).thenReturn(clientID);
        ArrayList<GameLobby> startingGames = new ArrayList<>();
        ArrayList<GameLobby> runningGames = new ArrayList<>();
        ArrayList<GameLobby> finishedGames = new ArrayList<>();

        // mocking serverController methods to return specific game lists
        when(serverController.getStateGames(any(), any())).thenReturn(startingGames)
                .thenReturn(runningGames)
                .thenReturn(finishedGames);

        try {
            // calling the method being tested
            String result = requestGameListHandler.handle(message, clientID);

            // TODO replace message with actual JSON string
            String expectedJsonString = "{\"type\":0,\"startingGames\":[],\"runningGames\":[],\"finishedGames\":[]}";
            assertEquals(expectedJsonString, result);

            // verifying serverController methods
            verify(serverController, times(3)).getStateGames(any(), any());
        } catch (HandlingException e) {
            // failing test if there is an exception
            e.printStackTrace();
            fail("Exception occurred while handling requestGameList");
        }
    }
}

 */







