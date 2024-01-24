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
    private Integer pieceId;
    private Integer hashcode = null;
    private ArrayList<Integer> hash = null; //TODO if hashcodes different print the hash that was passed and this hash
    
    public Node(Move move, Node parent) {
        this.move = move;
        if (move != null) {
            this.pieceId = move.getPieceId();
        }
        else {
            this.pieceId = null;
        }
        this.parent = parent;
        this.visits = 0;
        this.value = 0;
        this.haschildren = false;
    }

    public void setHash(ArrayList<Integer> hash) {
        if (this.hash == null) {
            this.hash = hash;
        }
        else if (this.hash.hashCode() != hash.hashCode()){
            System.out.println("hashcode inconsistent");
            System.out.println("thishash " + this.hash);
            System.out.println("hash " + hash);
            System.out.println("thispieceId " + this.pieceId);

            if(this.move != null) {
                this.move.printMove(); //probably pieceid issue
            }
            else {
                System.out.println("nullmove");
            }
            System.exit(4232);
        }
    }

    public void expand(Game game) {
        //assert game not over
        if (this.haschildren == true) {
            ArrayList<Move> moves = game.getMoves();
            if(Math.max(1,moves.size()) != children.size()) {
                System.out.println("generated different moves " + moves.size() + "children size " + children.size());
                System.exit(264);
            }
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
