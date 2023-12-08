package com.nexusvision.server.handler.message.menu;

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

        ReturnTechData returnTechData = new ReturnTechData();
        returnTechData.setType(TypeMenue.returnTechData.getOrdinal());
        returnTechData.getServerVersion();
        returnTechData.getSupportedProtocol();
        returnTechData.getEmbeddedExtensions();

        return gson.toJson(returnTechData);
    }
}

