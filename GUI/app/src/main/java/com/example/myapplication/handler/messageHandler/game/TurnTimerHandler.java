package GUI.app.src.main.java.com.example.myapplication.handler.messageHandler.game;

import GUI.app.src.main.java.com.example.myapplication.handler.Handler;
import GUI.app.src.main.java.com.example.myapplication.handler.HandlingException;
import GUI.app.src.main.java.com.example.myapplication.messages.sync.TurnTimer;

public class TurnTimerHandler extends Handler implements GameMessageHandler<TurnTimer> {
    @Override
    public String handle(TurnTimer message) throws HandlingException {
        return null;
    }
}
