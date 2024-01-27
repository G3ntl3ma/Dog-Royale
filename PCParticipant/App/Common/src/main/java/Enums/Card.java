package Enums;

public enum Card {
    card2(0),
    card3(1),
    card5(2),
    card6(3),
    card8(4),
    card9(5), // 5
    card10(6),
    card12(7),
    startCard1(8),
    startCard2(9),
    plusMinus4(19), // 10
    oneToSeven(11),
    magnetCard(12),
    swapCard(13),
    copyCard(14);

    private final int id;

    private Card(int id) {
        this.id = id;
    }

    private int getId() {
        return id;
    }

    public String getCardPath(){
        return "card_" + ordinal();
    }

    public static Card fromId(int id) {
        for (Card type : values()) {
            if (type.getId() == id) {
                return type;
            }
        }
        return null;
    }
}
