package com.nexusvision.server.handler.message.menuhandler;

import com.google.gson.Gson;

/**
 * General interface that's providing a <code>handle</code> method
 *
 * @author kellerb
 */
public interface GameMessageHandler<T> {

    Gson gson = new Gson();
    /**
     * Handle the received code
     *
     * @param message The deserialized json string that's getting processed
     * @return The response as a json string
     */
    String handle(T message);

}
