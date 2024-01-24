package Dtos.CustomClasses;

public class Field {
    // Field class for drawCardFields and startFields in TypeMenue.returnLobbyConfig
    private int count;
    private int[] positions;

    public Field(int count, int[] positions) {
        this.count = count;
        this.positions = positions;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int[] getPositions() {
        return positions;
    }

    public void setPositions(int[] positions) {
        this.positions = positions;
    }
}
