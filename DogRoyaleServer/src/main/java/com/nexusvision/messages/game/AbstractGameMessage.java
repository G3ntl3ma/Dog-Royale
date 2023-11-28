package com.nexusvision.messages.game;

import lombok.Data;

/**
 * @author felixwr
 */
@Data
public abstract class AbstractGameMessage {
    protected enum TypeGame {
        liveTimer,
        turnTimer,
        joinObs,
        leaveObs,
        leavePlayer,
        oardState,
        move,
        moveValid,
        response,
        cancel,
        freeze,
        unfreeze,
        drawCards,
        updateDrawCards,
        kick
    }

    protected enum Card {
        card2,
        card3,
        card5,
        card6,
        card8,
        card9,
        card10,
        card12,
        startCard1,
        startCard2,
        plusMinus4,
        oneToSeven,
        magnetCard,
        swapCard,
        copyCard
    }

    protected TypeGame type;
}
