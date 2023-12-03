package com.nexusvision.server.model.messages.game;

import lombok.Builder;
import lombok.Data;

/**
 * Server wertet Spielzug aus
 *
 * @author kellerb
 */
@Data
@Builder
public class MoveValid extends AbstractGameMessage{
    private boolean skip;
    private Card card;
//TODO implemntieren Selected Value
    private int pieceId;
    private boolean isStarter;
    private int opponentPieceId;
    private boolean validMove;
}
