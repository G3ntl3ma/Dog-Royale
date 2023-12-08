package com.example.myapplication.handler;

import lombok.Getter;

/**
 * Custom HandlingException that is getting thrown
 * whenever there occurs a problem in any of the handler classes
 *
 * @author felixwr
 */
public class HandlingException extends Exception {

    @Getter
    private int type;

    public HandlingException() {
        super("Handling exception occurred");
    }

    public HandlingException(String message) {
        super(message);
    }

    public HandlingException(String message, Throwable cause) {
        super(message, cause);
    }

    public HandlingException(int type) {
        super("Handling exception occurred");
        this.type = type;
    }

    public HandlingException(String message, int type) {
        super(message);
        this.type = type;
    }

    public HandlingException(String message, Throwable cause, int type) {
        super(message, cause);
        this.type = type;
    }
}
