package com.nexusvision.server.handler.message.menu;

import com.nexusvision.config.AppConfig;
import com.nexusvision.server.controller.ServerController;
import com.nexusvision.server.handler.HandlingException;
import com.nexusvision.server.handler.message.MessageHandler;
import com.nexusvision.server.model.messages.menu.RequestTechData;
import com.nexusvision.server.model.messages.menu.ReturnTechData;
import com.nexusvision.server.model.messages.menu.TypeMenue;
import lombok.Data;

@Data
public class RequestTechDataHandler extends MessageHandler<RequestTechData> {

    @Override
    protected String performHandle(RequestTechData message, int clientID) throws HandlingException {

        ServerController serverController = ServerController.getInstance();
        AppConfig config = AppConfig.getInstance();

        ReturnTechData returnTechData = new ReturnTechData();
        returnTechData.setType(TypeMenue.returnTechData.getOrdinal());
        returnTechData.setServerVersion(config.getProperty("serverVersion"));
        returnTechData.setSupportedProtocol(config.getProperty("supportedProtocol"));
        returnTechData.setEmbeddedExtensions(config.getPropertyIntList("embeddedExtensions"));

        return gson.toJson(returnTechData);
    }
}

