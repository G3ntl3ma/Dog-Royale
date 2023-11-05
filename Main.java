import java.util.ArrayList;
import java.util.Arrays;

//TODO random move
//TODO test if move generator is bugfree
//TODO rename all variables to english equivalents
//TODO field val is its index

public class Main {
    public static void main(String[] args) {

        String conf = "snnnnknnnnsnnnnknnn";
	int figurecount = 2;

	Game game = new Game();
	game.init(conf, figurecount);
	game.initdeck();
	game.distributecards();

	Player player1 = game.players.get(0);
	Player player2 = game.players.get(1);

	for (int _i = 0; _i < 10; _i++) {
	    ArrayList<Zug> moves = new ArrayList<>();
	    game.getcurplayer().genmoves(game, moves);
	    
	    for (int i = 0; i < moves.size(); i++) {
		moves.get(i).print();
	    }

	    if(moves.size() > 0) {
		moves.get(0).execute(game);
	    }
	    else {
		game.nextplayer();
	    }
	    game.printboard();
	}

    }
}
