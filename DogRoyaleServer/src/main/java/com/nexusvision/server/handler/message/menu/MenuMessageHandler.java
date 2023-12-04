package com.nexusvision.server.handler.message.menu;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nexusvision.server.handler.Handler;
import com.nexusvision.server.handler.HandlingException;
import com.nexusvision.utils.NewLineAppendingSerializer;

/**
 * General interface that's providing a <code>handle</code> method
 *
 * @author felixwr
 */
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
    String handle(T message, int clientID) throws HandlingException;
}
