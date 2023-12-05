// package org.example;

import java.util.ArrayList;



/**
 * This class represents a card and manages moves associated with the card.
 *
 * @author dgehse
 */

public class Card {
    CardType type;
    
    public Card(CardType type) {
	this.type = type;
    }

    //TODO improve efficiency and readability
    //TODO need to add moves where the player can move over startField without moving into house

	/**
	 * The method adds possible moves for a given Figure to an ArrayList of Move objects.
	 *
	 * @param moves An ArrayList where the method adds possible moves
	 * @param argsteps An integer representing the number of steps the figure can move
	 * @param figure An object representing the game piece to be moved
	 * @param game An object representing the gamestate
	 * @param player An object representing the player to whom the figure belongs
	 * @param range A boolean flag indicating whether the move should be within the movement range or not
	 */
    private void addStepMove(ArrayList<Move> moves, int argsteps, Figure figure, Game game, Player player, boolean range) {
	//check if current field startField (check if can move into house)
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
	
	if (!to.isEmpty()) { //check.isEmpty()ness first otherwise to.figure is null and therefore to.figure.color bug	
	    if((to.type== FieldType.START && to == game.players.get(to.figure.color).startField) || to.type== FieldType.HOUSE) {
		return;
	    }
	}
	
	if(range || steps == 0) moves.add(new Move(player, figure.field, to, false, this));

	while (steps != 0) {
	    // System.out.println(steps);
	    if (neg) {
		steps++;
		to = to.prev;
	    }
	    else {
		steps--;
		//check if move passes own startField
		if (game.players.get(figure.color).startField == to) {
		    to = to.house;
		}
		else to = to.next;
	    }
	    if (to == null) return;
	    
	    //check if fields occupied and startField of that figure
	    if (!to.isEmpty()) { //check.isEmpty()ness first otherwise to.figure is null and therefore to.figure.color bug
		if((to.type== FieldType.START && to == game.players.get(to.figure.color).startField) || to.type== FieldType.HOUSE) {
		    return;
		}
	    }
	    if (range || steps == 0) {
		moves.add(new Move(player, figure.field, to, false, this));
	    }
	}
    }
	/**
	 * The method generates and returns a Move based on a given Game, card type and other parameters.
	 *
	 * @param game An object representing the current state of the game
	 * @param selectedValue An integer representing the selected Value
	 * @param pieceId An integer representing the identifier of the player's figure
	 * @param isStarter A boolean indicating whether the move is initiated by a starter card
	 * @param opponentPieceId An optional parameter representing the identifier of an opponent's figure (can be null)
	 * @param player An object representing the player making the move
	 *
	 * @return the first move of the ArrayList moves
	 */
    public Move getMove(Game game, int selectedValue,
			int pieceId, boolean isStarter, Integer opponentPieceId, Player player) {
	Figure figure = player.figures.get(pieceId);
	Figure oppfigure = player.figures.get(opponentPieceId);
	ArrayList<Move> moves = new ArrayList<>();
	switch (this.type) {
	case CardType.SWAP:
	    moves.add(new Move(player, figure.field, oppfigure.field, true, this));
	    break;
	case CardType.START_13: 
	case CardType.START_1_11:
	    if(isStarter) {
		moves.add(new Move(player, this));
		break;
	    }
	case CardType.COPY: 
	case CardType.MAGNET: 
	case CardType.RANGE_7:
	default: //normal
	    addStepMove(moves, selectedValue,  figure, game, player, /*range*/ false);
	    break;
	}
	return moves.get(0);
    }
	/**
	 * The method that generates and adds possible moves for a given Figure
	 *
	 * @param game An object representing the current state of the game
	 * @param figure An object representing the game piece to be moved
	 * @param moves An ArrayList where the method adds possible moves
	 * @param player An object representing the player to whom the figure belongs
	 */
    //move generator for card
    public void getMoves(Game game, Figure figure, ArrayList<Move> moves, Player player) { //target figure
	Field to;
	switch (this.type) {
	case CardType.SWAP: 
	    if (figure.isInBank || figure.isInHouse /*allowed?*/) break;
	    for(int i = 0; i < game.players.size(); i++) {
		if(i == figure.color) continue;
		Player opponent = game.players.get(i);
		for (int j = 0; j < opponent.figures.size(); j++) {
		    Figure oppfigure = opponent.figures.get(j);
		    if (!oppfigure.isInBank && !oppfigure.isInHouse &&  oppfigure.field.type!= FieldType.START ) {
			moves.add(new Move(player ,figure.field, oppfigure.field, true, this));
		    }
		}
	    }
	    break;
	case CardType.MAGNET: 
		if (figure.isInBank || figure.isInHouse) break;
		to = figure.field;
		Field next = to.next;
		while (next.isEmpty()) { 
		    to = next;
		    next = next.next;
		}
		if(figure.field != to) { //move that does nothing allowed???
		    moves.add(new Move(player, figure.field, to, false, this));
		}
		break;
	case CardType.RANGE_7:
		if (figure.isInBank) break;
		addStepMove(moves, 7,  figure, game, player, /*range*/ true);		
		break;
	case CardType.PLUS_MINUS_4:
		if (figure.isInBank) break;
		addStepMove(moves, 4, figure, game, player, false);
		addStepMove(moves, -4, figure, game, player, false);		
		break;
	case CardType.START_13: 
	    if (figure.isInBank && (player.startField.isEmpty() ||  player.startField.figure.color != figure.color)) {
		moves.add(new Move(player, this));
	    }
	    else if (!figure.isInBank) {
		addStepMove(moves, 13,  figure, game, player, false);
	    }
	    break;
	case CardType.START_1_11:  
	    if (figure.isInBank && (player.startField.isEmpty() ||  player.startField.figure.color != figure.color)) {
		moves.add(new Move(player, this));
	    }
	    else if (!figure.isInBank){
		addStepMove(moves, 1,  figure, game, player, false);
		addStepMove(moves, 11,  figure, game, player, false);
	    }
	    break;
	case CardType.COPY: 
	    int inx = game.pile.size() -1 ;
	    for (int i = inx; i > 0; i--) {
		Card lastcard = game.pile.get(game.pile.size() - 1);
		if (lastcard.type != CardType.COPY) { 
		    // lastcard.getMoves(game, figure, moves);//bug, sets wrong usedcard
		    this.type = lastcard.type;
		    this.getMoves(game, figure, moves, player);
		    this.type = CardType.COPY; //hacky
		    break; 
		}
	    }
	    break;
	default: //normal
	    if (figure.isInBank) break;
	    addStepMove(moves, this.type.ordinal(),  figure, game, player, false);
	    break;
	}
    }

    public void printtyp() {
	System.out.println(this.type);
    }

    //slower and buggy
    // private void addStepMovenew(ArrayList<Move> moves, int argsteps, Figure figure, Game game, Player player, boolean range) {
	// if(range) {
	    // for (int i = 1; i <= argsteps; i++) {
		// if(!addStepMovehelp(moves, i, figure, game, player)) return;
	    // }
	// }
	// else {
	    // addStepMovehelp(moves, argsteps, figure, game, player);
	// }
    // }
				// 
    //for all start fields between from and to check if occupied
    // private boolean addStepMovehelp(ArrayList<Move> moves, int argsteps, Figure figure, Game game, Player player) {
	// int toinx = (argsteps + figure.field.val) % game.mainFieldCount;
	// int origtoinx = toinx;
	// int frominx = figure.field.val;
	// boolean intohouse = false;
	
	// only check if figure is not in house because not possible to move out anyway (?)
	// if( figure.field.type!= 'h') {
	    // check if path is blocked by startfigure
	    // for (int i = 0; i < game.players.size(); i++) {
		// if startField is between from and to
		// if( (game.startinxs[i] <= origtoinx && game.startinxs[i] > frominx)
		    // || (game.startinxs[i] <= frominx && game.startinxs[i] > origtoinx )) {
		    // if(game.occupied[i]) return false;
		    // if actually moves into own house
		    // if(game.startinxs[i] > frominx && game.startinxs[i] < toinx) {
			// toinx += player.houseinx - player.startField.val;
			// intohouse = true;
		    // }
		// }
		    
	    // }
	// }
	// if number too big for house
	// if (player.houseinx + game.figureCount > toinx) {
	    // return false;
	// }
	    
	 // if path is blocked by figure in house
	// if (intohouse) {
	    // if(toinx >= player.houseoccinx) return false;
	// }
	    
	// moves.add(new Move(player ,game.board[frominx] , game.board[toinx], false, this));
	// return true;
    // }
    
    
}
