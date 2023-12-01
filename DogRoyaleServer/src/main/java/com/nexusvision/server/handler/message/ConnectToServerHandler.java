package com.nexusvision.server.handler.message;

import com.nexusvision.messages.menu.ConnectToServer;
import com.nexusvision.messages.menu.ConnectedToServer;
import com.nexusvision.messages.menu.Error;
import com.nexusvision.messages.menu.TypeMenue;
import com.nexusvision.server.controller.ServerController;
import lombok.Data;

/**
 * @author feliwr
 */
@Data
public class ConnectToServerHandler implements MessageHandler<ConnectToServer> {

    @Override
    public String handle(ConnectToServer message, int clientID) {

        if (message.getName() == null) {
            Error error = new Error();
            error.setType(TypeMenue.error);
            error.setDataId(TypeMenue.connectToServer.ordinal() + 100);
            error.setMessage("connect to server fail (null)");
            return gson.toJson(error);
        }


	ServerController.setUsername(clientID, message.getName());
        ConnectedToServer connectedToServer = new ConnectedToServer();
        connectedToServer.setType(TypeMenue.connectedToServer);
        connectedToServer.setClientId(clientID);
        return gson.toJson(connectedToServer);
    }
}
