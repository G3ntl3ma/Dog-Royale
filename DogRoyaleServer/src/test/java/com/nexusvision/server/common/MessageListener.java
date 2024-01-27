package com.nexusvision.server.common;

import lombok.Getter;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author felixwr
 */
public class MessageListener implements Subscriber {

    private final Queue<String> messageQueue = new LinkedList<>();

    @Override
    public void sendMessage(String message) {
        messageQueue.add(message);
    }

    public String retrieveMessage() {
        return messageQueue.remove();
    }

    public void clearMessages() {
        messageQueue.clear();
    }
}
