package com.example.DogRoyalClient.handler.messageHandler.menu;

import com.example.DogRoyalClient.handler.HandlingException;
import com.example.DogRoyalClient.handler.NewLineAppendingSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
    String handle(T message) throws HandlingException;
}

