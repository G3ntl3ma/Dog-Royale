package Enums;

public enum Card {
    card2,
    card3,
    card5,
    card6,
    card8,
    card9, // 5
    card10,
    card12,
    startCard1,
    startCard2,
    plusMinus4, // 10
    oneToSeven,
    magnetCard,
    swapCard,
    copyCard,
    //Card for special usage, just to make it easier for me :)
    nothingCard;

    public static Card ordinal(int card) {
        return Card.values()[card];
    }

    public String getCardPath(){
        return "card_" + ordinal();
    }
}
