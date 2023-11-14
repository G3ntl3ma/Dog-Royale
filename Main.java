
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

//TODO enums for everything
//TODO generate moves only for every unique card type, and only once for a figure in bank
//TODO human input: choose card by type
//TODO improve addstepmove function
//TODO find best datastructures for everything
//TODO if deck.size() < players.size() * handcardnum -> combine pile with deck and reshuffle
//TODO test if move generator is bugfree
//TODO zeit/zug limit

public class Main {
    public static void main(String[] args) {
	//s = start field, n = normal field, k = draw (k)card
        String conf = "snnnnknnnnsnnnnknnnnsnnnnknnnn";
	int figureCount = 4;

	int players = 0;
	for (int i = 0; i < conf.length(); i++) {
	    if (conf.charAt(i) == 's') players++;
	}
	
	int[] wins = new int[players];
	for (int i = 0; i < players; i++) {
	    wins[i] = 0;
	}
	
	int handCardCount = 10;

	long start = System.currentTimeMillis();
	long functime = 0;

	int gamesToPlay = 1000;
	int totalMoves = 0;

	int maxMoves = 10000000;

	int human = -1;
	Scanner reader = new Scanner(System.in); 
	
	for (int _i = 0; _i < gamesToPlay; _i++) {
	    Game game = new Game();
	    game.init(conf, figureCount, handCardCount);
	    game.initDeck();
	    
	    Player winner = null;
	    int round = 0;
	    
	    
	    while (winner == null) {
		if(round != 0) {
		    game.reshuffle();
		    game.reinit();
		}
		game.distributeCards();

		
		while (game.playersRemaining > 0 && winner == null) {
		    long startfunc = System.currentTimeMillis();
				
		    ArrayList<Move> moves = new ArrayList<>();
		    Player curplyer = game.getCurrentPlayer();
		    // System.out.println("gen moves for player " + curplyer.col);
		    curplyer.generateMoves(game, moves); //TODO this is the slowest function by far
		    long endfunc = System.currentTimeMillis();
		    functime += (endfunc - startfunc);   
		    if(moves.size() > 0) {
			// moves.get(0).printcard();
			if(curplyer.color == human) {
			    game.printBoard();
			    ArrayList<Move> humanmoves = new ArrayList<>();
			    System.out.println("Enter a card to use: ");
			    int cardinx = reader.nextInt();
			    System.out.println("Enter a figure to use it on: ");
			    int figinx = reader.nextInt();
			    Figure chosenfigure = curplyer.figures.get(figinx);
			    curplyer.cards.get(cardinx).getMoves(game, chosenfigure, humanmoves, curplyer);
			    for (int ii = 0; ii < humanmoves.size(); ii++) {
				humanmoves.get(ii).printmove();
			    }
			    System.out.println("choose a move: ");
			    int moveinx = reader.nextInt();
			    humanmoves.get(moveinx).printmove();
			    humanmoves.get(moveinx).execute(game);
			}
			else {
			    moves.get(0).execute(game);
			}
		    }
		    else {
			// System.out.println("player " + game.getcurplayer().col + " is out");
			game.discardHandCards();
			game.playersRemaining--;
			game.nextPlayer();
			// System.out.println("player " + game.getcurplayer().col + "s turn");
		    }
		    // game.printboard();
		    winner = game.getWinner();
		    totalMoves++;
		    if (totalMoves >= maxMoves) break;
		}
		if (totalMoves >= maxMoves) break;
		// System.out.println("end of round " + round);
		round++;
	    }
	    if (totalMoves >= maxMoves) break;
	    // System.out.println("winner: " + winner.col);
	    wins[winner.color]++;
	}
	long end = System.currentTimeMillis();
	
	System.out.println("total time " + (double) (end - start)/1000 + "s " + (double) gamesToPlay*1000/(end-start) + "games/s " + (double) totalMoves*1000/(end-start) + " moves/s");
	for (int i = 0; i < players; i++) {
	    System.out.println("player " + i + ": " + wins[i] + " wins");
	}
	System.out.println( "functime/totaltime " + (double) (functime*100/(end - start)) + "%" );
	reader.close();
				    
    }

}
