package com.nexusvision.server.handler;

import lombok.Getter;

/**
 * Custom <code>HandlingException</code> that is getting thrown
 * whenever there occurs a problem in any of the handler classes
 *
 * @author felixwr
 */
@Getter
public class HandlingException extends Exception {

    private int type;

    /**
     * Constructor initializing an instance of the <code>HandlingException</code> class
     *
     */
    public HandlingException() {
        super("Handling exception occurred");
    }

    /**
     * Constructor initializing an instance of the <code>HandlingException</code> class
     *
     * @param message A string representing a custom error message
     */
    public HandlingException(String message) {
        super(message);
    }

    /**
     * Constructor initializing an instance of the <code>HandlingException</code> class
     *
     * @param message A string representing a custom error message
     * @param cause An object representing the cause of the exception
     */
    public HandlingException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor initializing an instance of the <code>HandlingException</code> class
     *
     * @param type An Integer associated with an exception
     */
    public HandlingException(int type) {
        super("Handling exception occurred");
        this.type = type;
    }

    /**
     * Constructor initializing an instance of the <code>HandlingException</code> class
     *
     * @param message A string representing a custom error message
     * @param type An Integer associated with an exception
     */
    public HandlingException(String message, int type) {
        super(message);
        this.type = type;
    }

    /**
     * Constructor initializing an instance of the <code>HandlingException</code> class
     *
     * @param message A string representing a custom error message
     * @param cause An object representing the cause of the exception
     * @param type An Integer associated with an exception
     */
    public HandlingException(String message, Throwable cause, int type) {
        super(message, cause);
        this.type = type;
    }
}
