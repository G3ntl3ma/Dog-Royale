package com.nexusvision.server.handler.message.menu;

import com.nexusvision.server.handler.Handler;
import com.nexusvision.server.model.messages.menu.FindTournament;
import com.nexusvision.server.model.messages.menu.ReturnFindTournament;
import com.nexusvision.server.model.messages.menu.Error;
import com.nexusvision.server.model.messages.menu.TypeMenue;
import lombok.Data;

import java.util.ArrayList;

@Data
public class FindTournamentHandler extends Handler implements MenuMessageHandler<FindTournament> {

    @Override
    public String handle(FindTournament message, int clientID) {

        ReturnFindTournament returnFindTournament = new ReturnFindTournament();
        returnFindTournament.setType(TypeMenue.returnFindTournament.getOrdinal());
        returnFindTournament.setClientId(clientID);
        returnFindTournament.setTournamentFinished(new ArrayList<>()); // TODO: Implement tournaments
        returnFindTournament.setTournamentStarting(new ArrayList<>());
        returnFindTournament.setTournamentInProgress(new ArrayList<>());

        return gson.toJson(returnFindTournament);
    }
}
