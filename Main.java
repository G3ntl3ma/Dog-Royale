import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

//TODO enums for everything
//TODO its actually not necessary to generate all moves because most do literally the same thing
//TODO generate moves only for every unique card type
//TODO choose card by type
//TODO human class?
//TODO improve addstepmove function
//TODO find best datastructures for everything
//TODO if deck.size() < players.size() * handcardnum -> combine pile with deck and reshuffle
//TODO test if move generator is bugfree
//TODO rename all variables to english equivalents
//TODO zeit/zug limit

public class Main {
    public static void main(String[] args) {

        String conf = "snnnnknnnnsnnnnknnnnsnnnnknnnn";
	int figurecount = 4;

	int players = 0;
	for (int i = 0; i < conf.length(); i++) {
	    if (conf.charAt(i) == 's') players++;
	}
	
	int[] wins = new int[players];
	for (int i = 0; i < players; i++) {
	    wins[i] = 0;
	}
	
	int handcardcount = 10;

	long start = System.currentTimeMillis();
	long functime = 0;

	int gamestoplay = 10000;
	int totalmoves = 0;

	int maxmoves = 10000000;

	int human = 0;
	Scanner reader = new Scanner(System.in); 
	
	for (int _i = 0; _i < gamestoplay; _i++) {
	    Game game = new Game();
	    game.init(conf, figurecount, handcardcount);
	    game.initdeck();
	    
	    Player winner = null;
	    int round = 0;
	    
	    
	    while (winner == null) {
		if(round != 0) {
		    game.reshuffle();
		    game.reinit();
		}
		game.distributecards();

		
		while (game.playersremaining > 0 && winner == null) {
		    long startfunc = System.currentTimeMillis();
				
		    ArrayList<Zug> moves = new ArrayList<>();
		    Player curplyer = game.getcurplayer();
		    // System.out.println("gen moves for player " + curplyer.col);
		    curplyer.genmoves(game, moves); //TODO this is the slowest function by far
		    long endfunc = System.currentTimeMillis();
		    functime += (endfunc - startfunc);   
		    if(moves.size() > 0) {
			// moves.get(0).printcard();
			if(curplyer.col == human) {
			    game.printboard();
			    ArrayList<Zug> humanmoves = new ArrayList<>();
			    System.out.println("Enter a card to use: ");
			    int cardinx = reader.nextInt();
			    System.out.println("Enter a figure to use it on: ");
			    int figinx = reader.nextInt();
			    Figure chosenfigure = curplyer.figures.get(figinx);
			    curplyer.cards.get(cardinx).getmoves(game, chosenfigure, humanmoves, curplyer);
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
			game.discardhandcards();
			game.playersremaining--;
			game.nextplayer();
			// System.out.println("player " + game.getcurplayer().col + "s turn");
		    }
		    // game.printboard();
		    winner = game.getwinner();
		    totalmoves++;
		    if (totalmoves >= maxmoves) break;
		}
		if (totalmoves >= maxmoves) break;
		// System.out.println("end of round " + round);
		round++;
	    }
	    if (totalmoves >= maxmoves) break;
	    // System.out.println("winner: " + winner.col);
	    wins[winner.col]++;
	}
	long end = System.currentTimeMillis();
	
	System.out.println("total time " + (double) (end - start)/1000 + "s " + (double) gamestoplay*1000/(end-start) + "games/s " + (double) totalmoves*1000/(end-start) + " moves/s");
	for (int i = 0; i < players; i++) {
	    System.out.println("player " + i + ": " + wins[i] + " wins");
	}
	System.out.println( "functime/totaltime " + (double) (functime*100/(end - start)) + "%" );
	reader.close();
				    
    }

}
