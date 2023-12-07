package com.nexusvision.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 *  Append a newline character to the end of the JSON representation of an object during serialization.
 *
 * @author felixwr
 */
public class NewLineAppendingSerializer<T> implements JsonSerializer<T> {
    /**
     * Serializes the object using the provided context, appends a newline character and returns it
     *
     * @param src an object to be serialized
     * @param typeOfSrc type of the source object
     * @param context an object providing facilities for serialization
     *
     * @return the serialized JSON String
     */
    @Override
    public JsonElement serialize(T src, Type typeOfSrc, JsonSerializationContext context) {
        JsonElement serialized = context.serialize(src);
        // Füge einen Zeilenumbruch am Ende der serialisierten Daten hinzu
        String jsonString = serialized.getAsJsonPrimitive().getAsString() + "\n";
        return context.serialize(jsonString);
    }
}