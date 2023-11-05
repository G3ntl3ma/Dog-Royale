import java.util.ArrayList;

public class Card {

    char typ; //TODO use enums ?
    int steps; 
    
    public Card(char typ, int steps) {
	this.typ = typ;
	this.steps = steps;
    }

    public Card(char typ) {
	this.typ = typ;
    }

    private void addstepmove(ArrayList<Zug> moves, int argsteps, Figure figure, Game game, Player player, boolean range) {
	//check if current field startfield (check if can move into house)
	boolean neg = argsteps < 0;
	Field to;
	int steps;
	if (neg) {
	    steps = argsteps + 1;
	    to = figure.field.prev;
	}
	else {
	    steps = argsteps - 1;
	    to = figure.field.next;
	}
	if(to == null) return;
	
	if(to.typ == 's' && to == game.players.get(to.figure.col).startfield) {
	    return;
	}
	
	if(range || steps == 0) moves.add(new Zug(player, figure.field, to, false));

	while (steps != 0) {
	    // System.out.println(steps);
	    if (neg) {
		steps++;
		to = to.prev;
	    }
	    else {
		steps--;
		//check if move passes own startfield
		if (game.players.get(figure.col).startfield == to) to = to.house;
		else to = to.next;
	    }
	    //check if fields occupied and startfield of that figure
	    //TODO bool for every start field that keeps track if occupied
	    //TODO set to.figure to null so checking for emptyness is not necessary
	    if(to.typ == 's' && to == game.players.get(to.figure.col).startfield && !to.empty) {
		return;
	    }
	    if (range || steps == 0) {
		moves.add(new Zug(player, figure.field, to, false));
	    }
	}
    }

    //move generator for card
    public void getmoves(Game game, Figure figure, ArrayList<Zug> moves) { //target figure
	Player player = game.players.get(figure.col);
	// System.out.println("go get move");
	Field to;
	switch (this.typ) {
	case 'n': //normal
		if (figure.inbank) break;
		addstepmove(moves, this.steps,  figure, game, player, false);
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
		to = figure.field;
		Field next = to.next;
		while (next.empty) {
		    to = next;
		    next = next.next;
		}
		if(figure.field != to) { //move that does nothing allowed???
		    moves.add(new Zug(player, figure.field, to, false));
		}
		break;
	case '7':  //1-7
		if (figure.inbank) break;
		addstepmove(moves, 7,  figure, game, player, /*range*/ true);		
		break;
	case '4':  //+-4
	        System.out.println("four card");
		if (figure.inbank) break;
		addstepmove(moves, 4, figure, game, player, false);
		addstepmove(moves, -4, figure, game, player, false);		
		break;
	case 't':  //13
	    if (figure.inbank && (player.startfield.empty ||  player.startfield.figure.col != figure.col)) {
		moves.add(new Zug(player));
	    }
	    else {
		addstepmove(moves, 13,  figure, game, player, false);
	    }
	    break;
	case 'e':  //1, 11
	    if (figure.inbank && (player.startfield.empty ||  player.startfield.figure.col != figure.col)) {
		moves.add(new Zug(player));
	    }
	    else if (!figure.inbank){
		addstepmove(moves, 1,  figure, game, player, false);
		addstepmove(moves, 11,  figure, game, player, false);
	    }
	    break;
	case 'c':  //copy
	    int inx = game.pile.size() -1 ;
	    for (int i = inx; i > 0; i--) {
		Card lastcard = game.pile.get(game.pile.size() - 1);
		if (lastcard.typ != 'c') {
		    lastcard.getmoves(game, figure, moves);
		    break; 
		}
	    }
	    break;
	}
    }

    public void printtyp() {
	System.out.println(this.typ);
    }
}
