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
     * @param clientId The clientId specified for the request
     */
    public final void handle(M message, int clientId) throws HandlingException {
        try {
            performHandle(message, clientId);
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
     * @param clientId The clientId specified for the request
     */
    protected abstract void performHandle(M message, int clientId) throws HandlingException;

    /**
     * Compares <code>expectedId</code> and <code>actualId</code> and throws a <code>HandlingException</code>
     * if they are not the same
     *
     * @param expectedId The expected client ID
     * @param actualId The actual client ID
     * @throws HandlingException If both IDs are not the same
     */
    protected void verifyClientId(int expectedId , int actualId) throws HandlingException {
        if (expectedId  != actualId) {
            throw new HandlingException(
                    "Received wrong client id: Expected " + expectedId  + ", but received " + actualId,
                    TypeMenue.error.getOrdinal());
        }
    }
}
