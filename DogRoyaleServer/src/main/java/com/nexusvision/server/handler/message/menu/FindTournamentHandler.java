package com.nexusvision.server.handler.message.menu;

import com.nexusvision.server.handler.Handler;
import com.nexusvision.server.handler.message.MessageHandler;
import com.nexusvision.server.model.messages.menu.FindTournament;
import com.nexusvision.server.model.messages.menu.ReturnFindTournament;
import com.nexusvision.server.model.messages.menu.Error;
import com.nexusvision.server.model.messages.menu.TypeMenue;
import lombok.Data;

import java.util.ArrayList;

public class FindTournamentHandler extends MessageHandler<FindTournament> {

    @Override
    protected String performHandle(FindTournament message, int clientID) {

        ReturnFindTournament returnFindTournament = new ReturnFindTournament();
        returnFindTournament.setType(TypeMenue.returnFindTournament.getOrdinal());
        returnFindTournament.setClientId(clientID);
        returnFindTournament.setTournamentFinished(new ArrayList<>()); // TODO: Implement tournaments
        returnFindTournament.setTournamentStarting(new ArrayList<>());
        returnFindTournament.setTournamentInProgress(new ArrayList<>());

        return gson.toJson(returnFindTournament);
    }
}
