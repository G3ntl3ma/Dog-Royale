package Dtos.CustomClasses;

import java.util.ArrayList;

public class PlayerOrder {
    // Player order class for ReturnLobbyConfigDto
    private int type;
    private ArrayList<PlayerName> order;

    public PlayerOrder(int type, ArrayList<PlayerName> order) {
        this.type = type;
        this.order = order;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ArrayList<PlayerName> getOrder() {
        return order;
    }

    public void setOrder(ArrayList<PlayerName> order) {
        this.order = order;
    }
}
