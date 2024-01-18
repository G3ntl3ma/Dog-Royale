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
        int maxMoves = 100000;

        int players = 0;
        for (int i = 0; i < conf.length(); i++) {
            if (conf.charAt(i) == 's') players++;
        }
        int handCardCount = 10;
        //TODO assert that field of figure is figure of field

        //BUG replaying the same game over and over...
        
        // ArrayList<Integer> oldhash = game.hash();
        // SaveState savestate = new SaveState(game);
        Ai ai = new Ai(10);
        int[] winCounter = new int[players];
        ArrayList<Integer>  winHistory = new ArrayList<>();                        
        for (int i = 0; i < 1; i++) {
            System.out.println("Main2 simulate game " + i);
            Game game = new Game(conf, figureCount, handCardCount, maxMoves, Penalty.kickFromGame.ordinal());
            game.initDeck();
            game.distributeCards();
            //first move can be null
            Integer winner = null;
            ArrayList<Integer> winners;
            int round = 0;
            while (winner == null) {
                //get move
                Move move = game.getRandomMove();
                if(game.getCurrentPlayer().getPlayerId() == 0) {
                    // System.out.println("ai generated move");
                    move = ai.getMove(game);
                }
                if (move != null) {
                    // move.printMove();
                }
                
                game.makeMove(move);
                // game.printBoard();
                if (game.checkGameOver()) {
                    winners = game.getWinnerOrder();
                    winner = winners.get(0);
                }
                game.nextPlayer();
                if (game.roundOver()) {
                    game.reshuffle();
                    game.reInit();
                    game.distributeCards();
                    round++;
                }
            }//end of game
            winCounter[winner]++;
            winHistory.add(winner);
            System.out.println("simulated the game, winner: " + winner);
            
        }
        for (int i = 0; i < players; i++) {
            System.out.println("player " + i + ": " + winCounter[i] + " wins");
        }
        System.out.println("winhistory" + winHistory);
    }
}
