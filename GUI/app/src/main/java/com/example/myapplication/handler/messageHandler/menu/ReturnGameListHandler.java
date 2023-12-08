package com.example.myapplication.handler.messageHandler.menu;

import com.example.myapplication.handler.Handler;
import com.example.myapplication.handler.HandlingException;
import com.example.myapplication.messages.menu.ReturnGameList;

public class ReturnGameListHandler extends Handler implements MenuMessageHandler<ReturnGameList> {
    public String handle(ReturnGameList message) throws HandlingException {
        //handle the starting games TODO StartingGames and save them in the gui
        if (message.getRunningGames() == null){

        }
        return "Games were added successfully to GUI";
    };
}
