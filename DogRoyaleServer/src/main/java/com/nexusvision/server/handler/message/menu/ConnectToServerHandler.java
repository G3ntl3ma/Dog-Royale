package com.nexusvision.server.handler.message.menu;

import com.nexusvision.server.handler.Handler;
import com.nexusvision.server.handler.HandlingException;
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
public class ConnectToServerHandler extends Handler implements MenuMessageHandler<ConnectToServer> {

    @Override
    public String handle(ConnectToServer message, int clientID) throws HandlingException {
        try {
            ServerController serverController = ServerController.getInstance();
            Client client = serverController.getClientById(clientID);
            client.setName(message.getName());
            client.setObserver(message.getIsObserver());

            ConnectedToServer connectedToServer = new ConnectedToServer();
            connectedToServer.setType(TypeMenue.connectedToServer.getOrdinal());
            connectedToServer.setClientId(clientID);

            return gson.toJson(connectedToServer);
        } catch (Exception e) {
            throw new HandlingException("Exception while handling connectToServer",
                    e, TypeMenue.connectToServer.getOrdinal());
        }
    }
}
