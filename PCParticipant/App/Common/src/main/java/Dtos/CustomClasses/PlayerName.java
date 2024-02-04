package Dtos.CustomClasses;

public class PlayerName extends Player {
    // Player class for players with name and id
    protected String name;
    public PlayerName(int clientId, String name) {
        super(clientId);
        this.name = name;
    }

    public PlayerName(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
