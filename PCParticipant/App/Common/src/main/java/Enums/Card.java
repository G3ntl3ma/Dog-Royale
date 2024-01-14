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
    copyCard;

    public String getCardPath(){
        return "card_" + ordinal();
    }
}
