package GUI.app.src.main.java.com.example.myapplication.handler.messageHandler.menu;

import GUI.app.src.main.java.com.example.myapplication.handler.Handler;
import GUI.app.src.main.java.com.example.myapplication.handler.HandlingException;

public class ReturnGameListHandler extends Handler implements MenuMessageHandler<com.example.myapplication.messages.menu.ReturnGameList> {
    public String handle(com.example.myapplication.messages.menu.ReturnGameList message) throws HandlingException {
        //handle the starting games TODO StartingGames and save them in the gui
        if (message.getRunningGames() == null){

        }
        return "Games were added successfully to GUI";
    };
}
