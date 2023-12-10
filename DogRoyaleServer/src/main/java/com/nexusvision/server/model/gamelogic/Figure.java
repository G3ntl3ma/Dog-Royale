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
    private int ownerId; //owner id

    public Figure(int ownerId) {
        this.isOnBench = true;
        this.isInHouse = false;
        this.ownerId = ownerId;
    }

    public int getFigureId(Game game) {
        for(int figureId = 0; figureId < game.getPlayerList().get(ownerId).getFigureList().size(); figureId++) {
            if(game.getPlayerList().get(ownerId).getFigureList().get(figureId) == this) return figureId;
        }
        return -1;
    }
}
