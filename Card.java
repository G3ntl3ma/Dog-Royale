import java.util.ArrayList;

public class Card {

    char typ; //TODO use enums ?
    int steps; //TODO use enums
    
    public Card(char typ, int steps) {
	this.typ = typ;
	this.steps = steps;
    }

    public Card(char typ) {
	this.typ = typ;
    }

    //move generator for card
    public ArrayList<Zug> getmoves(Game game, Figure figure) { //target figure
	Player player = game.players.get(figure.col);
	ArrayList<Zug> moves = new ArrayList<>(); //TODO pass moves as arg?
	System.out.println("go get move");
	switch (this.typ) {
	case 'n': //normal
		if (figure.inbank) break;
		//check if current field startfield
		//check if fields occupied and startfield of that figure
		//check if move passes startfield
		break;
	case 's': //swap
	    for(int i = 0; i < game.players.size(); i++) {
		if(i == figure.col) continue;
		Player opponent = game.players.get(i);
		for (int j = 0; j < opponent.figures.size(); j++) {
		    Figure oppfigure = opponent.figures.get(j);
		    if (!oppfigure.inbank && !oppfigure.inhouse /* &&  oppfigure.field.typ != 's' */) {
			System.out.println("added move");
			moves.add(new Zug(player ,figure.field, oppfigure.field, true));
		    }
		}
	    }
	    break;
	case 'm':  //magnet
		if (figure.inbank) break;
		break;
	case '7':  //1-7
		if (figure.inbank) break;
		break;
	case '4':  //+-4
		if (figure.inbank) break;
		break;
	case 't':  //13
	    if (figure.inbank && (player.startfield.empty ||  player.startfield.figure.col != figure.col)) {
		moves.add(new Zug(player));
	    }
	    //TODO normal 13 move
	    break;
	case 'e':  //1, 11
	    if (figure.inbank && (player.startfield.empty ||  player.startfield.figure.col != figure.col)) {
		moves.add(new Zug(player));
	    }
	    //TODO 1 11
	    break;
	}
	return moves;
    }

    public void printtyp() {
	System.out.println(this.typ);
    }
}
