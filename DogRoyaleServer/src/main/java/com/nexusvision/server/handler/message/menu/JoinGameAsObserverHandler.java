package com.nexusvision.server.handler.message.menu;

import com.nexusvision.server.controller.GameLobby;
import com.nexusvision.server.controller.ServerController;
import com.nexusvision.server.handler.HandlingException;
import com.nexusvision.server.handler.message.MessageHandler;
import com.nexusvision.server.model.messages.menu.ConnectedToGame;
import com.nexusvision.server.model.messages.menu.JoinGameAsObserver;
import com.nexusvision.server.model.messages.menu.TypeMenue;

/**
 * Handles a <code>JoinGameAsObserver</code> request
 *
 * @author felixwr
 */
public class JoinGameAsObserverHandler extends MessageHandler<JoinGameAsObserver> {

    /**
     * Handles the logic for a client joining a game as an observer.
     * It verifies the client ID, retrieves the associated game lobby, adds the client as an observer,
     * creates a success response and returns the response
     *
     * @param message An Instance of the <code>JoinGameAsObserver</code> representing a client's request to join a game as an observer
     * @param clientId An Integer representing the id of the requesting client
     */
    @Override
    protected void performHandle(JoinGameAsObserver message, int clientId) throws HandlingException {

        verifyClientID(clientId, message.getClientId());
        ServerController serverController = ServerController.getInstance();

        GameLobby lobby = serverController.getLobbyById(message.getGameId());
        lobby.addObserver(message.getClientId());
    }
}
