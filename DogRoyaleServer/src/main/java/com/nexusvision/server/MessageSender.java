package com.nexusvision.server;

/**
 * General interface that's providing a <code>send</code> method
 *
 * @author felixwr
 */
public interface MessageSender {

    /**
     * Send a json string
     *
     * @return the json string that should be sended
     */
    public String send();
}