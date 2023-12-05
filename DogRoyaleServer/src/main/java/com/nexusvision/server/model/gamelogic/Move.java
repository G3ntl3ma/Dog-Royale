package com.nexusvision.server.model.gamelogic;// package org.example;
/**
 *
 *
 * @author dgehse
 */
public final class Move  {

    private final Field from;
    private final Field to;
    private final boolean isSwapMove;
    private final boolean isStartMove; //ignore from field just put a fig on isStartMove
    private final Player player;
    private final Card cardUsed;
    
    public Move(Player player, Field from, Field to, boolean isSwapMove, Card cardUsed) {
	this.player = player;
	this.from = from;
	this.to = to;
	this.isSwapMove = isSwapMove;
	this.isStartMove = false;
	this.cardUsed = cardUsed;
    }

    public Move(Player player, Card cardUsed) { //isStartMove move
	this.player = player;
	this.isStartMove = true;
	this.cardUsed = cardUsed;
	this.from = null;
	this.to = null;
	this.isSwapMove = false;
    }

    public boolean equal(Move move) {
	//TODO readability
	if(this.from == move.from && this.to == move.to && this.isSwapMove == move.isSwapMove && this.isStartMove == move.isStartMove &&
	   this.player == move.player && this.cardUsed == move.cardUsed) {
	    return true;
	}

	if(this.from == null || move.from == null || this.to == null || move.to == null) {
	    return this.from == null && move.from == null && this.to == null && move.to == null &&
		this.isSwapMove == move.isSwapMove && this.isStartMove == move.isStartMove
		&& this.player.color == move.player.color && this.cardUsed.type == move.cardUsed.type;
	}
	
	
	return this.from.val == move.from.val && this.to.val == move.to.val &&
	    this.isSwapMove == move.isSwapMove && this.isStartMove == move.isStartMove
	    && this.player.color == move.player.color && this.cardUsed.type == move.cardUsed.type;
    }
    

    public void execute(Game game) {
	// System.out.println("cards num before " + this.player.cards.size());
	if(this.cardUsed != null) {
	    player.cards.remove(this.cardUsed);
	    game.pile.add(this.cardUsed);
	}
	// System.out.println("cards num after 1 " + this.player.cards.size());
	if(isSwapMove) {
	    Figure temp = to.figure;
	    Player opponent = game.players.get(to.figure.color);
	    //set figure of field
	    to.figure = from.figure;
	    from.figure = temp;

	    //set field of figures
	    to.figure.field = to;
	    from.figure.field = from;

	    if (to.type== FieldType.DRAW) {
		// System.out.println("player " + player.color + " draw card!");
		player.draw(game);
	    }
	    if (from.type== FieldType.DRAW) {
		// System.out.println("player " + opponent.col + " draw card!");
		opponent.draw(game);
	    }
	    
	}
	else if(isStartMove) {
	    //TODO assert figs in bank > 0
	    // System.out.println("isStartMove move");
	    // System.out.println("figs in bank before " + this.player.figuresInBank);
	    Figure figure = this.player.getFirstFigureInBank();
	    Field to = player.startField;
	    figure.isInBank = false;
	    figure.isInHouse = false;
	    
	    if (!to.isEmpty()) {
		Player opponent = game.players.get(to.figure.color);
		opponent.figuresInBank++;
		//set field of figure
		to.figure.isInBank = true;
	    }

	    //set figure of field
	    player.startField.setfigure(figure); //get first figure not on field from player
	    this.player.figuresInBank--;

	    game.occupied[player.color] = true;

	    //TODO assert figcol == playercol
	    // System.out.println("figs in bank " + this.player.figuresInBank);
	    // System.out.println("fig col " + figure.color);
	    // System.out.println("plyer col " + this.player.color);
	}
	else { //normal move
	    if (!to.isEmpty()) {
		Player opponent = game.players.get(to.figure.color);
		opponent.figuresInBank++;
		to.figure.isInBank = true;
	    }

	    if (to.type== FieldType.HOUSE) {
		player.houseOccupationIndex = to.val;
	    }
	    
	    if (to.type== FieldType.HOUSE && from.type!= FieldType.HOUSE) {
		player.figuresInHouse++;
		player.lastMoveCountFigureMovedIntoHouse = game.movesMade;
		from.figure.isInHouse = true;
	    }

	    //possible??
	    if (to.type!= FieldType.HOUSE && from.type== FieldType.HOUSE) {
		player.figuresInHouse--;
		from.figure.isInHouse = false;
	    }
	    
	    // from.empty() = true;
	    to.setfigure(from.figure);
	    from.setEmpty();


	    if (to.type== FieldType.DRAW) {
		// System.out.println("player " + player.color + " karte ziehen!");
		player.draw(game);
	    }

	    if (from.type== FieldType.START) {
		game.occupied[player.color] = false;
	    }
	    if (to.type== FieldType.START) {
		game.occupied[player.color] = true;
	    }
	}
	
	game.increaseMovesCounter();
	// game.nextPlayer();
	// System.out.println("cards num after 2 " + this.player.cards.size());
    }

    public void printmove() { 
	System.out.print("card type " + this.cardUsed.type + " ");
	if(this.from != null) {
	    System.out.print("from " + this.from.val + " ");
	}
	if(this.to != null) {
	    System.out.print("to " + this.to.val + " ");
	}
	System.out.println("swap figs " + this.isSwapMove + " isStartMove " + this.isStartMove + " player.coloror " + this.player.color);
    }



    public FieldType getFieldTypeTo() {
	return this.to.type;
    }
}
