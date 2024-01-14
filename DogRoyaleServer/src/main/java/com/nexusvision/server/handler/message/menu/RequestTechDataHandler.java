package com.nexusvision.server.handler.message.menu;

import com.nexusvision.config.AppConfig;
import com.nexusvision.server.common.ChannelType;
import com.nexusvision.server.controller.MessageBroker;
import com.nexusvision.server.controller.ServerController;
import com.nexusvision.server.handler.HandlingException;
import com.nexusvision.server.handler.message.MessageHandler;
import com.nexusvision.server.model.messages.menu.RequestTechData;
import com.nexusvision.server.model.messages.menu.ReturnTechData;
import com.nexusvision.server.model.messages.menu.TypeMenue;
import lombok.Data;

/**
 * Handling all requests of type <code>RequestTechData</code>
 *
 * @author felixwr
 */
@Data
public class RequestTechDataHandler extends MessageHandler<RequestTechData> {

    /**
     * Handles a client's request for technical data about the server. It retrieves relevant information from the server's configuration,
     * creates a ReturnTechData object with this information.
     * The response includes details such as the server version, supported protocol, and embedded extensions.
     *
     * @param message An Instance of the <code>RequestTechData</code> representing a client's request for technical data
     * @param clientId An Integer representing the Id of the requesting client
     */
    @Override
    protected void performHandle(RequestTechData message, int clientId) throws HandlingException {

        ServerController serverController = ServerController.getInstance();
        AppConfig config = AppConfig.getInstance();

        ReturnTechData returnTechData = new ReturnTechData();
        returnTechData.setType(TypeMenue.returnTechData.getOrdinal());
        returnTechData.setServerVersion(config.getProperty("serverVersion"));
        returnTechData.setSupportedProtocol(config.getProperty("supportedProtocol"));
        returnTechData.setEmbeddedExtensions(config.getPropertyIntList("embeddedExtensions"));

        String response = gson.toJson(returnTechData);
        MessageBroker.getInstance().sendMessage(ChannelType.SINGLE, clientId, response);
    }
}

