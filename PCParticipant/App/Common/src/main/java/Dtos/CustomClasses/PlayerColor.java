package Dtos.CustomClasses;

public class PlayerColor extends Player {
    // Player class for Players with name and color
    private int color;

    public PlayerColor(int clientId, int color) {
        super(clientId);
        this.color = color;
    }

    public  PlayerColor(){}

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
