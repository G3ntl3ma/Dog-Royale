package com.nexusvision.server.handler.message;

import com.nexusvision.messages.menu.Error;
import com.nexusvision.messages.menu.RequestTournamentInfo;
import com.nexusvision.messages.menu.ReturnTournamentInfo;
import com.nexusvision.messages.menu.TypeMenue;
import com.nexusvision.server.controller.ServerController;
import lombok.Data;

@Data
public class RequestTournamentInfoHandler implements MessageHandler<RequestTournamentInfo> {

    @Override
    public String handle(RequestTournamentInfo message, int clientID) {


        if (false) {
            Error error = new Error();
            error.setType(TypeMenue.error);
            error.setDataId(TypeMenue.requestTournamentInfo.ordinal() + 100);
            error.setMessage("TournamentInfo Request failed");
            return gson.toJson(error);
        }


        ReturnTournamentInfo returnTournamentInfo = new ReturnTournamentInfo();
        returnTournamentInfo.setType(TypeMenue.requestTournamentInfo);
        returnTournamentInfo.getTournamentInfo();
        return gson.toJson(returnTournamentInfo);

    }
}
