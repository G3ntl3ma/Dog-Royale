package com.nexusvision.server.handler.message.menu;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.nexusvision.server.common.MessageListener;
import com.nexusvision.server.controller.MessageBroker;
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

/**
 * @author felixwr
 */
class ConnectToServerHandlerTest extends HandlerTest {

    @Test
    void testHandle() {
        ConnectToServerHandler handler = new ConnectToServerHandler();

        ConnectToServer request = new ConnectToServer();
        request.setType(TypeMenue.connectToServer.getOrdinal());
        request.setName("Max Mustermann");
        request.setObserver(true);

        try {
            handler.handle(request, clientId);
            String response = messageListener.getLastMessage();

            try {
                ConnectedToServer connectedToServer = gson.fromJson(response, ConnectedToServer.class);
                assertEquals(connectedToServer.getType(), TypeMenue.connectedToServer.getOrdinal());
                assertEquals(connectedToServer.getClientId(), clientId);
            } catch (JsonSyntaxException e) {
                fail("Response string has wrong format: " + e.getMessage());
            }
        } catch (HandlingException e) {
            fail("Handling exception thrown during test: " + e.getMessage());
        }
    }
}
