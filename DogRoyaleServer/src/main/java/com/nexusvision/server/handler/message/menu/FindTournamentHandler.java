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

        if (message.getTournamentStarting() == 0 && message.getTournamentFinished() == 0
                && message.getTournamentInProgress() == 0) {
            return handleError("Failed to find tournament (no tournaments)",
                    TypeMenue.findTournament.getOrdinal());
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
