package GUI.app.src.main.java.com.example.myapplication.handler.messageHandler.game;

import GUI.app.src.main.java.com.example.myapplication.handler.Handler;
import GUI.app.src.main.java.com.example.myapplication.handler.HandlingException;
import GUI.app.src.main.java.com.example.myapplication.messages.sync.JoinObs;

public class JoinObsHandler extends Handler implements GameMessageHandler<JoinObs> {

    @Override
    public String handle(JoinObs message) throws HandlingException {
        return null;
    }
}
