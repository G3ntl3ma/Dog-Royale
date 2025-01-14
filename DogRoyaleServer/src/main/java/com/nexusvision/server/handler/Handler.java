package com.nexusvision.server.handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nexusvision.server.model.messages.menu.Error;
import com.nexusvision.server.model.messages.menu.TypeMenue;
import com.nexusvision.utils.NewLineAppendingSerializer;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Abstract class implementing error handling
 *
 * @author felixwr
 */
@Log4j2
public abstract class Handler {
    protected static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(Object.class, new NewLineAppendingSerializer<>())
            .create();

    /**
     * Handles errors
     *
     * @param errorMessage A String describing the nature of the error that occurred
     * @return a string, which is a JSON representation of an Error object
     */
    protected String handleError(String errorMessage) {
        log.error(errorMessage);
        Error error = new Error();
        error.setType(TypeMenue.error.getOrdinal());
        error.setMessage(errorMessage);
        return gson.toJson(error, Error.class);
    }

    /**
     * Handles errors
     *
     * @param errorMessage A String describing the nature of the error that occurred
     * @param type An Integer representing the type of the error
     * @return a string, which is a JSON representation of an Error object
     */
    protected String handleError(String errorMessage, int type) {
        log.error(errorMessage);
        Error error = new Error();
        error.setType(TypeMenue.error.getOrdinal());
        error.setType(type);
        error.setMessage(errorMessage);
        return gson.toJson(error, Error.class);
    }

    /**
     * Handles errors
     *
     * @param errorMessage A String describing the nature of the error that occurred
     * @param e An instance of the Exception class representing the exception or error that occurred
     * @return a string, which is a JSON representation of an Error object
     */
    protected String handleError(String errorMessage, Exception e) {
        log.error(errorMessage + ": " + e.getMessage());
        Error error = new Error();
        error.setType(TypeMenue.error.getOrdinal());
        error.setMessage(errorMessage);
        return gson.toJson(error, Error.class);
    }

    /**
     * Handles errors
     *
     * @param errorMessage A String describing the nature of the error that occurred
     * @param type An Integer representing the type of the error
     * @param e An instance of the Exception class representing the exception or error that occurred
     * @return a string, which is a JSON representation of an Error object
     */
    protected String handleError(String errorMessage, int type, Exception e) {
        log.error(errorMessage + ": " + e.getMessage());
        Error error = new Error();
        error.setType(TypeMenue.error.getOrdinal());
        error.setType(type);
        error.setMessage(errorMessage);
        return gson.toJson(error, Error.class);
    }
}
