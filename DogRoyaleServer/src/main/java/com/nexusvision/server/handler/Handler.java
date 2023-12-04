package com.nexusvision.server.handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nexusvision.server.model.messages.menu.Error;
import com.nexusvision.server.model.messages.menu.TypeMenue;
import com.nexusvision.utils.NewLineAppendingSerializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class Handler {
    private static final Logger logger = LogManager.getLogger(ClientHandler.class);
    Gson gson = new GsonBuilder()
            .registerTypeAdapter(Object.class, new NewLineAppendingSerializer<>())
            .create();

    protected String handleError(String errorMessage) {
        logger.error(errorMessage);
        Error error = new Error();
        error.setType(TypeMenue.error.getOrdinal());
        error.setMessage(errorMessage);
        return gson.toJson(error, Error.class);
    }

    protected String handleError(String errorMessage, int type) {
        logger.error(errorMessage);
        Error error = new Error();
        error.setType(TypeMenue.error.getOrdinal());
        error.setType(type);
        error.setMessage(errorMessage);
        return gson.toJson(error, Error.class);
    }

    protected String handleError(String errorMessage, Exception e) {
        logger.error(errorMessage + ": " + e.getMessage());
        Error error = new Error();
        error.setType(TypeMenue.error.getOrdinal());
        error.setMessage(errorMessage);
        return gson.toJson(error, Error.class);
    }

    protected String handleError(String errorMessage, int type, Exception e) {
        logger.error(errorMessage + ": " + e.getMessage());
        Error error = new Error();
        error.setType(TypeMenue.error.getOrdinal());
        error.setType(type);
        error.setMessage(errorMessage);
        return gson.toJson(error, Error.class);
    }
}
