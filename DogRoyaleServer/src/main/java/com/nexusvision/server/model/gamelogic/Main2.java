package com.nexusvision.server.model.gamelogic;

import com.nexusvision.server.model.enums.Penalty;

import java.util.ArrayList;
import java.util.Scanner;

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

        long start = System.currentTimeMillis();
        long funcTime = 0;

        int gamesToPlay = 10000;
        int totalMoves = 0;

        int human = -1;
        Game game = new Game(conf, figureCount, handCardCount, maxMoves, Penalty.kickFromGame.ordinal());
        game.initDeck();
        game.distributeCards();
        Player curPlayer = game.getCurrentPlayer();
        ArrayList<Move> moves = curPlayer.generateMoves(game); //
        game.printBoard();
        UndoMove undo = moves.get(0).execute(game);
        game.printBoard();
        undo.execute(game);
        game.printBoard();
        
    }

}
