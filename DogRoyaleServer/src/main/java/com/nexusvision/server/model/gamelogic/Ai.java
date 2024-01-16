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

    public void findMove(Game game) {
        Node root = new Node(null, null);
        Player currentPlayer = game.getCurrentPlayer();
        for (int i = 0; i < this.numberOfSimulations; ++i) {
            Stack<UndoMove> movestack = new Stack<>();
            Node currentnode = root;
            Node bestchild = currentnode;
            int value = 0;
            boolean over = false;
                        
            while (true) {
                if (game.checkGameOver()) {
                    ArrayList<Integer> winnerOrder = game.getWinnerOrder();
                    int rank = winnerOrder.indexOf(currentPlayer.getPlayerId());
                    value = -rank;
                    over = true;
                    break;
                }
                bestchild = null;
                float bestutc = -Float.MAX_VALUE;
                currentnode.expand(game);
                float utc = bestutc;
                for (Node child : currentnode.getChildren()) {
                    utc = child.getutc();
                    if (utc > bestutc) {
                        bestutc = utc;
                        bestchild = child;
                        if (child.getVisits() == 0) {
                            break;
                        }
                    }
                }

                currentnode = bestchild;
                Move move = bestchild.getMove();
                UndoMove undomove = move.execute(game);
                movestack.push(undomove);
                if (currentnode.getVisits() == 0) {
                    break;
                }
            }
            //over boolean superfluous probably
            while (!game.checkGameOver() && !over) { // checking if the game is over sets next player
                Move move = game.getRandomMove();
                UndoMove undomove = move.execute(game);
                movestack.push(undomove);
            }
            ArrayList<Integer> winnerOrder = game.getWinnerOrder();
            int rank = winnerOrder.indexOf(currentPlayer.getPlayerId());
            value = -rank;

            //backprop
            Node iterchild = bestchild;
            while (iterchild != null) {
                iterchild.incVisits();
                iterchild.addValue(value);
                // visits.append(iterchild.visits);
                iterchild = iterchild.getParent();
            }

            //undo, needs to be compared to loading the root nodes state in terms of speed
            while (movestack.size() > 0) {
                movestack.pop().execute(game);
            }
        }
    }
}


