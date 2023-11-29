package com.nexusvision.messages.game;

import lombok.Data;

/**
 * Server wartet auf Aktion
 *
 * @author kellerb
 */
@Data
public class Move extends AbstractGameMessage{
    private boolean skip;
    private Card card;
    private int selectedValue;
    private int pieceId;
    private boolean isStarter;
    private int opponentPieceId;
}
