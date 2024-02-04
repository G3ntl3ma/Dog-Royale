package com.nexusvision.server.handler.message.menu;

import com.nexusvision.server.common.ChannelType;
import com.nexusvision.server.controller.GameLobby;
import com.nexusvision.server.controller.MessageBroker;
import com.nexusvision.server.controller.ServerController;
import com.nexusvision.server.handler.HandlingException;
import com.nexusvision.server.handler.message.MessageHandler;
import com.nexusvision.server.model.messages.menu.JoinGameAsPlayer;
import com.nexusvision.server.model.messages.menu.ConnectedToGame;
import com.nexusvision.server.model.messages.menu.TypeMenue;
import com.nexusvision.server.service.ConnectedToGameService;

/**
 * Handles a <code>JoinGameAsPlayer</code> request
 *
 * @author felixwr, dgehse
 */
public class JoinGameAsPlayerHandler extends MessageHandler<JoinGameAsPlayer> {

    private static final MessageBroker messageBroker = MessageBroker.getInstance();
    private static final ConnectedToGameService connectedToGameService = new ConnectedToGameService();

    /**
     * Handles the logic for a client joining a game as a participant.
     * It verifies the client ID, retrieves the associated game lobby, adds the client as a player with a specific color,
     * creates a success response and returns the response
     *
     * @param message An Instance of the <code>JoinGameAsPlayer</code> representing a client's request to join a game as a participant
     * @param clientId An Integer representing the id of the requesting client
     */
    @Override
    protected void performHandle(JoinGameAsPlayer message, int clientId) throws HandlingException {

        verifyClientId(clientId, message.getClientId());
        ServerController serverController = ServerController.getInstance();

        GameLobby lobby = serverController.getLobbyById(message.getGameId());
        if (lobby == null) {
            messageBroker.sendMessage(ChannelType.SINGLE, clientId, connectedToGameService.getConnectedToGame(false));
        } else {
            lobby.addPlayer(message.getPlayerName(), message.getClientId());
        }
    }
}
