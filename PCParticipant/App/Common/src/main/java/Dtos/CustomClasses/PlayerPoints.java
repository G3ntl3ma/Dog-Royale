package Dtos.CustomClasses;

public class PlayerPoints  extends PlayerName {
    // Player class for players with name, id and points
    private int points;

    public PlayerPoints(int clientId, String name, int points) {
        super(clientId, name);
        this.points = points;
    }

    public PlayerPoints(){}

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
