package Dtos.CustomClasses;

import java.util.ArrayList;

public class Field {
    // Field class for drawCardFields and startFields in TypeMenue.returnLobbyConfig
    private int count;
    private ArrayList<Integer> positions;

    public Field(int count, ArrayList<Integer> positions) {
        this.count = count;
        this.positions = positions;
    }

    public Field(){}

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ArrayList<Integer> getPositions() {
        return positions;
    }

    public void setPositions(ArrayList<Integer> positions) {
        this.positions = positions;
    }
}
