package com.nexusvision.server.model.gamelogic;
public class Figure  {
    Field field;
    boolean isInBank; //ignore field if isInBank
    boolean isInHouse; //cant be swapped for example
    int color;

    public Figure(int color) {
        this.isInBank = true;
        this.isInHouse = false;
        this.color = color;
    }

}
