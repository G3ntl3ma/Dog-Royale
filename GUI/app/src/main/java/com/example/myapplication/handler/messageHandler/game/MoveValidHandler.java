package GUI.app.src.main.java.com.example.myapplication.handler.messageHandler.game;

import GUI.app.src.main.java.com.example.myapplication.handler.Handler;
import GUI.app.src.main.java.com.example.myapplication.handler.HandlingException;
import GUI.app.src.main.java.com.example.myapplication.handler.messageHandler.menu.MenuMessageHandler;
import GUI.app.src.main.java.com.example.myapplication.messages.game.TypeGame;
import com.example.myapplication.messages.game.MoveValid;

public class MoveValidHandler extends Handler implements MenuMessageHandler<com.example.myapplication.messages.game.MoveValid> {

    @Override
    public String handle(MoveValid message) throws HandlingException {
        //TODO just show message invalid move if it isn't
        try {//send Updated
            com.example.myapplication.messages.game.Response response = new com.example.myapplication.messages.game.Response();
            response.setUpdated(true);

            return gson.toJson(response);
        } catch (Exception e) {
            throw new HandlingException("Exception while handling ValidMove",
                    e, TypeGame.response.getOrdinal());
        }
    }
}
