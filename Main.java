import java.util.ArrayList;
import java.util.Arrays;

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
	
	for (int _i = 0; _i < gamestoplay; _i++) {
	    Game game = new Game();
	    game.init(conf, figurecount, handcardcount);
	    game.initdeck();
	    
	    Player winner = null; //dumb
	    int round = 0;
	    
	    
	    while (winner == null) {
		game.reshuffle();
		game.reinit();
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
			moves.get(0).execute(game);
		    }
		    else {
			// System.out.println("player " + game.getcurplayer().col + " is out");
			game.discardhandcards();
			game.playersremaining--; //TODO BUG 
			game.nextplayer();
			// System.out.println("player " + game.getcurplayer().col + "s turn");
		    }
		    // game.printboard();
		    winner = game.getwinner();
		    totalmoves++;
		}
		
		// System.out.println("end of round " + round);
		round++;
	    }
	    
	    // System.out.println("winner: " + winner.col);
	    wins[winner.col]++;
	}
	long end = System.currentTimeMillis();
	
	System.out.println("total time " + (double) (end - start)/1000 + "s " + (double) gamestoplay*1000/(end-start) + "games/s " + (double) totalmoves*1000/(end-start) + " moves/s");
	for (int i = 0; i < players; i++) {
	    System.out.println("player " + i + ": " + wins[i] + " wins");
	}
	System.out.println( "functime/totaltime " + (double) (functime*100/(end - start)) + "%" );
    }

}
