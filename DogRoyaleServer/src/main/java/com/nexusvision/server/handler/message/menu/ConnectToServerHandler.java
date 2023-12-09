package com.nexusvision.server.handler.message.menu;

import com.nexusvision.server.handler.Handler;
import com.nexusvision.server.handler.HandlingException;
import com.nexusvision.server.handler.message.MessageHandler;
import com.nexusvision.server.model.entities.Client;
import com.nexusvision.server.model.messages.menu.ConnectToServer;
import com.nexusvision.server.model.messages.menu.ConnectedToServer;
import com.nexusvision.server.model.messages.menu.Error;
import com.nexusvision.server.model.messages.menu.TypeMenue;
import com.nexusvision.server.controller.ServerController;
import lombok.Data;

/**
 * Handles a <code>connectToServer</code> request
 *
 * @author felixwr
 */
public class ConnectToServerHandler extends MessageHandler<ConnectToServer> {

    /**
     * Handles a <code>connectToServer</code> request
     *
     * @param message An instance of <code>connectToServer</code> representing the client's connection request message
     * @param clientId An Integer representing the Id of the client
     * @return A String representing a <code>ConnectedToServer</code> message
     */
    @Override
    protected String performHandle(ConnectToServer message, int clientId) throws HandlingException {
            ServerController serverController = ServerController.getInstance();
            Client client = serverController.getClientById(clientId);
            client.setName(message.getName());
            client.setObserver(message.getIsObserver());

            ConnectedToServer connectedToServer = new ConnectedToServer();
            connectedToServer.setType(TypeMenue.connectedToServer.getOrdinal());
            connectedToServer.setClientId(clientId);

            return gson.toJson(connectedToServer);
    }
}
