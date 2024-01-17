package com.nexusvision.server.model.gamelogic;

import com.nexusvision.server.model.enums.Card;
import com.nexusvision.server.model.enums.FieldType;
import java.util.ArrayList;
import java.lang.Math; 
import lombok.Data;

@Data
public class Node {
    private ArrayList<Node> children;
    private Node parent;
    private int visits;
    private int value;
    private Boolean haschildren;
    private Move move;
    
    public Node(Move move, Node parent) {
        this.move = move;
        this.parent = parent;
        this.visits = 0;
        this.value = 0;
        this.haschildren = false;
    }

    public void expand(Game game) {
        //assert game not over
        if (this.haschildren == true) {
            // System.out.println("already expanded");
            return;
        }
        this.haschildren = true;
        ArrayList<Move> moves = game.getMoves();
        this.children = new ArrayList<>();
        for (Move move : moves) {
            this.children.add(new Node(move, this));
        }
        if (moves.size() == 0) {//if there are no legal moves
            this.children.add(new Node(null, this));
        }
        // System.out.println("expanded with " + this.children.size());
    }

    public void incVisits() {
        this.visits++;
    }

    public void addValue(int value) {
        this.value += value;
    }

    public float getutc() {
        float c = 10; //adjustable factor
        Node curnode = this;
        while (curnode.parent != null) {
            curnode = curnode.parent;
        }
        if (this.visits == 0) {
            return Integer.MAX_VALUE;
        }
        // print("all visits", curnode.visits)
        return (float) ((float)this.value/(float)this.visits + c*Math.sqrt(Math.log(curnode.visits)/this.visits));
    }
}
