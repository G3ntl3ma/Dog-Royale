package com.example.myapplication.messages.game;

import lombok.Data;

/**
 * @author felixwr
 */
@Data
public abstract class AbstractGameMessage {
    public enum TypeGame {
        liveTimer,
        turnTimer,
        joinObs,
        leaveObs,
        leavePlayer,
        boardState,
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

    public enum Card {
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
