package com.nexusvision.server.handler.message.menu;

import com.nexusvision.server.handler.Handler;
import com.nexusvision.server.model.messages.menu.ConnectToServer;
import com.nexusvision.server.model.messages.menu.ConnectedToServer;
import com.nexusvision.server.model.messages.menu.Error;
import com.nexusvision.server.model.messages.menu.TypeMenue;
import com.nexusvision.server.controller.ServerController;
import lombok.Data;

/**
 * @author felixwr
 */
@Data
public class ConnectToServerHandler extends Handler implements MenuMessageHandler<ConnectToServer> {

    @Override
    public String handle(ConnectToServer message, int clientID) {

        if (message.getName() == null) {
            return handleError("Connect to server failed, name is null",
                    TypeMenue.connectToServer.getOrdinal());
        }

        if (message.getIsObserver() == null) {
            return handleError("Connect to server fail, isObserver boolean null",
                    TypeMenue.connectToServer.getOrdinal());
        }

        ServerController serverController = ServerController.getInstance();
        serverController.setUsername(clientID, message.getName());
        serverController.setObserver(clientID, message.getIsObserver());
        ConnectedToServer connectedToServer = new ConnectedToServer();
        connectedToServer.setType(TypeMenue.connectedToServer.getOrdinal());
        connectedToServer.setClientId(clientID);

        return gson.toJson(connectedToServer);
    }
}
