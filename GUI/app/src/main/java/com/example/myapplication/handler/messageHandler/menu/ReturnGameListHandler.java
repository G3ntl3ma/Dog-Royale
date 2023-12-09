package com.example.myapplication.handler.messageHandler.menu;

import com.example.myapplication.controller.Games;
import com.example.myapplication.handler.Handler;
import com.example.myapplication.handler.HandlingException;
import com.example.myapplication.messages.menu.ReturnGameList;

public class ReturnGameListHandler extends Handler implements MenuMessageHandler<ReturnGameList> {
    /**
     * saves the Games sent in message in controller.Games
     * @param message The deserialized json string that's getting processed
     * @return null since no request has to be sent back
     * @throws HandlingException
     */
    public String handle(ReturnGameList message) throws HandlingException {

        Games.startingGames = message.getStartingGames();
        Games.runningGames = message.getRunningGames();
        Games.finishedGames = message.getFinishedGames();

        return null;
    };
}
