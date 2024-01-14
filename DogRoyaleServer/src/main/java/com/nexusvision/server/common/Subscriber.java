package com.nexusvision.server.common;

/**
 * A subscriber that is being used to subscribe to channels
 *
 * @author felixwr
 */
public interface Subscriber {
    void sendMessage(String message);
}
