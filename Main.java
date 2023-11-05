import java.util.ArrayList;
import java.util.Arrays;

//TODO deck empty -> combine pile with deck and reshuffle
//TODO if deck.size() < players.size() * handcardnum -> combine pile with deck and reshuffle
//TODO dont allow jumping over figures in house
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

	Game game = new Game();
	game.init(conf, figurecount);
	game.initdeck();

	// game.reshuffledeck();
	game.distributecards();

	while (game.playersremaining > 0) {
	    ArrayList<Zug> moves = new ArrayList<>();
	    game.getcurplayer().genmoves(game, moves);
	    
	    for (int i = 0; i < moves.size(); i++) {
		moves.get(i).print();
	    }

	    if(moves.size() > 0) {
		moves.get(0).execute(game);
	    }
	    else {
		//TODO add handcards to pile if any
		game.playersremaining--;
		game.nextplayer();
	    }
	    game.printboard();
	}

	System.out.println("end of round");

    }
}
