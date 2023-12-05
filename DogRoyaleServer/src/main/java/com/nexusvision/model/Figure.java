// package org.example;

public class Figure  {
    Field field;
    boolean isInBank; //ignore field if isInBank
    boolean isInHouse; //cant be swapped for example
    int color;

    /**
     * Constructor that initializes the figure
     *
     * @param color An Integer representing the color of the figure
     */
    public Figure(int color) {
        this.isInBank = true;
        this.isInHouse = false;
        this.color = color;
    }

}
