package com.nexusvision.server.model.messages.game;

/**
 * Enum that specifies the card
 *
 * @author felixwr
 */
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
    copyCard;

    /**
     * Maps the correct ordinal value by taking care of the shift
     *
     * @return The correct ordinal value
     */
    public int getOrdinal() {
        return ordinal();
    }

    /**
     * Maps the correct type by taking care of the shift
     *
     * @param ordinal The ordinal that will be mapped to it's type
     * @return The correct type
     */
    public static Card getType(int ordinal) {
        return values()[ordinal];
    }
}