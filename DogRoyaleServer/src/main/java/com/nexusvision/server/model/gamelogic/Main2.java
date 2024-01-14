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

        Game game = new Game(conf, figureCount, handCardCount, maxMoves, Penalty.kickFromGame.ordinal());
        game.initDeck();
        game.distributeCards();
        ArrayList<Integer> oldhash = game.hash();

        SaveState savestate = new SaveState(game);
        
        //first move can be null
        Integer winner = null;
        ArrayList<Integer> winners = new ArrayList<>();
        int round = 0;
        while (winner == null) {
            if (round != 0) {
                game.reshuffle();
                game.reInit();
            }
            game.distributeCards();

            while (!game.roundOver() && winner == null) {
                Player curPlayer = game.getCurrentPlayer();
                // System.out.println("gen moves for player " + curPlayer.getPlayerId());
                ArrayList<Move> moves = curPlayer.generateMoves(game); //
                // System.out.println("moves size " + moves.size());
                // System.out.println("available moves");
                for (Move move : moves) {
                    move.printMove();
                }
                if (!moves.isEmpty()) {
                    // System.out.println("moves not empty");
                    Move move = moves.get(0);
                    move.printMove();
                    UndoMove undo = move.execute(game);
                } else {
                    // System.out.println("has no moves so out this round");
                    curPlayer.setOutThisRound();
                }
                // game.printBoard();
                if (game.checkGameOver()) {
                    winners = game.getWinnerOrder();
                    winner = winners.get(0);
                }
            }
            round++;
        }//end of game
        System.out.println("simulated the game");

        savestate.loadState(game);
        System.out.println("game hash " + game.hash() + " old hash " + oldhash);
    }

}
