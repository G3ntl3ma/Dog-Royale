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
    private int clientId; // where the figure belongs to
    private Field field;
    private boolean isOnBench; //ignore field if isInBank
    private boolean isInHouse; //cant be swapped for example
    private int figureId;

    public Figure(int figureId, int clientId) {
        this.isOnBench = true;
        this.isInHouse = false;
        this.figureId = figureId;
        this.clientId = clientId;
    }

    public Player getPlayerObjectByOwner(Game game) {
        int clientId = this.getClientId();
        Player opponent = null;
        for (Player player : game.getPlayerList()) {
            if(player.getClientId() == clientId) {
                opponent = player;
                break;
            }
        }
        return opponent;
    }

    public Field getStartFieldByOwner(Game game){
        return getPlayerObjectByOwner(game).getStartField();
    }

//    public void print() {
//        System.out.print("figure print: id: " + this.figureId + " isOnBench " + this.isOnBench + " isInHouse " + this.isInHouse + " ownerId :" + this.ownerId);
//        if (this.field != null) {
//            System.out.println(" field " + this.field.getFieldId());
//        }
//        else {
//            System.out.println(" field: null");
//        }
//    }

    public int hash() {
        ArrayList<Integer> variables = new ArrayList<>();
        variables.add(isOnBench ? 0 : 1);
        variables.add(isInHouse ? 0 : 1);
        variables.add(field.hash());
        return variables.hashCode();
    }
}
