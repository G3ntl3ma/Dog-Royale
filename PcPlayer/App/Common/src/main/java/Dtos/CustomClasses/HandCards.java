package Dtos.CustomClasses;

public class HandCards {
    // Class for cards in hand in UpdateDrawCardsDto
    private int clientId;
    private int count;

    public HandCards(int clientId, int count) {
        this.clientId = clientId;
        this.count = count;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
