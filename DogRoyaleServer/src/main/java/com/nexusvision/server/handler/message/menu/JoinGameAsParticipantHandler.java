package com.nexusvision.server.handler.message.menu;

import com.nexusvision.server.common.ChannelType;
import com.nexusvision.server.controller.GameLobby;
import com.nexusvision.server.controller.MessageBroker;
import com.nexusvision.server.controller.ServerController;
import com.nexusvision.server.handler.HandlingException;
import com.nexusvision.server.handler.message.MessageHandler;
import com.nexusvision.server.model.enums.Colors;
import com.nexusvision.server.model.messages.menu.JoinGameAsPlayer;
import com.nexusvision.server.model.messages.menu.ConnectedToGame;
import com.nexusvision.server.model.messages.menu.TypeMenue;

/**
 * Handles a <code>JoinGameAsPlayer</code> request
 *
 * @author felixwr, aermysh, dgehse
 */
public class JoinGameAsParticipantHandler extends MessageHandler<JoinGameAsPlayer> {

    /**
     * Handles the logic for a client joining a game as a participant.
     * It verifies the client ID, retrieves the associated game lobby, adds the client as a player with a specific color,
     * creates a success response and returns the response
     *
     * @param message An Instance of the <code>JoinGameAsPlayer</code> representing a client's request to join a game as a participant
     * @param clientId An Integer representing the Id of the requesting client
     */
    @Override
    protected void performHandle(JoinGameAsPlayer message, int clientId) throws HandlingException {

        verifyClientID(clientId, message.getClientId());
        ServerController serverController = ServerController.getInstance();

        GameLobby lobby = serverController.getLobbyById(message.getGameId());
        lobby.addPlayer(message.getPlayerName(), message.getClientId()); // TODO: What if player can't join because lobby is full, should maybe receive success = false

        ConnectedToGame connectedToGame = new ConnectedToGame();
        connectedToGame.setType(TypeMenue.connectedToGame.getOrdinal());
        connectedToGame.setSuccess(true);

        String response = gson.toJson(connectedToGame); // TODO: Should maybe return null
        MessageBroker.getInstance().sendMessage(ChannelType.SINGLE, clientId, response);
    }
}
