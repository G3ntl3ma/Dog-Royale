package com.example.DogRoyalClient;

/**
 * The Tuple class represents a pair of two double values.
 *
 */
public class Tuple {

    // The two components of the tuple
    public double x;
    public double y;

    /**
     * Constructor for the Tuple class. Initializes the x and y components of the tuple.
     *
     * @param x Value of the x component
     * @param y Value of the y component
     */
    public Tuple(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns a string representation of the tuple.
     *
     * @return String in the format "(x, y)"
     */
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    /**
     * Gets the value of the x component.
     *
     * @return Value of the x component
     */
    public double getX() {
        return x;
    }

    /**
     * Gets the value of the y component.
     *
     * @return Value of the y component
     */
    public double getY() {
        return y;
    }

    /**
     * Sets the value of the x component
     *
     * @param x Value of the x component
     */
    public void setX(double x) {this.x = x;}
    /**
     * Sets the value of the y component
     *
     * @param y Value of the y component
     */
    public void setY(double y) {this.y = y;}

    /**
     * Gets the length of the Vector represented by a Tuple
     *
     * @return Length of the Vector
     */
    public double vek_length()
    {
        return (Math.sqrt(Math.pow(this.getX(),2 ) + Math.pow(this.getY(), 2)));
    }
}
