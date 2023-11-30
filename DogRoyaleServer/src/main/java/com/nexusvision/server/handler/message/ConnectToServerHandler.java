package com.nexusvision.server.handler.message;

import com.nexusvision.messages.menu.ConnectToServer;
import com.nexusvision.messages.menu.ConnectedToServer;
import com.nexusvision.messages.menu.Error;
import com.nexusvision.messages.menu.TypeMenue;
import lombok.Data;

/**
 * @author feliwr
 */
@Data
public class ConnectToServerHandler implements MessageHandler<ConnectToServer> {

    @Override
    public String handle(ConnectToServer message, int clientID) {
        /* {" type " : TypeMenue . connectedToServer ,
            " clientId " : int}
        or error */

        if (message.getName() == null) { //isObserver
//            return "{\"type\": " + (TypeMenue.error.ordinal() + 100) +
//                    ", \"dataID\": " + TypeMenue.connectToServer.ordinal() + // + 100???
//                    ", \"message\": \"could not connect to server because no name and observer bool given\"}";
            Error error = new Error();
            error.setType(TypeMenue.error);
            error.setDataId(TypeMenue.connectToServer.ordinal() + 100);
            error.setMessage("connect to server fail (null)");
            return gson.toJson(error);
        }

        //TODO add clientId to the "server" somehow
//        int id = Ids.size();
//        Ids.add(id);
        ConnectedToServer connectedToServer = new ConnectedToServer();
        connectedToServer.setType(TypeMenue.connectedToServer);
        connectedToServer.setClientId(clientID);
        return gson.toJson(connectedToServer);

//        return "{\"type\": " + (TypeMenue.connectedToServer.ordinal() + 100) +
//                ", \"clientId\": " + id + "}";

        // return response;
    }
}
