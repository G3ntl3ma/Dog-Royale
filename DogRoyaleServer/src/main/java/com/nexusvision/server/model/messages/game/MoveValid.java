package com.nexusvision.server.model.messages.game;

import com.nexusvision.server.model.messages.AbstractMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Server evaluates move
 *
 * @author kellerb
 */

@Data
public class MoveValid extends AbstractMessage {
    private boolean skip;
    private int card;
    private int selectedValue;
    private int pieceId;
    private boolean isStarter;
    private int opponentPieceId;
    private boolean validMove;
}
