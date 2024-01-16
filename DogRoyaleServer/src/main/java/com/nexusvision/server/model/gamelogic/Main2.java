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
        //TODO assert that field of figure is figure of field
        Game game = new Game(conf, figureCount, handCardCount, maxMoves, Penalty.kickFromGame.ordinal());
        game.initDeck();
        game.distributeCards();
        ArrayList<Integer> oldhash = game.hash();
        SaveState savestate = new SaveState(game);
        Ai ai = new Ai(100);
                       
        for (int i = 0; i < 1; i++) {
            System.out.println("Main2 simulate game " + i);
            game.printBoard();
            //first move can be null
            Integer winner = null;
            ArrayList<Integer> winners;
            int round = 0;
            while (winner == null) {
                if (round != 0) {
                    game.reshuffle();
                    game.reInit();
                }
                game.distributeCards();

                while (!game.roundOver() && winner == null) {
                    Player curPlayer = game.getCurrentPlayer();
                    System.out.println("gen moves for player " + curPlayer.getPlayerId());
                    ArrayList<Move> moves = curPlayer.generateMoves(game); //
                    System.out.println("moves size " + moves.size());
                    if (!moves.isEmpty()) {
                        // System.out.println("moves not empty");
                        // Move move = moves.get(0);
                        Move move = ai.getMove(game);
                        System.out.println("ai generated move");
                        move.printMove();
                        UndoMove undo = move.execute(game);
                    } else {
                        System.out.println("has no moves so out this round");
                        curPlayer.setOutThisRound();
                    }
                    if (game.checkGameOver()) {
                        winners = game.getWinnerOrder();
                        winner = winners.get(0);
                    }
                }
                round++;
            }//end of game
            // System.out.println("simulated the game");

            savestate.loadState(game);
            ArrayList<Integer> newhash = game.hash();
            boolean same = true;
            for (int j = 0; j < oldhash.size(); j++) {
                if((int)newhash.get(j) != (int)oldhash.get(j)) {
                    System.out.println(j + ":" + (int)newhash.get(j) + "!=" + (int)oldhash.get(j));
                    System.out.println("hash not same: game hash " + newhash + " old hash " + oldhash);
                    same = false;
                    System.exit(45);
                    break;
                }
            }
            if (same) {
                System.out.println("hash same: game hash " + newhash + " old hash " + oldhash);                
            }
            
        }
        
    }
}
