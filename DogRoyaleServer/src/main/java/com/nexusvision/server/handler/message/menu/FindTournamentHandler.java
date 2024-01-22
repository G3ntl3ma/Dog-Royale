package com.nexusvision.server.handler.message.menu;

import com.nexusvision.server.common.ChannelType;
import com.nexusvision.server.controller.MessageBroker;
import com.nexusvision.server.handler.message.MessageHandler;
import com.nexusvision.server.model.messages.menu.RequestTournamentList;
import com.nexusvision.server.model.messages.menu.ReturnTournamentList;
import com.nexusvision.server.model.messages.menu.TypeMenue;

import java.util.ArrayList;

public class FindTournamentHandler extends MessageHandler<RequestTournamentList> {

    @Override
    protected void performHandle(RequestTournamentList message, int clientID) {

        ReturnTournamentList returnTournamentList = new ReturnTournamentList();
        returnTournamentList.setType(TypeMenue.returnTournamentList.getOrdinal());
        returnTournamentList.setClientId(clientID);
        returnTournamentList.setTournamentsFinished(new ArrayList<>()); // TODO: Implement tournaments
        returnTournamentList.setTournamentsUpcoming(new ArrayList<>());
        returnTournamentList.setTournamentsRunning(new ArrayList<>());

        String response = gson.toJson(returnTournamentList);
        MessageBroker.getInstance().sendMessage(ChannelType.SINGLE, clientID, response);
    }
}
