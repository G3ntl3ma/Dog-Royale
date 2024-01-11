package com.nexusvision.server.model.gamelogic;

import com.nexusvision.server.model.enums.FieldType;
import lombok.Data;

/**
 * Creates a Field object
 *
 * @author dgehse
 */
@Data
public final class Field {

    private final int fieldId;
    private final FieldType type;

    private Figure figure;
    private Field next;
    private Field house;
    private Field prev;  //TODO rename to set

    public Field(int fieldId, FieldType type) {
        this.fieldId = fieldId;
        this.figure = null;
        this.type = type;
    }

    /**
     * Constructor that initializes a Field object
     *
     * @param val      //TODO better name
     * @param typeChar A Char representing the type of the Field
     */
    public Field(int val, char typeChar) {
        this.fieldId = val;
        this.figure = null;
        switch (typeChar) {
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
     */
    public void printVal() {
        System.out.println(this.fieldId);
    }

    /**
     * Prints the val of this, previous and next Field //TODO Better Name
     */
    public void printVals() {
        System.out.println("this val");
        System.out.println(this.fieldId);
        System.out.println("next val");
        this.next.printVal();
        System.out.println("prev val");
        this.prev.printVal();
    }

    /**
     * Prints the type of the Field
     */
    public void printType() {

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
     */
    public void printTypes() {
        System.out.println("this type");
        System.out.println(this.type);
        System.out.println("next type");
        this.next.printType();
        System.out.println("prev type");
        this.prev.printType();
    }

    /**
     * Setter for figure
     *
     * @param figure An object representing a figure, which will be assigned to a field
     */
    public void setFigure(Figure figure) {
        this.figure = figure;
        if (figure != null) {
            figure.setField(this);
        }
    }

    /**
     * Make a field empty
     */
    public void setEmpty() {
        this.figure = null;
    }


    public void setNext(Field next) {
        this.next = next;
    }

    public void setHouse(Field house) {
        this.house = house;
    }

}
