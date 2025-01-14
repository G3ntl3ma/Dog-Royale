package com.nexusvision.server.model.gamelogic;

import com.nexusvision.server.model.enums.Card;
import com.nexusvision.server.model.enums.FieldType;

import java.util.ArrayList;

public class Ai {
    private int numberOfSimulations;

    public Ai(int numberOfSimulations) {
        this.numberOfSimulations = numberOfSimulations;
    }

    public Move getMove(Game game) {
        Node root = new Node(null, null);
        Player currentPlayer = game.getCurrentPlayer();
        SaveState savestate = new SaveState(game);
        System.out.println("getting move from ai");
        ArrayList<Integer> oldhash = game.hash();
        //TODO assert same hash
        for (int i = 0; i < this.numberOfSimulations; ++i) {
            System.out.println("ai simulation " + i);
            Node currentnode = root;
            // System.out.println("visits root node: " + currentnode.getVisits());
            Node bestchild = currentnode;
            int value = 0;
            boolean over = false;
            Integer winner = null;
            ArrayList<Integer> winnerOrder = null;
            while (true) {
                bestchild = null;
                float bestutc = -Float.MAX_VALUE;
                currentnode.setHash(game.hash().hashCode());
                currentnode.expand(game);
                float utc = bestutc;
                ArrayList<Node> children = currentnode.getChildren();
                System.out.println("num of children " + children.size());
                for (Node child : children) {
                    utc = child.getutc();
                    // System.out.println("utc " + utc + "bestutc " + bestutc);
                    if (utc > bestutc) {
                        bestutc = utc;
                        bestchild = child;
                        if (child.getVisits() == 0) {
                            break;
                        }
                    }
                }
                currentnode = bestchild;
                Move move = currentnode.getMove();
                System.out.println("child makemove");
                game.makeMove(move);
                currentnode.setHash(game.hash().hashCode());
                System.out.println("set nextPlayer");
                game.nextPlayer();
                if (game.checkGameOver()) {
                    winnerOrder = game.getWinnerOrder();
                    int rank = winnerOrder.indexOf(currentPlayer.getPlayerId());
                    value = -rank;
                    winner = winnerOrder.get(0);
                    break;
                }
                if (game.roundOver()) {
                    game.reshuffle();
                    game.reInit();
                    game.distributeCards();
                }
                if (currentnode.getVisits() == 0) {
                    break;
                }
                System.out.println("after the 3 checks");
                                
            }
            System.out.println("simulate the game");
            //simulate the game
            int simulations = 0;
            while (winner == null) {
                System.out.println("simulations " + simulations);
                // game.printBoard();
                Move move = game.getRandomMove();
                if(move != null) {
                    move.printMove();
                }
                else {
                    System.out.println("null move");
                }
                System.out.println("rollout makemove");
                game.makeMove(move);
                game.nextPlayer();
                if (game.checkGameOver()) {
                    winnerOrder = game.getWinnerOrder();
                    winner = winnerOrder.get(0);
                }
                if (game.roundOver()) {    
                    game.reshuffle();
                    game.reInit();
                    game.distributeCards();
                }
                simulations++;
            }//end of game
            
            int rank = winnerOrder.indexOf(currentPlayer.getPlayerId());
            value = -rank;
            // System.out.println("value after rolling out the game " + value);

            //backprop
            Node iterchild = bestchild;
            while (iterchild != null) {
                iterchild.incVisits();
                iterchild.addValue(value);
                // visits.append(iterchild.visits);
                iterchild = iterchild.getParent();
            }

            // System.out.println("ai load the state");
            savestate.loadState(game);
            ArrayList<Integer> newhash = game.hash();
            for(int k = 0; k < newhash.size(); k++) {
                if ((int)newhash.get(k) != (int) oldhash.get(k)) {
                    System.out.println("hash not same in ai");
                    System.exit(234);
                }
            }
            
            // game.shuffleUnknown(currentPlayer);
        }
        
        //choose child node of root node with highest val
        savestate.loadState(game);
        int avgval = -Integer.MAX_VALUE;
        Move bestmove = null;
        for (Node child : root.getChildren()) {
            if (child.getVisits() == 0) {
                continue; //avoid div by zero
            }
            int avg = child.getValue() / child.getVisits();
            if (avg > avgval) {
                avgval = avg;
                bestmove = child.getMove();
            }
        }
        return bestmove;
    }

}


