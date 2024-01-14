package com.nexusvision.server.model.gamelogic;

import com.nexusvision.server.model.enums.Penalty;

import java.util.*;

/**
 * This class represents the Main, setups the game, iterates through a specified number of games,
 * optional player interaction, prints the game results
 *
 * @author dgehse
 */
public class Main2 {

    /**
     * Entry point for the program. Setups the game, iterates through a specified number of games,
     * optional player interaction, prints the game results
     *
     * @param args Command-line arguments (Optional).
     */
    public static void main(String[] args) {
        System.out.println("start");

        //s = start field, n = normal field, k = draw (k)carn
        String conf = "snnnnknnnnsnnnnknnnnsnnnnknnnn";
        int figureCount = 4;
        int maxMoves = 10000000;

        int players = 0;
        for (int i = 0; i < conf.length(); i++) {
            if (conf.charAt(i) == 's') players++;
        }
        int handCardCount = 10;

        Stack<Integer> hashstack = new Stack<>();
        Stack<UndoMove> movestack = new Stack<>();
        Game game = new Game(conf, figureCount, handCardCount, maxMoves, Penalty.kickFromGame.ordinal());
        game.initDeck();
        game.distributeCards();
        System.out.println("game hash " + game.hash());

        //TODO
        //play game until over
        //make list of hashes of each state
        //undo all the moves and check hashes
        //print how many hashes match out of game count
        //if everything matches the undo move should be bugfree

        //first move can be null
        Integer winner = null;
        ArrayList<Integer> winners = new ArrayList<>();
        int round = 0;
        while (winner == null) {
            System.out.println("round " + round);
            if (round != 0) {
                game.reshuffle();
                game.reInit();
            }
            game.distributeCards();
            
            while (!game.roundOver() && winner == null) {
                Move move = game.getRandomMove();
                if (move != null) {
                    UndoMove undo = move.execute(game);
                    hashstack.add(game.hash());
                    movestack.add(undo);
                }
                if (game.checkGameOver()) {
                    winners = game.getWinnerOrder();
                    winner = winners.get(0);
                }
            }
        }

        System.out.println("simulated the game");
        
        int movesmade = movestack.size();
        int correct = 0;
        while(movestack.size() > 0) {
            UndoMove undo = movestack.pop();
            int hash = hashstack.pop();
            undo.execute(game);
            if(hash == game.hash()) {
                correct++;
            }
        }
        System.out.println("movesmade " + movesmade + " correct " + correct);
    }

}
