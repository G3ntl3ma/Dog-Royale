package com.nexusvision.server.model.messages.game;

import lombok.Builder;
import lombok.Data;

/**
 * Server wartet auf Aktion
 *
 * @author kellerb
 */
@Data
@Builder
public class Move extends AbstractGameMessage{
    private boolean skip;
    private Card card;
    private int selectedValue;
    private int pieceId;
    private boolean isStarter;
    private int opponentPieceId;
}
