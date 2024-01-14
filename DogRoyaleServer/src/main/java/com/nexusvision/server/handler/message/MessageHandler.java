package com.nexusvision.server.handler.message;

import com.nexusvision.server.handler.Handler;
import com.nexusvision.server.handler.HandlingException;
import com.nexusvision.server.model.messages.AbstractMessage;
import com.nexusvision.server.model.messages.menu.TypeMenue;

/**
 * @author felixwr
 */
public abstract class MessageHandler<M extends AbstractMessage> extends Handler {

    /**
     * Handle the received message
     *
     * @param message The deserialized json string that's getting processed
     * @param clientID The clientID specified for the request
     */
    public final void handle(M message, int clientID) throws HandlingException {
        try {
            performHandle(message, clientID);
        } catch (HandlingException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlingException("Exception while handling " + TypeMenue.getType(message.getType()),
                    e, message.getType());
        }
    }

    /**
     * Handle the received message internally
     *
     * @param message The deserialized json string that's getting processed
     * @param clientID The clientID specified for the request
     */
    protected abstract void performHandle(M message, int clientID) throws HandlingException;

    /**
     * Compares <code>expectedID</code> and <code>actualID</code> and throws a <code>HandlingException</code>
     * if they are not the same
     *
     * @param expectedID The expected client ID
     * @param actualID The actual client ID
     * @throws HandlingException If both IDs are not the same
     */
    protected void verifyClientID(int expectedID , int actualID) throws HandlingException {
        if (expectedID  != actualID) {
            throw new HandlingException(
                    "Received wrong client id: Expected " + expectedID  + ", but received " + actualID,
                    TypeMenue.error.getOrdinal());
        }
    }
}
