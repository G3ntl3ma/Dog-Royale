package com.nexusvision.server.model.gamelogic;// package org.example;

enum FieldType {
    NORMAL,
    DRAW,
    START,
    HOUSE,
}
/**
 * Creates a Field object
 *
 * @author dgehse
 */
public final class Field  {

    final int val; //TODO better name
    final FieldType type;

    Figure figure;

    Field next;
    Field house;
    Field prev;  //these should be final

    /**
     * Constructor that initializes a Field object
     *
     * @param val //TODO better name
     * @param type An Enum representing the type of the Field
     */
    public Field(int val, FieldType type) {
        this.val = val;
        this.figure = null;
        this.type = type;
    }

    /**
     * Constructor that initializes a Field object
     *
     * @param val //TODO better name
     * @param typeChar A Char representing the type of the Field
     */
    public Field(int val, char typeChar) {
        this.val = val;
        this.figure = null;
        switch (typeChar)
        {
            case 'h':
                this.type = FieldType.HOUSE;
                break;
            case 'k':
                this.type = FieldType.DRAW;
                break;
            case 's':
                this.type = FieldType.START;
                break;
            case 'n':
                this.type = FieldType.NORMAL;
                break;
            default:
                System.out.println("parsing error");
                this.type = FieldType.NORMAL;
                break;
        }
    }

    /**
     * Checks if Field is empty
     *
     * @return A Boolean representing if the Field is empty
     */
    public boolean isEmpty() {
        return this.figure == null;
    }

    /**
     * Prints the value of val //TODO Better Name
     *
     */

    public void printval() {
        System.out.println(this.val);
    }
    /**
     * Prints the val of this, previous and next Field //TODO Better Name
     *
     */

    public void printvals() {
        System.out.println("this val");
        System.out.println(this.val);
        System.out.println("next val");
        this.next.printval();
        System.out.println("prev val");
        this.prev.printval();
    }

    /**
     * Prints the type of the Field
     *
     */

    public void printtype() {

        switch (type) {
            case NORMAL:
                System.out.println("normal");
                break;

            case DRAW:
                System.out.println("draw card");
                break;

            case START:
                System.out.println("start");
                break;

            case HOUSE:
                System.out.println("house");
                break;
        }

    }
    /**
     * Prints the type of this, previous and next Field
     *
     */

    public void printtypes() {
        System.out.println("this type");
        System.out.println(this.type);
        System.out.println("next type");
        this.next.printtype();
        System.out.println("prev type");
        this.prev.printtype();
    }
    /**
     * Setter for next Field
     *
     * @param field An object representing a Field on the Board
     */

    public void setNext(Field field) {
        this.next = field;
    }

    /**
     * Setter for a house field
     *
     * @param field An object representing a Field on the Board
     */


    public void setHouse(Field field) {
        this.house = field;
    }
    /**
     * Setter for previous Field
     *
     * @param field An object representing a Field on the Board
     */

    public void setPrev(Field field) { //TODO rename to set
        this.prev = field;
    }
    /**
     * Setter for previous field
     *
     * @param figure An object representing a figure, which will be assigned to a field
     */

    public void setfigure(Figure figure) {
        this.figure = figure;
        figure.field = this;
    }
    /**
     * Make a field empty
     *
     */

    public void setEmpty() {
        this.figure = null;
    }


}
