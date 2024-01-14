package Dtos.CustomClasses;

public abstract class Player {
    // abstarct Class Player, wich includes attributes and functions, that all inheritors have in common
    protected int clientId;

    public Player(int clientId) {
        this.clientId = clientId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }
}