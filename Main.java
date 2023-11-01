import java.util.ArrayList;
import java.util.Arrays;

//TODO implement move generator
//TODO random move
//TODO index fields enough instead of next and prev?
//TODO field val is its index

//TODO check if can move in house 
//check if move + card >= max and move + card < max + figcount
//

//TODO fix move signature?? not sure

public class Main {
    public static void main(String[] args) {

        String conf = "snnnnknnnnsnnnnknnn";
	int figurecount = 2;

	Game game = new Game();
	game.init(conf, figurecount);

	System.out.println("");
	game.printboard();

	//tests
	Player player = game.players.get(0);
	Field none = new Field(100);
	Zug z = new Zug(player, none, none, /*start*/ true, /*swap*/ false);
	z.execute(game);
	System.out.println("");
	game.printboard();

	
	Player player2 = game.players.get(1);
	Zug z2 = new Zug(player2, none, none, /*start*/ true, /*swap*/ false);
	z2.execute(game);
	System.out.println("");
	game.printboard();

	Zug z3 = new Zug(player, player.startfield, player2.startfield, /*start*/ false, /*swap*/ false);
	z3.execute(game);
	System.out.println("");
	game.printboard();

	
	z2.execute(game);
	System.out.println("");
	game.printboard();

	Zug karte = new Zug(player2, player2.startfield, game.board.get(15),/*start*/ false, /*swap*/ false);
	karte.execute(game);
	System.out.println("");
	game.printboard();

	z.execute(game);
	System.out.println("");
	game.printboard();

	Zug swap = new Zug(player, player.startfield, game.board.get(15), /*start*/ false, /*swap*/ true);
	swap.execute(game);
	System.out.println("");
	game.printboard();


	Zug house = new Zug(player, game.board.get(15), game.board.get(22), /*start*/ false, /*swap*/ false);
	house.execute(game);
	System.out.println("");
	game.printboard();
    }
}
