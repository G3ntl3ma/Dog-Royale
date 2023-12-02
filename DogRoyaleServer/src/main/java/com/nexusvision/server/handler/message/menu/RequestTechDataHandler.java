package com.nexusvision.server.handler.message.menu;

import com.nexusvision.server.model.messages.menu.RequestTechData;
import com.nexusvision.server.model.messages.menu.ReturnTechData;
import com.nexusvision.server.model.messages.menu.Error;
import com.nexusvision.server.model.messages.menu.TypeMenue;
import lombok.Data;

@Data
public class RequestTechDataHandler implements MenuMessageHandler<RequestTechData> {

    @Override
    public java.lang.String handle(RequestTechData message, int clientID) {

        if (false) {
            Error error = new Error();
            error.setType(TypeMenue.error.getOrdinal());
            error.setDataId(TypeMenue.requestGameList.getOrdinal());
            error.setMessage("Request failed (no TechData available)");

            return gson.toJson(error);
        }

        ReturnTechData returnTechData = new ReturnTechData();
        returnTechData.setType(TypeMenue.returnTechData.getOrdinal());
        returnTechData.getServerVersion();
        returnTechData.getSupportedProtocol();
        returnTechData.getEmbeddedExtensions();

        return gson.toJson(returnTechData);
    }
}
