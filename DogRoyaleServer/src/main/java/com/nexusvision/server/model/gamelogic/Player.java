package com.nexusvision.server.model.gamelogic;

import com.nexusvision.server.model.enums.CardType;
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
    private ArrayList<Figure> figures = new ArrayList<>();
    private ArrayList<Card> cards = new ArrayList<>();
    private Field startField; //accessed a lot in other classes
    private int houseFirstIndex;
    private int figuresInBank;
    private int figuresInHouse;
    private int lastMoveCountFigureMovedIntoHouse;
    private int houseOccupationIndex; //index of housefield that with last figure TODO unused

    /**
     * Constructor for the Player
     *
     * @param color       An Integer representing the color of the player
     * @param figurecount An Integer representing the amount of figures a player has
     */
    public Player(int playerId, int figurecount) {
        this.figuresInBank = figurecount;
        this.playerId = playerId;
        this.lastMoveCountFigureMovedIntoHouse = 0;
        for (int i = 0; i < figurecount; i++) {
            figures.add(new Figure(playerId));
        }
    }

    /**
     * Gets the first figure in the bank and returns it
     *
     * @return an object representing the figure
     */
    public Figure getFirstFigureInBank() {
        for (int i = 0; i < figures.size(); i++) {
            if (this.figures.get(i).isOnBench()) {
                return this.figures.get(i);
            }
        }

        System.out.println("unreachable getfirstinbank");
        System.exit(23);
        return this.figures.get(0);
    }

    /**
     * Prints information player, figures in bank, figures in house and the cards
     */
    public void printInfo() {
        System.out.print("player " + this.playerId + " figbank " + this.figuresInBank + " figs in house " + this.figuresInHouse);
        System.out.print(" cards ");
        this.printCards();
        System.out.println("");
    }

    /**
     * Prints information about all the figures in every house
     */
    public void printHouse() {
        Field house = this.startField.getHouse();
        System.out.print("house: ");
        while (house != null) {
            if (!house.isEmpty()) System.out.print(house.getFigure().getColor() + "-");
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
        this.cards.add(pop);
    }

    /**
     * Prints all cards from the player
     */
    public void printCards() {
        for (Card card : this.cards) {
            System.out.print(card.getType() + " ");
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
     * @param moves an ArrayList storing the represented moves
     */
    public void generateMoves(Game game, ArrayList<Move> moves) {
        boolean[] seenCardTypes = new boolean[CardType.values().length];
        boolean seenBankFigure = false;
//        for (int i = 0; i < seenCardTypes.length; i++) {                 Probably not necessary
//            seenCardTypes[i] = false;
//        }
        // System.out.println("this player color " + this.color);
        for (int i = 0; i < this.cards.size(); i++) {
            // System.out.println("card " + i + ": " + this.cards.get(i).typ);
            if (seenCardTypes[this.cards.get(i).getType().ordinal()]) continue;
            seenCardTypes[this.cards.get(i).getType().ordinal()] = true;
            for (int j = 0; j < this.figures.size(); j++) {
                // System.out.println("figure " + j);
                if (seenBankFigure && this.figures.get(j).isOnBench()) continue;
                if (this.figures.get(j).isOnBench() && (this.cards.get(i).getType() == CardType.startCard1
                        || this.cards.get(i).getType() == CardType.startCard2)) {
                    seenBankFigure = true;
                }
                this.cards.get(i).getMoves(game, this.figures.get(j), moves, this);
            }
        }
    }
}
