import java.util.ArrayList;
import java.util.Arrays;

//TODO if deck.size() < players.size() * handcardnum -> combine pile with deck and reshuffle
//TODO random move
//TODO test if move generator is bugfree
//TODO rename all variables to english equivalents
//TODO zeit/zug limit

public class Main {
    public static void main(String[] args) {

        String conf = "snnnnknnnnsnnnnknnnnsnnnnknnnn";
	int figurecount = 4;
	int handcardcount = 10;
	
	for (int _i = 0; _i < 1000; _i++) {
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
		    ArrayList<Zug> moves = new ArrayList<>();
		    Player curplyer = game.getcurplayer();
		    // System.out.println("gen moves for player " + curplyer.col);
		    curplyer.genmoves(game, moves);
		    
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
		}
		
		// System.out.println("end of round " + round);
		round++;
	    }
	    
	    System.out.println("winner: " + winner.col);
	}
    }

}
