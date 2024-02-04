package Dtos.CustomClasses;

public class DiscardedCard {
    // Class for discarded Cards in BoardStateDto
    private int clientId;
    private int card;

    public DiscardedCard(int clientId, int card) {
        this.clientId = clientId;
        this.card = card;
    }

    public DiscardedCard(){}

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getCard() {
        return card;
    }

    public void setCard(int card) {
        this.card = card;
    }
}
