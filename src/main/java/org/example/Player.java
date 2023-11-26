package org.example;

import java.util.ArrayList;

public final class Player  {

    ArrayList<Figure> figures = new ArrayList<>();
    ArrayList<Card> cards = new ArrayList<>();
    Field startField; //accessed a lot in other classes
	int id;
    int houseFirstIndex;
    int figuresInBank;
    int figuresInHouse;
    public int color;
    int lastMoveCountFigureMovedIntoHouse;
    
    int houseOccupationIndex; //index of housefield that with last figure TODO unused
    
    public Player(int id,int color, int figurecount) {
	this.id = id;
	this.figuresInBank = figurecount;
	this.color = color;
	this.lastMoveCountFigureMovedIntoHouse = 0;
	for (int i = 0; i < figurecount; i++) {
	    figures.add(new Figure(color));
	}
    }

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

    public void printInfo() {
	System.out.print("player " + this.color + " figbank " + this.figuresInBank + " figs in house " + this.figuresInHouse);
	System.out.print(" cards ");
	this.printCards();
	System.out.println("");
    }

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

    public void draw(Game game) {
	if(game.deck.size() == 0) {
	    game.reshuffle();
	}
	Card pop = game.deck.remove(game.deck.size() - 1);
	this.cards.add(pop);
    }

    public void printCards() {
	for (int i = 0; i < this.cards.size(); i++) {
	    System.out.print(this.cards.get(i).type + " ");
	}
    }

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

