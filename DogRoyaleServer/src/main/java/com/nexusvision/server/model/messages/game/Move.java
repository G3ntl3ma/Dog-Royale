package com.nexusvision.server.model.messages.game;

import com.nexusvision.server.model.messages.AbstractMessage;
import lombok.Data;

/**
 * Server waiting for an action
 *
 * @author kellerb
 */
@Data
public class Move extends AbstractMessage {
    private boolean skip;
    private int card;
    private int selectedValue;
    private int pieceId;
    private boolean isStarter;
    private int opponentPieceId;
}
