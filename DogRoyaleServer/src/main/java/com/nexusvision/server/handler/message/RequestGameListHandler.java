package com.nexusvision.server.handler.message;

import com.nexusvision.messages.menu.*;
import com.nexusvision.messages.menu.Error;
import lombok.Data;

@Data
public class RequestGameListHandler implements MessageHandler<RequestGameList> {

    @Override
    public String handle(RequestGameList message, int clientID){

                /*
                if () {
                    Error error = new Error();
                    error.setType(TypeMenue.error);
                    error.setDataId(TypeMenue.requestGameList.ordinal() + 100);
                    error.setMessage("Game List fail (no Games)");
                    return gson.toJson(error);
                }

                 */


        ReturnGameList returnGameList = new ReturnGameList();
        returnGameList.setType(TypeMenue.returnGameList);
        returnGameList.setStartingGames(); //1.Liste ??
        returnGameList.setFinishedGames(); //2.Liste ??
        returnGameList.setRunningGames(); //3.Liste
        return gson.toJson(returnGameList);

    }

}

