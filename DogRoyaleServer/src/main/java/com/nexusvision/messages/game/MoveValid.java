package com.nexusvision.messages.game;

import lombok.Data;

/**
 * Server wertet Spielzug aus
 *
 * @author kellerb
 */
@Data
public class MoveValid extends AbstractGameMessage{
    private boolean skip;
    private Card card;
//TODO implemntieren Selected Value
    private int pieceId;
    private boolean isStarter;
    private int opponentPieceId;
    private boolean validMove;
}
