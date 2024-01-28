package com.nexusvision.server.model.gamelogic;

import com.nexusvision.server.model.enums.Colors;
import com.nexusvision.server.model.enums.Penalty;
import com.nexusvision.server.model.utils.*;

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
        //BUG replaying the same game over and over if using savestate...
        
        // ArrayList<Integer> oldhash = game.hash();
        // SaveState savestate = new SaveState(game);
        Ai ai = new Ai(100, 1000);
        int[] winCounter = new int[players];
        ArrayList<Integer>  winHistory = new ArrayList<>();
        List<ColorMapping> colorMap = new ArrayList<>();
        for(int i = 0; i < 3; i++) {
            ColorMapping colorMapping = new ColorMapping();
            colorMapping.setColor(i);
            colorMapping.setClientId(i);
            colorMap.add(colorMapping);
        }

        DrawCardFields drawCardFields = new DrawCardFields();
        StartFields startFields = new StartFields();

        drawCardFields.setCount(2);
        List<Integer> positions = new ArrayList<>();
        positions.add(1);        positions.add(3);
        drawCardFields.setPositions(positions);

        startFields.setCount(3);
        positions = new ArrayList<>();
        positions.add(0);        positions.add(2);        positions.add(4);
        startFields.setPositions(positions);

        LobbyConfig lobbyConfig = new LobbyConfig();
        lobbyConfig.importLobbyConfig( "gameName",
                3,
                10,
                4,
                colorMap,
                drawCardFields,
                startFields,
                10,
                1,
                10,
                0,
                10000,
            1000000);
        lobbyConfig.addPlayer("grigori perelman", 0);
        lobbyConfig.addPlayer("mike", 1);
        lobbyConfig.addPlayer("grigori oxlong", 2);
        Scanner userInput = new Scanner(System.in);
        for (int i = 0; i < 10; i++) {
            System.out.println("Main2 simulate game " + i);
            Game game = new Game(lobbyConfig, 0);
            //Game game = new Game(conf, figureCount, handCardCount, maxMoves, Penalty.kickFromGame.ordinal());
            game.initDeck();
            game.distributeCards();
            //first move can be null
            Integer winner = null;
            ArrayList<Integer> winners;
            int round = 0;
            while (winner == null) {
                game.printBoard();
                //get move
                // System.out.println("----------input----------");
                // String input = userInput.nextLine();
                Move move = game.getRandomMove();
                System.out.println("executing");
                if(move != null)                move.printMove();
                if(game.getCurrentPlayer().getClientId() == 0) {
                    // System.out.println("ai generated move");
                    move = ai.getMove(game, System.currentTimeMillis());
                }
                if (move != null) {
                    // move.printMove();
                }
                
                game.makeMove(move);
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
            game.printBoard();
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
