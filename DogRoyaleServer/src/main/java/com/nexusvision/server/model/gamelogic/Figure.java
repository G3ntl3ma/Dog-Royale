package com.nexusvision.server.model.gamelogic;

import lombok.Data;

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
    private int color;

    public Figure(int color) {
        this.isOnBench = true;
        this.isInHouse = false;
        this.color = color;
    }

}
