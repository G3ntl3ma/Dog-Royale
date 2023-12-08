package com.nexusvision.server.model.messages.game;

import com.nexusvision.server.model.messages.AbstractMessage;
import lombok.Data;

/**
 * Server evaluates move
 *
 * @author kellerb
 */
@Data
public class MoveValid extends AbstractMessage {
    private boolean skip;
    private Card card;
//TODO implemntieren Selected Value
    private int pieceId;
    private boolean isStarter;
    private int opponentPieceId;
    private boolean validMove;
}
