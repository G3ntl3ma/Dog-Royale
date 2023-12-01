package com.nexusvision.server.handler.message;

import com.google.gson.Gson;
import com.nexusvision.messages.menu.ReturnFindTournament;

import java.util.List;

/**
 * General interface that's providing a <code>handle</code> method
 *
 * @author felixwr
 */
public interface MessageHandler<T> {

    Gson gson = new Gson();
    /**
     * Handle the received code
     *
     * @param message The deserialized json string that's getting processed
     * @return The response as a json string
     */
    String handle(T message, int clientID, );

}
