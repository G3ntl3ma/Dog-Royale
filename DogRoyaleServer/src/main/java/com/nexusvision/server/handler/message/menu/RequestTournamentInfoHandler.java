package com.nexusvision.server.handler.message.menu;

import com.nexusvision.server.model.messages.menu.Error;
import com.nexusvision.server.model.messages.menu.RequestTournamentInfo;
import com.nexusvision.server.model.messages.menu.ReturnTournamentInfo;
import com.nexusvision.server.model.messages.menu.TypeMenue;
import lombok.Data;

@Data
public class RequestTournamentInfoHandler implements MenuMessageHandler<RequestTournamentInfo> {

    @Override
    public String handle(RequestTournamentInfo message, int clientID) {

        if (false) {
            Error error = new Error();
            error.setType(TypeMenue.error.getOrdinal());
            error.setDataId(TypeMenue.requestTournamentInfo.getOrdinal());
            error.setMessage("TournamentInfo Request failed");

            return gson.toJson(error);
        }

        ReturnTournamentInfo returnTournamentInfo = new ReturnTournamentInfo();
        returnTournamentInfo.setType(TypeMenue.requestTournamentInfo.getOrdinal());
        returnTournamentInfo.getTournamentInfo();

        return gson.toJson(returnTournamentInfo);
    }
}
