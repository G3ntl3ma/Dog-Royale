package com.example.myapplication;

/**
 * This class calculates coordinates for game board.
 */
import java.lang.Math;

public class CoordinateCalculator {
    // Instance variables
    private int fieldsize;
    private int playerCount;
    private int radius;

    /**
     * Constructor for the CoordinateCalculator class.
     *
     * @param fieldsize        The size of the game field.
     * @param playerCount      The number of players.
     * @param radius The number of figures per player.
     */
    public CoordinateCalculator(int fieldsize, int playerCount, int radius) {
        this.fieldsize = fieldsize;
        this.playerCount = playerCount;
        this.radius = radius;
    }

    /**
     * Getter method for the game field size.
     *
     * @return The size of the game field.
     */
    public int getFieldsize() {
        return fieldsize;
    }

    /**
     * Getter method for the number of figures per player.
     *
     * @return The number of figures per player.
     */
    public int getradius() {
        return radius;
    }

    /**
     * Getter method for the number of players.
     *
     * @return The number of players.
     */
    public int getPlayerCount() {
        return playerCount;
    }

    /**
     * Calculates the polar coordinates for each field on the game board.
     *
     * @return An array of strings representing the calculated polar coordinates.
     */
    public Tuple calculateCoordinates(int i) {
        // Initialize array for coordinates
        Tuple coordinates = new Tuple(0,0);

        // Calculate angle for each position on the game field
        double r = i * 360.0 / fieldsize;

            // Create PolarCoordinate object
            PolarCoordinate polarCoordinate = new PolarCoordinate(radius, r);

            // Convert polar coordinates to a string
            Tuple polarCoordinateTuple = polarCoordinate.toTuple();

            // Insert coordinates into the array
            coordinates = polarCoordinateTuple;


        // Return array with calculated coordinates
         return coordinates;
    }
    public Tuple calculateCartesicCoordinates(int i) {
        Tuple polarCoordinates = new Tuple(0,0);
        polarCoordinates = calculateCoordinates(i);
            double radius = polarCoordinates.getX();              // get radius of polarcoordinate
            double degree = polarCoordinates.getY();              // get degree of polarcoordinate
            double cartesicX = radius * Math.cos(degree);
            double cartesicY = radius * Math.sin(degree);
            Tuple cartesicCoordinates = new Tuple(cartesicX, cartesicY); // insert x and y into tuple

        return cartesicCoordinates;

    }

    /*
    i = wievielte spielfeldx
     */
        public Tuple calculateFloatCoordinates(int i) {
            Tuple  cartesicCoordinates = new Tuple(0,0);
            cartesicCoordinates = calculateCartesicCoordinates(i);
            Tuple floatCoordinates = new Tuple(0,0);
                float x = (float) cartesicCoordinates.getX();
                float y = (float) cartesicCoordinates.getY();

                Tuple coordinate = new Tuple(x, y);
                floatCoordinates = coordinate;


            return floatCoordinates;
        }

}
