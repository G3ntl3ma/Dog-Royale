package com.example.myapplication;

/**
 * This class calculates coordinates for game board.
 */
import java.lang.Math;

public class CoordinateCalculator {
    // Instance variables
    private int fieldsize;
    private int playerCount;
    private int figuresPerPlayer;

    /**
     * Constructor for the CoordinateCalculator class.
     *
     * @param fieldsize        The size of the game field.
     * @param playerCount      The number of players.
     * @param figuresPerPlayer The number of figures per player.
     */
    public CoordinateCalculator(int fieldsize, int playerCount, int figuresPerPlayer) {
        this.fieldsize = fieldsize;
        this.playerCount = playerCount;
        this.figuresPerPlayer = figuresPerPlayer;
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
    public int getFiguresPerPlayer() {
        return figuresPerPlayer;
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
    public Tuple[] calculateCoordinates() {
        // Initialize array for coordinates
        Tuple[] coordinates = new Tuple[fieldsize];

        // Calculate angle for each position on the game field
        double r = 0;
        for (int i = 0; i < fieldsize; i++) {
            r += 360.0 / fieldsize;

            // Create PolarCoordinate object
            PolarCoordinate polarCoordinate = new PolarCoordinate(figuresPerPlayer, r);

            // Convert polar coordinates to a string
            Tuple polarCoordinateTuple = polarCoordinate.toTuple();

            // Insert coordinates into the array
            coordinates[i] = polarCoordinateTuple;
        }

        // Return array with calculated coordinates
         return coordinates;
    }
    public Tuple[] calculateCartesicCoordinates() {
        Tuple[] polarCoordinates = new Tuple[fieldsize];
        Tuple[] result = new Tuple[fieldsize];
        polarCoordinates = calculateCoordinates();
        for (int i = 0; i < fieldsize; i++) {
            double radius = polarCoordinates[i].getX();              // get radius of polarcoordinate
            double degree = polarCoordinates[i].getY();              // get degree of polarcoordinate
            double cartesicX = radius * Math.cos(degree);
            double cartesicY = radius * Math.sin(degree);
            Tuple cartesicCoordinates = new Tuple(cartesicX, cartesicY); // insert x and y into tuple
            result[i] = cartesicCoordinates;
        }
        return result;

    }

        public Tuple[] calculateFloatCoordinates() {
            Tuple[]  cartesicCoordinates = new Tuple[fieldsize];
            cartesicCoordinates = calculateCartesicCoordinates();
            Tuple[] floatCoordinates = new Tuple[fieldsize];
            for (int i = 0; i < fieldsize; i++) {
                float x = (float) cartesicCoordinates[i].getX();
                float y = (float) cartesicCoordinates[i].getY();

                Tuple coordinate = new Tuple(x, y);
                floatCoordinates[i] = coordinate;

            }
            return floatCoordinates;
        }

}
