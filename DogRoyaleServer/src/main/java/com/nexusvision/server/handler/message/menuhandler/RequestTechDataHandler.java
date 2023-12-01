package com.nexusvision.server.handler.message.menuhandler;

import com.nexusvision.messages.menu.RequestTechData;
import com.nexusvision.messages.menu.ReturnTechData;
import com.nexusvision.messages.menu.Error;
import com.nexusvision.messages.menu.TypeMenue;
import lombok.Data;

@Data
public class RequestTechDataHandler implements MenuMessageHandler<RequestTechData> {

    @Override
    public java.lang.String handle(RequestTechData message, int clientID) {

        if (false) {
            Error error = new Error();
            error.setType(TypeMenue.error.ordinal() + 100);
            error.setDataId(TypeMenue.requestGameList.ordinal() + 100);
            error.setMessage("Request failed (no TechData available)");

            return gson.toJson(error);
        }

        ReturnTechData returnTechData = new ReturnTechData();
        returnTechData.setType(TypeMenue.returnTechData.ordinal() + 100);
        returnTechData.getServerVersion();
        returnTechData.getSupportedProtocol();
        returnTechData.getEmbeddedExtensions();

        return gson.toJson(returnTechData);
    }
}
