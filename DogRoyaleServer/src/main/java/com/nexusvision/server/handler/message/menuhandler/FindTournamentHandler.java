package com.nexusvision.server.handler.message.menuhandler;

import com.nexusvision.messages.menu.FindTournament;
import com.nexusvision.messages.menu.ReturnFindTournament;
import com.nexusvision.messages.menu.Error;
import com.nexusvision.messages.menu.TypeMenue;
import lombok.Data;

@Data
public class FindTournamentHandler implements MenuMessageHandler<FindTournament> {


    @Override       //neuer Handler ???
        public String handle(FindTournament message, int clientID){

        if (message.getTournamentStarting() == 0 && message.getTournamentFinished() == 0 && message.getTournamentInProgress() == 0) {
            Error error = new Error();
            error.setType(TypeMenue.error);
            error.setDataId(TypeMenue.findTournament.ordinal() + 100);
            error.setMessage("tournament fail (no tournaments)");
            return gson.toJson(error);
        }


        ReturnFindTournament returnFindTournament = new ReturnFindTournament();
        returnFindTournament.setType(TypeMenue.returnFindTournament);
        returnFindTournament.setClientId(clientID);
        returnFindTournament.getTournamentFinished(); //1.Liste ??
        returnFindTournament.getTournamentStarting(); //2. Liste ??
        returnFindTournament.getTournamentInProgress(); //3.Liste ??
        return gson.toJson(returnFindTournament);
        }
    }
