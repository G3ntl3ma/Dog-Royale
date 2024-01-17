package com.nexusvision.server.model.gamelogic;

import lombok.Data;

import java.util.ArrayList;

/**
 * Creates a Figure object
 *
 * @author dgehse
 */
@Data
public class Figure  {
    private Field field;
    private boolean isOnBench; //ignore field if isInBank
    private boolean isInHouse; //cant be swapped for example
    private int ownerId; //owner id
    private int figureId;

    public Figure(int ownerId, int figureId) {
        this.isOnBench = true;
        this.isInHouse = false;
        this.ownerId = ownerId;
        this.figureId = figureId;
    }
    
    public Figure(Field field, boolean isOnBench, boolean isInHouse, int ownerId) {
        this.field = field;
        this.isOnBench = isOnBench;
        this.isInHouse = isInHouse;
        this.ownerId = ownerId;
    }

    public void print() {
        System.out.print("figure print: id: " + this.figureId + " isOnBench " + this.isOnBench + " isInHouse " + this.isInHouse + " ownerId :" + this.ownerId);
        if (this.field != null) {
            System.out.println(" field " + this.field.getFieldId());
        }
        else {
            System.out.println(" field: null");
        }
    }

    public int hash() {
        ArrayList<Integer> variables = new ArrayList<>();
        variables.add(isOnBench ? 0 : 1);
        variables.add(isInHouse ? 0 : 1);
        variables.add(field.hash());
        return variables.hashCode();
    }
}
