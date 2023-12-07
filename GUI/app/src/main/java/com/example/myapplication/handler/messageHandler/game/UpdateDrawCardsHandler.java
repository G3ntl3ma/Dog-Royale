package GUI.app.src.main.java.com.example.myapplication.handler.messageHandler.game;

import GUI.app.src.main.java.com.example.myapplication.handler.Handler;
import GUI.app.src.main.java.com.example.myapplication.handler.HandlingException;
import GUI.app.src.main.java.com.example.myapplication.handler.messageHandler.menu.MenuMessageHandler;
import GUI.app.src.main.java.com.example.myapplication.messages.game.TypeGame;
import com.example.myapplication.messages.game.Response;
import com.example.myapplication.messages.game.UpdateDrawCards;

public class UpdateDrawCardsHandler extends Handler implements MenuMessageHandler<com.example.myapplication.messages.game.UpdateDrawCards> {

    @Override
    public String handle(UpdateDrawCards message) throws HandlingException {
        //TODO update GUI
        try {
            com.example.myapplication.messages.game.Response response = new Response();
            response.setUpdated(true);

            return gson.toJson(response);
        } catch (Exception e) {
            throw new HandlingException("Exception while handling UpdateDrawCards",
                    e, TypeGame.response.getOrdinal());
        }


    }
}
