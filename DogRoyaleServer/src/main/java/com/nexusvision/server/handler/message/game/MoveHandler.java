package com.nexusvision.server.handler.message.game;

import com.nexusvision.server.controller.GameLobby;
import com.nexusvision.server.controller.ServerController;
import com.nexusvision.server.handler.message.MessageHandler;
import com.nexusvision.server.model.enums.Card;
import com.nexusvision.server.model.enums.GameState;
import com.nexusvision.server.model.enums.Penalty;
import com.nexusvision.server.model.messages.game.*;

import java.util.ArrayList;
import java.util.Arrays;

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
     * @return An errormessage if an error occurs or null
     */
    @Override
    protected String performHandle(Move message, int clientId) {
        ServerController serverController = ServerController.getInstance();
        GameLobby gameLobby = serverController.getGameOfPlayer(clientId); //find game corresponding to clientId

        //try move only if game running and not paused
        if (gameLobby.getGameState() != GameState.IN_PROGRESS || gameLobby.isPaused()) {
            return handleError("Game is either not in progress or paused", TypeGame.move.getOrdinal());
        }

        gameLobby.tryMove(clientId, message.isSkip(), message.getCard().ordinal(), message.getSelectedValue(), message.getPieceId(),
                message.isStarter(), message.getOpponentPieceId());

        return null;
    }
}
