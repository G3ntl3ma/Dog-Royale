package GUI.app.src.main.java.com.example.myapplication.handler.messageHandler.game;


import GUI.app.src.main.java.com.example.myapplication.handler.Handler;
import GUI.app.src.main.java.com.example.myapplication.handler.HandlingException;
import GUI.app.src.main.java.com.example.myapplication.messages.game.TypeGame;
import com.example.myapplication.messages.game.BoardState;

public class BoardStateHandler extends Handler implements GameMessageHandler<com.example.myapplication.messages.game.BoardState> {


    @Override
    public String handle(BoardState message) throws HandlingException {

            //TODO update the Board accordingly
        if(message.isGameOver()){
            //TODO show winners and leave game change State
        }
        try {
            com.example.myapplication.messages.game.Response response = new com.example.myapplication.messages.game.Response();
            response.setUpdated(true);

            return gson.toJson(response);
        } catch (Exception e) {
            throw new HandlingException("Exception while handling BoardState",
                    e, TypeGame.response.getOrdinal());
        }
    }
}
