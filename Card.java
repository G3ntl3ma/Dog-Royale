import java.util.ArrayList;

enum Cardtype {
    MAGNET, //0
    COPY, //1
    NORMAL_2,
    NORMAL_3,
    PLUS_MINUS_4,
    NORMAL_5,
    NORMAL_6,
    RANGE_7, 
    NORMAL_8,
    NORMAL_9,
    NORMAL_10,
    START_1_11,
    NORMAL_12,
    START_13,
    SWAP,
}
    

public class Card {
    Cardtype type;
    
    public Card(Cardtype type) {
	this.type = type;
    }

    //TODO improve efficiency and readability
    //TODO need to add moves where the player can move over startfield without moving into house
    private void addstepmove(ArrayList<Zug> moves, int argsteps, Figure figure, Game game, Player player, boolean range) {
	//check if current field startfield (check if can move into house)
	// System.out.println("argsteps " + argsteps);
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
	
	if (!to.empty) { //check emptyness first otherwise to.figure is null and therefore to.figure.col bug	
	    if((to.typ == 's' && to == game.players.get(to.figure.col).startfield) || to.typ == 'h') {
		return;
	    }
	}
	
	if(range || steps == 0) moves.add(new Zug(player, figure.field, to, false, this));

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
	    if (to == null) return;
	    
	    //check if fields occupied and startfield of that figure
	    //TODO bool for every start field that keeps track if occupied
	    //TODO set to.figure to null so checking for emptyness is not necessary
	    if (!to.empty) { //check emptyness first otherwise to.figure is null and therefore to.figure.col bug
		if((to.typ == 's' && to == game.players.get(to.figure.col).startfield) || to.typ == 'h') {
		    return;
		}
	    }
	    if (range || steps == 0) {
		moves.add(new Zug(player, figure.field, to, false, this));
	    }
	}
    }

    //move generator for card
    public void getmoves(Game game, Figure figure, ArrayList<Zug> moves, Player player) { //target figure
	Field to;
	switch (this.type) {
	case Cardtype.SWAP: 
	    if (figure.inbank || figure.inhouse /*allowed?*/) break;
	    for(int i = 0; i < game.players.size(); i++) {
		if(i == figure.col) continue;
		Player opponent = game.players.get(i);
		for (int j = 0; j < opponent.figures.size(); j++) {
		    Figure oppfigure = opponent.figures.get(j);
		    if (!oppfigure.inbank && !oppfigure.inhouse &&  oppfigure.field.typ != 's' ) {
			moves.add(new Zug(player ,figure.field, oppfigure.field, true, this));
		    }
		}
	    }
	    break;
	case Cardtype.MAGNET: 
		if (figure.inbank || figure.inhouse) break;
		to = figure.field;
		Field next = to.next;
		while (next.empty) { 
		    to = next;
		    next = next.next;
		}
		if(figure.field != to) { //move that does nothing allowed???
		    moves.add(new Zug(player, figure.field, to, false, this));
		}
		break;
	case Cardtype.RANGE_7:
		if (figure.inbank) break;
		addstepmove(moves, 7,  figure, game, player, /*range*/ true);		
		break;
	case Cardtype.PLUS_MINUS_4:
		if (figure.inbank) break;
		addstepmove(moves, 4, figure, game, player, false);
		addstepmove(moves, -4, figure, game, player, false);		
		break;
	case Cardtype.START_13: 
	    if (figure.inbank && (player.startfield.empty ||  player.startfield.figure.col != figure.col)) {
		moves.add(new Zug(player, this));
	    }
	    else if (!figure.inbank) {
		addstepmove(moves, 13,  figure, game, player, false);
	    }
	    break;
	case Cardtype.START_1_11:  
	    if (figure.inbank && (player.startfield.empty ||  player.startfield.figure.col != figure.col)) {
		moves.add(new Zug(player, this));
	    }
	    else if (!figure.inbank){
		addstepmove(moves, 1,  figure, game, player, false);
		addstepmove(moves, 11,  figure, game, player, false);
	    }
	    break;
	case Cardtype.COPY: 
	    int inx = game.pile.size() -1 ;
	    for (int i = inx; i > 0; i--) {
		Card lastcard = game.pile.get(game.pile.size() - 1);
		if (lastcard.type != Cardtype.COPY) { 
		    // lastcard.getmoves(game, figure, moves);//bug, sets wrong usedcard
		    this.type = lastcard.type;
		    this.getmoves(game, figure, moves, player);
		    this.type = Cardtype.COPY; //hacky
		    break; 
		}
	    }
	    break;
	default: //normal
	    if (figure.inbank) break;
	    addstepmove(moves, this.type.ordinal(),  figure, game, player, false);
	    break;
	}
    }

    public void printtyp() {
	System.out.println(this.type);
    }

    //slower and buggy
    private void addstepmovenew(ArrayList<Zug> moves, int argsteps, Figure figure, Game game, Player player, boolean range) {
	if(range) {
	    for (int i = 1; i <= argsteps; i++) {
		if(!addstepmovehelp(moves, i, figure, game, player)) return;
	    }
	}
	else {
	    addstepmovehelp(moves, argsteps, figure, game, player);
	}
    }

    //for all start fields between from and to check if occupied
    private boolean addstepmovehelp(ArrayList<Zug> moves, int argsteps, Figure figure, Game game, Player player) {
	int toinx = (argsteps + figure.field.val) % game.mainfields;
	int origtoinx = toinx;
	int frominx = figure.field.val;
	boolean intohouse = false;
	
	//only check if figure is not in house because not possible to move out anyway (?)
	if( figure.field.typ != 'h') {
	    //check if path is blocked by startfigure
	    for (int i = 0; i < game.players.size(); i++) {
		//if startfield is between from and to
		if( (game.startinxs[i] <= origtoinx && game.startinxs[i] > frominx)
		    || (game.startinxs[i] <= frominx && game.startinxs[i] > origtoinx )) {
		    if(game.occupied[i]) return false;
		    //if actually moves into own house
		    if(game.startinxs[i] > frominx && game.startinxs[i] < toinx) {
			toinx += player.houseinx - player.startfield.val;
			intohouse = true;
		    }
		}
		    
	    }
	}
	// if number too big for house
	if (player.houseinx + game.figurecount > toinx) {
	    return false;
	}
	    
	//  if path is blocked by figure in house
	if (intohouse) {
	    if(toinx >= player.houseoccinx) return false;
	}
	    
	moves.add(new Zug(player ,game.board[frominx] , game.board[toinx], false, this));
	return true;
    }
    
    
}
