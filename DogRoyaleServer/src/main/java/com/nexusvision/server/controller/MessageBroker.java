package com.nexusvision.server.controller;

import com.nexusvision.server.common.ChannelType;
import com.nexusvision.server.common.Subscriber;
import lombok.Getter;

import java.util.*;

/**
 * The message broker provides the functionality to propagate messages to subscriber by
 * registering and unregistering them
 *
 * @author felixwr
 */
public class MessageBroker {

    private static MessageBroker instance;

    private final Hashtable<Integer, List<Subscriber>> lobbySubLists;
    private final Hashtable<Integer, Subscriber> subIdentifier;

    private MessageBroker() {
        lobbySubLists = new Hashtable<>();
        subIdentifier = new Hashtable<>();
    }

    public static MessageBroker getInstance() {
        if (instance == null) {
            instance = new MessageBroker();
        }
        return instance;
    }

    /**
     * Sends a message to all subscribers of the channel
     *
     * @param channelType Indicating the type of the channel
     * @param channel The channel that will be used to propagate the message
     * @param message The message to send
     */
    public void sendMessage(ChannelType channelType, int channel, String message) {
        switch (channelType) {
            case SINGLE:
                Subscriber subscriber = subIdentifier.get(channel);
                subscriber.sendMessage(message);
                break;
            case LOBBY:
                List<Subscriber> subList = lobbySubLists.get(channel);
                for (Subscriber sub : subList) {
                    sub.sendMessage(message);
                }
                break;
        }
    }

    /**
     * Registers a subscriber to a channel by using his id
     *
     * @param subscriberId The id of the subscriber that is getting registered
     * @param channel The channel the subscriber registers to
     */
    public void registerSubscriber(int subscriberId, int channel) {
        List<Subscriber> subList;
        if (lobbySubLists.containsKey(channel)) {
            subList = lobbySubLists.get(channel);
        } else {
            subList = new ArrayList<>();
            lobbySubLists.put(channel, subList);
        }
        Subscriber subscriber = subIdentifier.get(subscriberId);
        subList.add(subscriber);
    }

    /**
     * Unregisters a subscriber from a channel by using his id
     *
     * @param subscriberId The id of the subscriber that is getting unregistered
     * @param channel The channel the subscriber unregisters from
     */
    public void unregisterSubscriber(int subscriberId, int channel) {
        List<Subscriber> subList = lobbySubLists.get(channel);
        Subscriber sub = subIdentifier.get(subscriberId);
        removeSubscriberFromSubList(sub, subList, channel);
    }

    /**
     * Adds an identifier for a subscriber which is necessary to register and unregister the subscriber
     *
     * @param id The identifier
     * @param subscriber The subscriber
     */
    public void addIdentifier(int id, Subscriber subscriber) {
        subIdentifier.put(id, subscriber);
    }

    /**
     * Deletes the subscriber with id <code>subscriberId</code> completely
     *
     * @param subscriberId The id of the subscriber to get deleted
     */
    public void deleteSubscriber(int subscriberId) {
        Subscriber targetSubscriber = subIdentifier.get(subscriberId);
        for (Map.Entry<Integer, List<Subscriber>> entry : lobbySubLists.entrySet()) {
            int channel = entry.getKey();
            List<Subscriber> subList = entry.getValue();
            deleteSubFromSubList(channel, subList, targetSubscriber);
        }
        subIdentifier.remove(subscriberId);
    }

    private void deleteSubFromSubList(int channel, List<Subscriber> subList, Subscriber targetSubscriber) {
        Iterator<Subscriber> iterator = subList.iterator();
        while (iterator.hasNext()) {
            Subscriber sub = iterator.next();
            if (sub == targetSubscriber) {
                iterator.remove(); // Remove the current element via iterator
                if (subList.isEmpty()) {
                    lobbySubLists.remove(channel);
                }
            }
        }
    }

    private void removeSubscriberFromSubList(Subscriber sub, List<Subscriber> subList, int channel) {
        subList.remove(sub);
        if (subList.isEmpty()) {
            lobbySubLists.remove(channel);
        }
    }
}
