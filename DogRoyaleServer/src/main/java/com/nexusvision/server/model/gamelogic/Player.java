package com.nexusvision.server.model.gamelogic;// package org.example;

import java.util.ArrayList;
/**
 * This class represents a player and manages moves a player can make.
 *
 * @author dgehse
 */
public final class Player  {

    ArrayList<Figure> figures = new ArrayList<>();
    ArrayList<Card> cards = new ArrayList<>();
    Field startField; //accessed a lot in other classes
    int houseFirstIndex;
    int figuresInBank;
    int figuresInHouse;
    final int color;
    int lastMoveCountFigureMovedIntoHouse;
    public boolean excluded = false;
    public boolean outThisRound = false;
    
    int houseOccupationIndex; //index of housefield that with last figure TODO unused

	/**
	 * Constructor for the Player
	 *
	 * @param color An Integer representing the color of the player
	 * @param figurecount An Integer representing the amount of figures a player has
	 */
    public Player(int color, int figurecount) {
	this.figuresInBank = figurecount;
	this.color = color;
	this.lastMoveCountFigureMovedIntoHouse = 0;
	for (int i = 0; i < figurecount; i++) {
	    figures.add(new Figure(color));
	}
    }

	/**
	 * Gets the first figure in the bank and returns it
	 *
	 * @return an object representing the figure
	 */
    public Figure getFirstFigureInBank() {
	for (int i = 0; i < figures.size(); i++) {
	    if (this.figures.get(i).isInBank) {
		return this.figures.get(i);
	    }
	}

	System.out.println("unreachable getfirstinbank");
	System.exit(23);
	return this.figures.get(0);
    }

	/**
	 * Prints information player, figures in bank, figures in house and the cards
	 *
	 */
    public void printInfo() {
	System.out.print("player " + this.color + " figbank " + this.figuresInBank + " figs in house " + this.figuresInHouse);
	System.out.print(" cards ");
	this.printCards();
	System.out.println("");
    }

	/**
	 * Prints information about all the figures in every house
	 *
	 */
    public void printHouse() {
	Field house = this.startField.house;
	System.out.print("house: ");
	while(house != null) {
	    if(!house.isEmpty()) System.out.print(house.figure.color+"-");
	    else         System.out.print("_"+"-");
	    house = house.next;
	}
	System.out.println("");
    }

	/**
	 * Draw a card from the deck, shuffle if empty
	 *
	 * @param game an object representing the game
	 */
    public void draw(Game game) {
	if(game.deck.size() == 0) {
	    game.reshuffle();
	}
	Card pop = game.deck.remove(game.deck.size() - 1);
	this.cards.add(pop);
    }

	/**
	 * Prints all cards from the player
	 *
	 */
    public void printCards() {
	for (int i = 0; i < this.cards.size(); i++) {
	    System.out.print(this.cards.get(i).type + " ");
	}
    }

	/**
	 * Marks the player as excluded from the game
	 *
	 */
    public void setExclude() {
	this.excluded = true;
    }

	/**
	 * Marks the player as excluded from the round
	 *
	 */
    public void setOutThisRound() {
	this.outThisRound = true;
    }

	/**
	 * Generate and collect possible moves
	 *
	 * @param game An object representing the game
	 * @param moves an ArrayList storing the represented moves
	 */
    public void generateMoves(Game game, ArrayList<Move> moves) {
	boolean[] seenCardTypes = new boolean[CardType.values().length];
	boolean seenBankFigure = false;
	for (int i = 0; i < seenCardTypes.length; i++) {
	    seenCardTypes[i] = false;
	}
	// System.out.println("this player coloror " + this.color);
	for (int i = 0; i < this.cards.size(); i++) {
	    // System.out.println("card " + i + ": " + this.cards.get(i).typ);
	    if (seenCardTypes[this.cards.get(i).type.ordinal()]) continue;
	    seenCardTypes[this.cards.get(i).type.ordinal()] = true;
	    for (int j = 0; j < this.figures.size(); j++) {
		// System.out.println("figure " + j);
		if(seenBankFigure && this.figures.get(j).isInBank == true) continue;
		if (this.figures.get(j).isInBank == true && (this.cards.get(i).type == CardType.START_1_11 || this.cards.get(i).type == CardType.START_13)) {
		    seenBankFigure = true;
		}
		this.cards.get(i).getMoves(game, this.figures.get(j), moves, this); 
	    }
	}
    }
}
