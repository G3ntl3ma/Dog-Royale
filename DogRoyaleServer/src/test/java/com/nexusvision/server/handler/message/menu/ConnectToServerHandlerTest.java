package com.nexusvision.server.handler.message.menu;

import com.google.gson.JsonSyntaxException;
import com.nexusvision.server.handler.HandlerTest;
import com.nexusvision.server.handler.HandlingException;
import com.nexusvision.server.model.messages.menu.ConnectToServer;
import com.nexusvision.server.model.messages.menu.ConnectedToServer;
import com.nexusvision.server.model.messages.menu.TypeMenue;
import org.junit.jupiter.api.Test;

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
            handler.handle(request, clientId1);
            String response = messageListener1.retrieveMessage();

            try {
                ConnectedToServer connectedToServer = gson.fromJson(response, ConnectedToServer.class);
                assertEquals(connectedToServer.getType(), TypeMenue.connectedToServer.getOrdinal());
                assertEquals(connectedToServer.getClientId(), clientId1);
            } catch (JsonSyntaxException e) {
                fail("Response string has wrong format: " + e.getMessage());
            }
        } catch (HandlingException e) {
            fail("Handling exception thrown during test: " + e.getMessage());
        }
    }
}
