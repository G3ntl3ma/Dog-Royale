import java.util.ArrayList;
import java.util.Arrays;

//TODO implement all moves
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
	Zug z = new Zug(player, none, none, true,false);
	// z.printz();
	z.execute();
	System.out.println("");
	game.printboard();	
	
    }
}
