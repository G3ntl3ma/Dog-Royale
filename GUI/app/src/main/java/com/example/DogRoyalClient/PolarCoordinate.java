package com.example.DogRoyalClient;

/**
 * This class represents a polar coordinate in two-dimensional space.
 */
public class PolarCoordinate {
    // Instance variables
    private int radius;
    private double degree;

    /**
     * Constructor for the PolarCoordinate class.
     *
     * @param radius The radial distance from the origin.
     * @param degree The angle (in degrees) from the positive x-axis.
     */
    public PolarCoordinate(int radius, double degree) {
        this.radius = radius;
        this.degree = degree;
    }

    /**
     * Returns a string representation of the polar coordinate.
     *
     * @return A string in the format "(radius, degree)".
     */
    public String toString() {
        return "(" + radius + ", " + degree + ")";
    }
    public Tuple toTuple() {
        Tuple tuple = new Tuple(radius, degree);
        return tuple;
    }
}
