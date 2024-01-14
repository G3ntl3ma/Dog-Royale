package com.nexusvision.server.common;

import com.nexusvision.server.controller.MessageBroker;

/**
 * Provides a fixed setup to publish messages to a specific channel
 *
 * @author felixwr
 */
public class Publisher {

    private final MessageBroker messageBroker;
    private final ChannelType channelType;
    private final int channel;

    public Publisher(ChannelType channelType, int channel) {
        messageBroker = MessageBroker.getInstance();
        this.channelType = channelType;
        this.channel = channel;
    }

    /**
     * Publishes the message to the fixed channel
     *
     * @param message The message
     */
    public void publish(String message) {
        messageBroker.sendMessage(channelType, channel, message);
    }
}
