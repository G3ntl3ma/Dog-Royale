package com.nexusvision.server.handler.message.menu;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.nexusvision.server.controller.ServerController;
import com.nexusvision.server.handler.HandlerTest;
import com.nexusvision.server.handler.HandlingException;
import com.nexusvision.server.model.messages.menu.ConnectToServer;
import com.nexusvision.server.model.messages.menu.ConnectedToServer;
import com.nexusvision.server.model.messages.menu.TypeMenue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

public class ConnectToServerHandlerTest extends HandlerTest {

    @Test
    public void testHandle() {
        ConnectToServerHandler handler = new ConnectToServerHandler();
        ServerController serverController = ServerController.getInstance();
        int clientID = serverController.createNewClient();

        ConnectToServer request = new ConnectToServer();
        request.setType(TypeMenue.connectToServer.getOrdinal());
        request.setName("Max Mustermann");
        request.setIsObserver(true);

        try {
            String response = handler.handle(request, clientID);

            try {
                ConnectedToServer connectedToServer = gson.fromJson(response, ConnectedToServer.class);
                assertNotNull(connectedToServer.getType());
                assertNotNull(connectedToServer.getClientId()); // In case type gets changed later
                assertEquals(connectedToServer.getType(), TypeMenue.connectedToServer.getOrdinal());
                assertEquals(connectedToServer.getClientId(), clientID);
            } catch (JsonSyntaxException e) {
                fail("Response string has wrong format: " + e.getMessage());
            }
        } catch (HandlingException e) {
            fail("Handling exception thrown during test: " + e.getMessage());
        }
    }
}
