package com.nexusvision.server.handler.message.menu;

import com.nexusvision.server.model.messages.menu.RequestGameList;
import com.nexusvision.server.model.messages.menu.ReturnGameList;
import com.nexusvision.server.model.messages.menu.TypeMenue;
import lombok.Data;

@Data
public class RequestGameListHandler implements MenuMessageHandler<RequestGameList> {

    @Override
    public String handle(RequestGameList message, int clientID) {
        /*
        if (false) {
            Error error = new Error();
            error.setType(TypeMenue.error);
            error.setDataId(TypeMenue.requestGameList.ordinal() + 100);
            error.setMessage("Request failed (no Games)");

            return gson.toJson(error);
        }

         */

        ReturnGameList returnGameList = new ReturnGameList();
        returnGameList.setType(TypeMenue.returnGameList.getOrdinal());
        returnGameList.getStartingGames();
        returnGameList.getFinishedGames();
        returnGameList.getRunningGames();

        return gson.toJson(returnGameList);
    }
}

