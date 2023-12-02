package com.nexusvision.server.handler.message.menu;

import com.nexusvision.server.model.messages.menu.FindTournament;
import com.nexusvision.server.model.messages.menu.ReturnFindTournament;
import com.nexusvision.server.model.messages.menu.Error;
import com.nexusvision.server.model.messages.menu.TypeMenue;
import lombok.Data;

@Data
public class FindTournamentHandler implements MenuMessageHandler<FindTournament> {

    @Override
    public String handle(FindTournament message, int clientID) {

        if (message.getTournamentStarting() == 0 && message.getTournamentFinished() == 0 && message.getTournamentInProgress() == 0) {
            Error error = new Error();
            error.setType(TypeMenue.error.getOrdinal());
            error.setDataId(TypeMenue.findTournament.getOrdinal());
            error.setMessage("tournament fail (no tournaments)");

            return gson.toJson(error);
        }
        ReturnFindTournament returnFindTournament = new ReturnFindTournament();
        returnFindTournament.setType(TypeMenue.returnFindTournament.getOrdinal());
        returnFindTournament.setClientId(clientID);
        returnFindTournament.getTournamentFinished();
        returnFindTournament.getTournamentStarting();
        returnFindTournament.getTournamentInProgress();

        return gson.toJson(returnFindTournament);
    }
}
