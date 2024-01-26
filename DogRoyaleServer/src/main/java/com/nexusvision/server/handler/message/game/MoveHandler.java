package com.nexusvision.server.handler.message.game;

import com.nexusvision.server.controller.GameLobby;
import com.nexusvision.server.controller.ServerController;
import com.nexusvision.server.handler.HandlingException;
import com.nexusvision.server.handler.message.MessageHandler;
import com.nexusvision.server.model.enums.GameState;
import com.nexusvision.server.model.messages.game.*;

/**
 * Handles messages of type <code>Move</code>
 *
 * @author dgehse, felixwr
 */
//respond with 3.8 (validity of move to all)
//also kind of needs to respond with 3.4 to one client
public class MoveHandler extends MessageHandler<Move> {

    /**
     * Processes and validates a player's move in a game, handling various scenarios
     *
     * @param message  An Object representing a <code>Move</code> including details whether the move is a skip, the card used, the selected value ect.
     * @param clientId An Integer representing the id of the requesting client
     */
    @Override
    protected void performHandle(Move message, int clientId) throws HandlingException {
        ServerController serverController = ServerController.getInstance();
        GameLobby gameLobby = serverController.getLobbyOfPlayer(clientId); //find game corresponding to clientId

        //try the move only if game running and not paused
        if (gameLobby.getGameState() != GameState.IN_PROGRESS || gameLobby.isPaused()) {
            throw new HandlingException("Game is either not in progress or paused",
                    TypeGame.move.getOrdinal());
        }

        gameLobby.tryMove(clientId, message.isSkip(), message.getCard().ordinal(), message.getSelectedValue(), message.getPieceId(),
                message.isStarter(), message.getOpponentPieceId());
    }
}
