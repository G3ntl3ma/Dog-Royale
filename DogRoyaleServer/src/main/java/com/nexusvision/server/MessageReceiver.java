package com.nexusvision.server;

/**
 * General interface that's providing a <code>handle</code> method
 *
 * @author felixwr
 */
public interface MessageReceiver {

    /**
     * Handle the received code
     *
     * @param jsonString The json string that's getting processed
     * @return true, if successful
     */
    public boolean handle(String jsonString);
}
