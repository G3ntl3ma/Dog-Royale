package GUI.app.src.main.java.com.example.myapplication.handler.messageHandler.game;

import GUI.app.src.main.java.com.example.myapplication.handler.Handler;
import GUI.app.src.main.java.com.example.myapplication.handler.HandlingException;
import GUI.app.src.main.java.com.example.myapplication.messages.sync.LiveTimer;


public class LiveTimerHandler extends Handler implements GameMessageHandler<LiveTimer> {
    @Override
    public String handle(LiveTimer message) throws HandlingException {
        //TODO update GUI accordingly using message.getLiveTimer()
        return null;
    }
}
