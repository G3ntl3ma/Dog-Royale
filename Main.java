import java.util.ArrayList;
import java.util.Arrays;


//TODO field method: get all fields between current and target
//TODO generate all moves a card(/*arg*/ figure) can produce 
//TODO per player keep track of house fields
//TODO implement full move generator
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
	Zug z = new Zug(player);
	z.execute(game);
	System.out.println("");
	game.printboard();

	
	Player player2 = game.players.get(1);
	Zug z2 = new Zug(player2);
	z2.execute(game);
	System.out.println("");
	game.printboard();

	Zug z3 = new Zug(player, player.startfield, player2.startfield, /*swap*/ false);
	z3.execute(game);
	System.out.println("");
	game.printboard();

	
	z2.execute(game);
	System.out.println("");
	game.printboard();

	Zug karte = new Zug(player2, player2.startfield, game.board.get(15), /*swap*/ false);
	karte.execute(game);
	System.out.println("");
	game.printboard();

	z.execute(game);
	System.out.println("");
	game.printboard();

	Card swapcard = new Card('s');
	Zug swapmove = swapcard.getmoves(game, player.figures.get(0)).get(0);
	swapmove.execute(game);
	System.out.println("");
	game.printboard();

	Zug house = new Zug(player, game.board.get(15), game.board.get(22),  /*swap*/ false);
	house.execute(game);
	System.out.println("");
	game.printboard();


	Figure figg = game.players.get(0).getfirstinbank();
	Card card = new Card('t');
	ArrayList<Zug> züge = card.getmoves(game, figg);
	System.out.println("anzahl züge " + züge.size());
	Zug thirteen = züge.get(0);
	thirteen.execute(game);
	System.out.println("");
	game.printboard();
	
    }
}
