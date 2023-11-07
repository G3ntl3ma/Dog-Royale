import java.util.ArrayList;
import java.util.Arrays;

//TODO dont allow jumping over figures in house
//TODO deck empty -> combine pile with deck and reshuffle
//TODO if deck.size() < players.size() * handcardnum -> combine pile with deck and reshuffle
//TODO random move
//TODO test if move generator is bugfree
//TODO rename all variables to english equivalents
//TODO zeit/zug limit
//TODO detect when the game is over (one player full house)
//TODO determine winner

public class Main {
    public static void main(String[] args) {

        String conf = "snnnnknnnnsnnnnknnn";
	int figurecount = 2;
	int handcardcount = 10;
	
	Game game = new Game();
	game.init(conf, figurecount, handcardcount);
	game.initdeck();

	int round = 1;
	// for (int round = 0; round < 1; round++) {
	    // game.reshuffle();
	    game.distributecards();
	    
	    while (game.playersremaining > 0) {
		ArrayList<Zug> moves = new ArrayList<>();
		game.getcurplayer().genmoves(game, moves);
		
		if(moves.size() > 0) {
		    moves.get(0).print();
		    moves.get(0).execute(game);
		}
		else {
		    game.discardhandcards();
		    game.playersremaining--;
		    game.nextplayer();
		}
		game.printboard();
	    }
	    
	    System.out.println("end of round " + round);
	// }

    }
}
