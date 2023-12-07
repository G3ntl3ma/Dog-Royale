package GUI.app.src.main.java.com.example.myapplication.handler.messageHandler.menu;

import GUI.app.src.main.java.com.example.myapplication.handler.HandlingException;
import GUI.app.src.main.java.com.example.myapplication.handler.NewLineAppendingSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public interface MenuMessageHandler<T> {

    Gson gson = new GsonBuilder()
            .registerTypeAdapter(Object.class, new NewLineAppendingSerializer<>())
            .create();

    /**
     * Handle the received code
     *
     * @param message The deserialized json string that's getting processed
     * @return The response as a json string
     */
    String handle(T message) throws HandlingException;
}

