package com.nexusvision.server.common;

import lombok.Getter;

/**
 * @author felixwr
 */
@Getter
public class MessageListener implements Subscriber {

    private String lastMessage;

    @Override
    public void sendMessage(String message) {
        lastMessage = message;
    }
}
