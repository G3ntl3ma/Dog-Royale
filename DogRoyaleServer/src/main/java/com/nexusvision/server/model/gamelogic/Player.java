package com.nexusvision.server.model.gamelogic;

import com.nexusvision.server.model.enums.Card;
import lombok.Data;

import java.util.ArrayList;

/**
 * This class represents a player and manages moves a player can make.
 *
 * @author dgehse
 */
@Data
public final class Player {

    private final int playerId;
    private boolean excluded = false;
    private boolean outThisRound = false;
    private ArrayList<Figure> figureList = new ArrayList<>();
    private ArrayList<Card> cardList = new ArrayList<>();
    private Field startField; //accessed a lot in other classes
    private int houseFirstIndex;
    private int figuresInBank;
    private int figuresInHouse;
    private int lastMoveCountFigureMovedIntoHouse;
    private int houseOccupationIndex; //index of housefield that with last figure TODO unused

    /**
     * Constructor for the Player
     *
     * @param playerId    An Integer representing the playerId of the player
     * @param figureCount An Integer representing the amount of figures a player has
     */
    public Player(int playerId, int figureCount) {
        this.figuresInBank = figureCount;
        this.playerId = playerId;
        this.lastMoveCountFigureMovedIntoHouse = 0;
        for (int i = 0; i < figureCount; i++) {
            figureList.add(new Figure(playerId, i));
        }
    }

    public Player(int playerId,  boolean excluded,  boolean outThisRound,  ArrayList<Figure> figureList,  ArrayList<Card> cardList,  Field startField,  int houseFirstIndex,  int figuresInBank,  int figuresInHouse,  int lastMoveCountFigureMovedIntoHouse,  int houseOccupationIndex ) {
        this.playerId=playerId;
        this.excluded=excluded;
        this.outThisRound=outThisRound;
        this.figureList=figureList;
        this.cardList=cardList;
        this.startField=startField;
        this.houseFirstIndex=houseFirstIndex;
        this.figuresInBank=figuresInBank;
        this.figuresInHouse=figuresInHouse;
        this.lastMoveCountFigureMovedIntoHouse=lastMoveCountFigureMovedIntoHouse;
        this.houseOccupationIndex=houseOccupationIndex;
    }

    // public Player copy() {
        // ArrayList<Figure> figureList = this.figureList.copy();
        //for figure in figure list copy the figure and put in array
        // ArrayList<Card> cardList = this.cardList.copy();
        //for card in card list copy the figure and put in array
        
        // Field startField = this.startField.copy();
        
        // return new Player(this.playerId,  this.excluded,  this.outThisRound,  figureList, cardList,  startField,  this.houseFirstIndex,  this.figuresInBank,  this.figuresInHouse,  this.lastMoveCountFigureMovedIntoHouse,  this.houseOccupationIndex );
    // }

    /**
     * Gets the first figure in the bank and returns it
     *
     * @return an object representing the figure
     */
    public Figure getFirstOnBench() {
        for (int i = 0; i < figureList.size(); i++) {
            if (this.figureList.get(i).isOnBench()) {
                return this.figureList.get(i);
            }
        }

        System.out.println("unreachable getfirstonbench");
        System.exit(42);
        return this.figureList.get(0);
    }

    /**
     * Gets the card list with the integer ordinal values
     *
     * @return The card integer list
     */
    public ArrayList<Integer> getCardListInteger() {
        ArrayList<Integer> cardList = new ArrayList<>();
        for (Card card : this.cardList) {
            cardList.add(card.getOrdinal());
        }
        return cardList;
    }

    /**
     * Prints information player, figures in bank, figures in house and the cards
     */
    public void printInfo() {
        System.out.print("player " + this.playerId + " figBank " + this.figuresInBank + " figs in house " + this.figuresInHouse);
        System.out.print(" cards ");
        this.printCards();
        System.out.println("");
        for(Figure figure : this.figureList) {
            if (figure != null) {
                figure.print();
            }
            else {
                System.out.println("null field");
            }
        }
    }

    /**
     * Prints information about all the figures in every house
     */
    public void printHouse() {
        Field house = this.startField.getHouse();
        System.out.print("house: ");
        while (house != null) {
            if (!house.isEmpty()) System.out.print(house.getFigure().getOwnerId() + "-");
            else System.out.print("_" + "-");
            house = house.getNext();
        }
        System.out.println("");
    }

    /**
     * Draw a card from the deck, shuffle if empty
     *
     * @param game an object representing the game
     */
    public void draw(Game game) {
        if (game.getDeck().isEmpty()) {
            game.reshuffle();
        }
        Card pop = game.getDeck().remove(game.getDeck().size() - 1);
        game.setDrawnCard(pop);
        this.cardList.add(pop);
    }

    /**
     * Prints all cards from the player
     */
    public void printCards() {
        for (Card card : this.cardList) {
            System.out.print(card + " ");
        }
    }

    /**
     * Marks the player as excluded from the game
     */
    public void setExclude() {
        this.excluded = true;
    }

    /**
     * Marks the player as excluded from the round
     */
    public void setOutThisRound() {
        this.outThisRound = true;
    }

    /**
     * Generate and collect possible moves
     *
     * @param game  An object representing the game
     * @return An ArrayList storing the represented moves
     */
    public ArrayList<Move> generateMoves(Game game) {
        ArrayList<Move> moves = new ArrayList<>();
        boolean[] seenCardTypes = new boolean[Card.values().length];
        boolean seenBenchFigure = false;
//        for (int i = 0; i < seenCardTypes.length; i++) {                 Probably not necessary
//            seenCardTypes[i] = false;
//        }
        // System.out.println("this player color " + this.color);
        for (Card card : this.cardList) {
            game.getCardService().setType(card);
            // System.out.println("card " + card);
            if (seenCardTypes[card.ordinal()]) continue;
            seenCardTypes[card.ordinal()] = true;
            for (Figure figure : figureList) {
                // System.out.println("figure " + j);
                if (seenBenchFigure && figure.isOnBench()) continue;
                if (figure.isOnBench() && (card == Card.startCard1
                        || card == Card.startCard2)) {
                    seenBenchFigure = true;
                }
                game.getCardService().getMoves(game, figure, moves, this);
            }
        }
        // System.out.println("size of moves array" + moves.size());
        return moves;
    }

    public int hash() { //compute a hah of things that can change in simulation

        ArrayList<Integer> handCardValues = this.cardList.stream()
            .map(card -> card.ordinal())
            .sorted()
            .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);

        ArrayList<Integer> variables = new ArrayList<>(); 
        variables.add(outThisRound ? 0 : 1);
        variables.add(figuresInBank);
        variables.add(figuresInHouse);
        variables.add(lastMoveCountFigureMovedIntoHouse);
        variables.add(handCardValues.hashCode());
        return variables.hashCode();
    }
    
}
