package com.nexusvision.server.handler.message.menu;

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
public class ConnectToServerHandler implements MenuMessageHandler<ConnectToServer> {

    @Override
    public String handle(ConnectToServer message, int clientID) {

        if (message.getName() == null) {
            Error error = new Error();
            error.setType(TypeMenue.error.getOrdinal());
            error.setDataId(TypeMenue.connectToServer.getOrdinal());
            error.setMessage("connect to server failed, name is null");

            return gson.toJson(error);
        }

        if (message.getIsObserver() == null) {
            Error error = new Error();
            error.setType(TypeMenue.error.getOrdinal());
            error.setDataId(TypeMenue.connectToServer.getOrdinal());
            error.setMessage("connect to server fail, isObserver boolean null");

            return gson.toJson(error);
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
