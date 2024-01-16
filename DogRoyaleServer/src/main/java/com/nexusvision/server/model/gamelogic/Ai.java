package com.nexusvision.server.model.gamelogic;

import com.nexusvision.server.model.enums.Card;
import com.nexusvision.server.model.enums.FieldType;

import java.util.Stack;
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
        
        for (int i = 0; i < this.numberOfSimulations; ++i) {
            // System.out.println("ai simulation " + i);
            Node currentnode = root;
            // System.out.println("visits root node: " + currentnode.getVisits());
            Node bestchild = currentnode;
            int value = 0;
            boolean over = false;
            Integer winner = null;
            ArrayList<Integer> winnerOrder = null;
            while (true) {
                if (game.checkGameOver()) {
                    winnerOrder = game.getWinnerOrder();
                    int rank = winnerOrder.indexOf(currentPlayer.getPlayerId());
                    value = -rank;
                    winner = winnerOrder.get(0);
                    break;
                }
                bestchild = null;
                float bestutc = -Float.MAX_VALUE;
                currentnode.expand(game);
                float utc = bestutc;
                ArrayList<Node> children = currentnode.getChildren();
                // System.out.println("num of children " + children.size());
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
                //TODO
                //if bestchild is null the game is not over but currentplayer is out
                //nodes can have children where move is null (representing the player is out)
                Move move = bestchild.getMove();
                move.execute(game);
                if (currentnode.getVisits() == 0) {
                    break;
                }
            }
            // System.out.println("simulate the game");
            //simulate the game
            while (winner == null) {
                game.reshuffle();
                game.reInit();
                game.distributeCards();
                
                while (!game.roundOver() && winner == null) {
                    Player curPlayer = game.getCurrentPlayer();
                    Move move = game.getRandomMove();
                    // System.out.println("sand");
                    if (move != null) {
                        // move.printMove();
                        move.execute(game);
                    } else {
                        curPlayer.setOutThisRound();
                    }
                    // System.out.println("whicht");
                                        
                    if (game.checkGameOver()) {
                        winnerOrder = game.getWinnerOrder();
                        winner = winnerOrder.get(0);
                    }
                }
                // round++;
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

            System.out.println("ai load the state");
            savestate.loadState(game);
        }
        
        //choose child node of root node with highest val
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


