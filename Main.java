import java.util.ArrayList;
import java.util.Arrays;

//TODO implement full move generator
//TODO rename all variables to english equivalents
//TODO per player keep track of house fields
//TODO random move
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

	player1.printinfo();

	ArrayList<Zug> moves = new ArrayList<>();
	player1.genmoves(game, moves);

	for (int i = 0; i < moves.size(); i++) {
	    moves.get(i).print();
	}
	
    }
}
