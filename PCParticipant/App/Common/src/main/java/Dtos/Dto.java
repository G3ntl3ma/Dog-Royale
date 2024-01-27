package Dtos;

public abstract class Dto {
    protected final int type;

    protected Dto(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
