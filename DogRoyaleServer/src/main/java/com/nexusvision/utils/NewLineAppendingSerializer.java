package com.nexusvision.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class NewLineAppendingSerializer<T> implements JsonSerializer<T> {
    @Override
    public JsonElement serialize(T src, Type typeOfSrc, JsonSerializationContext context) {
        JsonElement serialized = context.serialize(src);
        // Füge einen Zeilenumbruch am Ende der serialisierten Daten hinzu
        String jsonString = serialized.getAsJsonPrimitive().getAsString() + "\n";
        return context.serialize(jsonString);
    }
}