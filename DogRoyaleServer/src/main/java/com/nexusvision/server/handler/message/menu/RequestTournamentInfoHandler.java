package com.nexusvision.server.handler.message.menu;

import com.nexusvision.server.handler.Handler;
import com.nexusvision.server.model.messages.menu.Error;
import com.nexusvision.server.model.messages.menu.RequestTournamentInfo;
import com.nexusvision.server.model.messages.menu.ReturnTournamentInfo;
import com.nexusvision.server.model.messages.menu.TypeMenue;
import lombok.Data;

@Data
public class RequestTournamentInfoHandler extends Handler implements MenuMessageHandler<RequestTournamentInfo> {

    @Override
    public String handle(RequestTournamentInfo message, int clientID) {

        if (false) {
            return handleError("TournamentInfo request failed",
                    TypeMenue.requestTournamentInfo.getOrdinal());
        }

        ReturnTournamentInfo returnTournamentInfo = new ReturnTournamentInfo();
        returnTournamentInfo.setType(TypeMenue.requestTournamentInfo.getOrdinal());
        returnTournamentInfo.getTournamentInfo();

        return gson.toJson(returnTournamentInfo);
    }
}
