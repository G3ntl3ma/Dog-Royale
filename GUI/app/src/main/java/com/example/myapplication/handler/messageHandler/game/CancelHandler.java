package GUI.app.src.main.java.com.example.myapplication.handler.messageHandler.game;

import GUI.app.src.main.java.com.example.myapplication.handler.Handler;
import GUI.app.src.main.java.com.example.myapplication.handler.HandlingException;
import GUI.app.src.main.java.com.example.myapplication.handler.messageHandler.menu.MenuMessageHandler;
import com.example.myapplication.messages.game.Cancel;

public class CancelHandler extends Handler implements MenuMessageHandler<com.example.myapplication.messages.game.Cancel> {

    @Override
    public String handle(Cancel message) throws HandlingException {
        //TODO show winners message.winnerOrder
        //TODO and then request tournament directly
        return null;
    }
}
