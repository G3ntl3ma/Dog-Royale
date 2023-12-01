package com.nexusvision.server.handler.message.menuhandler;

import com.nexusvision.messages.menu.*;
import com.nexusvision.messages.menu.Error;
import lombok.Data;

@Data
public class RequestGameListHandler implements MenuMessageHandler<RequestGameList> {

    @Override
    public String handle(RequestGameList message, int clientID){


                if (false) {
                    Error error = new Error();
                    error.setType(TypeMenue.error);
                    error.setDataId(TypeMenue.requestGameList.ordinal() + 100);
                    error.setMessage("Request failed (no Games)");
                    return gson.toJson(error);
                }


        ReturnGameList returnGameList = new ReturnGameList();
        returnGameList.setType(TypeMenue.returnGameList);
        returnGameList.getStartingGames(); //1.Liste ??
        returnGameList.getFinishedGames(); //2.Liste ??
        returnGameList.getRunningGames(); //3.Liste ??
        return gson.toJson(returnGameList);

    }

}

