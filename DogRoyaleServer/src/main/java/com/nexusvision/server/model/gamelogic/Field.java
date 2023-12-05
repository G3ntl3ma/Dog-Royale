package com.nexusvision.server.model.gamelogic;

import com.nexusvision.server.model.enums.FieldType;

import java.util.ArrayList;
import java.util.Arrays;



public final class Field  {

    final int val; //TODO better name
    final FieldType type;

    Figure figure;

    Field next;
    Field house;
    Field prev;  //these should be final

    public Field(int val, FieldType type) {
        this.val = val;
        this.figure = null;
        this.type = type;
    }

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

    public boolean isEmpty() {
        return this.figure == null;
    }

    public void printval() {
        System.out.println(this.val);
    }

    public void printvals() {
        System.out.println("this val");
        System.out.println(this.val);
        System.out.println("next val");
        this.next.printval();
        System.out.println("prev val");
        this.prev.printval();
    }

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

    public void printtypes() {
        System.out.println("this type");
        System.out.println(this.type);
        System.out.println("next type");
        this.next.printtype();
        System.out.println("prev type");
        this.prev.printtype();
    }

    public void setNext(Field field) {
        this.next = field;
    }

    public void setHouse(Field field) {
        this.house = field;
    }

    public void setPrev(Field field) { //TODO rename to set
        this.prev = field;
    }

    public void setfigure(Figure figure) {
        this.figure = figure;
        figure.field = this;
    }

    public void setEmpty() {
        this.figure = null;
    }


}
