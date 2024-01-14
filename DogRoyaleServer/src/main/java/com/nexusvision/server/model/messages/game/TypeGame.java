package com.nexusvision.server.model.messages.game;

/**
 * Enum that specifies the state in the game stage
 *
 * @author felixwr
 */
public enum TypeGame {
    liveTimer,
    turnTimer,
    joinObs,
    leaveObs,
    leavePlayer,
    disconnect,

    boardState,
    move,
    moveValid,
    response,
    cancel,
    freeze,
    unfreeze,
    drawCards,
    updateDrawCards,
    kick,
    requestScores,
    returnScores;

    /**
     * Maps the correct ordinal value by taking care of the shift
     *
     * @return The correct ordinal value
     */
    public int getOrdinal() {
        return ordinal() + 200;
    }

    /**
     * Maps the correct type by taking care of the shift
     *
     * @param ordinal The ordinal that will be mapped to it's type
     * @return The correct type
     */
    public static TypeGame getType(int ordinal) {
        return values()[ordinal - 200];
    }
}
