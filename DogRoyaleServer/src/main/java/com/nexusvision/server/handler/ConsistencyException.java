package com.nexusvision.server.handler;

/**
 * Gets thrown if there is a consistency issue inside a class
 *
 * @author felixwr
 */
public class ConsistencyException extends Exception {

    /**
     * Creates a new consistency exception
     *
     * @param message The detail message of the consistency issue
     */
    public ConsistencyException(String message) {
        super(message);
    }
}
