package GUI.app.src.main.java.com.example.myapplication.handler.messageHandler.game;

import GUI.app.src.main.java.com.example.myapplication.handler.Handler;
import GUI.app.src.main.java.com.example.myapplication.handler.HandlingException;
import GUI.app.src.main.java.com.example.myapplication.messages.game.TypeGame;
import com.example.myapplication.messages.game.BoardState;

public class FirstBoardStateHandler extends Handler implements GameMessageHandler<com.example.myapplication.messages.game.BoardState>{
    @Override
    public String handle(BoardState message) throws HandlingException {
        // TODO create Game Board
        try {
            com.example.myapplication.messages.game.Response response = new com.example.myapplication.messages.game.Response();
            response.setUpdated(true);

            return gson.toJson(response);
        } catch (Exception e) {
            throw new HandlingException("Exception while handling FirstBoardState",
                    e, TypeGame.response.getOrdinal());
        }

    }
}
