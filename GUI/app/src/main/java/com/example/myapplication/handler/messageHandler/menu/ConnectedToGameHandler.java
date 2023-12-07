package GUI.app.src.main.java.com.example.myapplication.handler.messageHandler.menu;

import GUI.app.src.main.java.com.example.myapplication.handler.Handler;
import GUI.app.src.main.java.com.example.myapplication.handler.HandlingException;
import com.example.myapplication.messages.menu.ConnectedToGame;

public class ConnectedToGameHandler extends Handler implements MenuMessageHandler<com.example.myapplication.messages.menu.ConnectedToGame> {
    public String handle(ConnectedToGame message) throws HandlingException {
        if (message.isSuccess()){
            //TODO show on the GUI the waiting for game to start window
            return "Connection to game was successful";
        }
        else{//maybe show message: connection to game failed try again
            return "Connection to game was not successful";
        }
    };
}
