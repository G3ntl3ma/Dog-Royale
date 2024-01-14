package com.nexusvision.server.model.gamelogic;

import com.nexusvision.server.model.enums.Penalty;

import java.util.ArrayList;
import java.util.Scanner;

//TODO tests to write
//total cards always same
//cant go over figure in house/startfield
//can go over figure startfield if wrong color
//figure right behind start field, player has exactly only the card move 10 fields in game with 1 figure per player
//should yield 1 possible move
//figure in house +-4 fields should yield a move that moves out of house

//TODO bool for every start field that keeps track if occupied
//TODO 2 types of moves not implemented yet
//TODO game time limit
//TODO human input: choose card by type
//TODO improve addstepmove function
//TODO find best datastructures for everything
//TODO if deck.size() < players.size() * handcardnum -> combine pile with deck and reshuffle
//TODO test if move generator is bugfree

/**
 * This class represents the Main, setups the game, iterates through a specified number of games,
 * optional player interaction, prints the game results
 *
 * @author dgehse
 */
public class Main {

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

        int gamesToPlay = 1;
        int totalMoves = 0;

        int human = -1;
        Scanner reader = new Scanner(System.in);
        int[] wins = new int[players];
        for (int _i = 0; _i < gamesToPlay; _i++) {
            Game game = new Game(conf, figureCount, handCardCount, maxMoves, Penalty.kickFromGame.ordinal());
            game.initDeck();

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
                    long startFunc = System.currentTimeMillis();

                    Player curPlayer = game.getCurrentPlayer();
                    System.out.println("gen moves for player " + curPlayer.getPlayerId());
                    ArrayList<Move> moves = curPlayer.generateMoves(game); //
                    long endFunc = System.currentTimeMillis();
                    funcTime += (endFunc - startFunc);
                    System.out.println("moves size " + moves.size());
                    System.out.println("available moves");
                    for (Move move : moves) {
                        move.printMove();
                    }
                    if (!moves.isEmpty()) {
                        System.out.println("moves not empty");
                        if (curPlayer.getPlayerId() == human) {
                            game.printBoard();
                            ArrayList<Move> humanMoves = new ArrayList<>();
                            System.out.println("Enter a card to use: ");
                            int cardInx = reader.nextInt();
                            System.out.println("Enter a figure to use it on: ");
                            int figInx = reader.nextInt();
                            Figure chosenfigure = curPlayer.getFigureList().get(figInx);
                            game.getCardService().setType(curPlayer.getCardList().get(cardInx));
                            game.getCardService().getMoves(game, chosenfigure, humanMoves, curPlayer);
                            for (Move humanMove : humanMoves) {
                                humanMove.printMove();
                            }
                            System.out.println("choose a move: ");
                            int moveInx = reader.nextInt();
                            humanMoves.get(moveInx).printMove();
                            humanMoves.get(moveInx).execute(game);
                        } else { //computer move
                            Move move = moves.get(0);
                            move.printMove();
                            move.execute(game);
                        }
                    } else {
                        System.out.println("has no moves so out this round");
                        curPlayer.setOutThisRound();
                    }
                    game.printBoard();
                    if (game.checkGameOver()) {
                        winners = game.getWinnerOrder();
                        winner = winners.get(0);
                    }
                    totalMoves++;
                }
                if (totalMoves >= maxMoves) break;
                // System.out.println("end of round " + round);
                round++;
            }//end of game

            //print winners in ordre
            // for (int i = 0; i < winners.size(); i++) {
            // 	System.out.println("rank " + i + ":" + winners.get(i).color);
            // }

            assert winner != null;
            wins[winner]++;
        }
        long end = System.currentTimeMillis();

        System.out.println("total time " + (double) (end - start) / 1000 + "s " + (double) gamesToPlay * 1000 / (end - start) + "games/s " + (double) totalMoves * 1000 / (end - start) + " moves/s");
        for (int i = 0; i < players; i++) {
            System.out.println("player " + i + ": " + wins[i] + " wins");
        }
        System.out.println("funcTime/totaltime " + (double) (funcTime * 100 / (end - start)) + "%");
        reader.close();

    }

}
