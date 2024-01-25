package com.nexusvision.server.service;

import com.nexusvision.server.model.enums.Card;
import com.nexusvision.server.model.enums.FieldType;
import com.nexusvision.server.model.gamelogic.*;
import lombok.Data;

import java.util.ArrayList;

/**
 * This class represents a card and manages moves associated with the card.
 *
 * @author dgehse
 */
@Data
public class CardService {
    private Card emulatedType;
    private Card usedType;

    public CardService(Card type) {
        this.emulatedType = type;
        this.usedType = type;
    }

    public void setType(Card type) {
        this.emulatedType = type;
        this.usedType = type;
    }

    /**
     * The method adds possible moves for a given Figure to an ArrayList of Move objects.
     *
     * @param moves    An ArrayList where the method adds possible moves
     * @param argSteps An integer representing the number of steps the figure can move
     * @param figure   An object representing the game piece to be moved
     * @param game     An object representing the game state
     * @param player   An object representing the player to whom the figure belongs
     * @param range    A boolean flag indicating whether the move should be within the movement range or not
     */
    //TODO improve efficiency and readability
    //TODO need to add moves where the player can move over startField without moving into house
    private void addStepMove(ArrayList<Move> moves, int argSteps, Figure figure, Game game, Player player, boolean range) {
        //check if current field startField (check if can move into house)
        // System.out.println("argSteps " + argSteps);
        boolean neg = argSteps < 0;
        Field to;
        int steps;
        if (neg) {
            steps = argSteps + 1;
            to = figure.getField().getPrev();
        } else {
            steps = argSteps - 1;
            to = figure.getField().getNext();
        }
        if (to == null) return;

        if (!to.isEmpty()) { //check.isEmpty()ness first otherwise to.figure is null and therefore to.figure.color bug
            Field startField = figure.getStartFieldByOwner(game);
            if ((to.getType() == FieldType.START
                    && to == startField)
                    || to.getType() == FieldType.HOUSE) {
                return;
            }
        }

        if (range || steps == 0) moves.add(new Move(player, figure.getField(), to, false, this.usedType));

        while (steps != 0) {
            // System.out.println(steps);
            if (neg) {
                steps++;
                to = to.getPrev();
            } else {
                steps--;
                //check if move passes own startField
                Field startField = to.getFigure().getStartFieldByOwner(game);
                if (startField == to) {
                    to = to.getHouse();
                } else to = to.getNext();
            }
            if (to == null) return;

            //check if fields occupied and startField of that figure
            if (!to.isEmpty()) { //check.isEmpty()ness first otherwise to.figure is null and therefore to.figure.color bug
                Field startField = to.getFigure().getStartFieldByOwner(game);
                if ((to.getType() == FieldType.START
                        && to == startField)
                        || to.getType() == FieldType.HOUSE) {
                    return;
                }
            }
            if (range || steps == 0) {
                moves.add(new Move(player, figure.getField(), to, false, this.usedType));
            }
        }
    }

    /**
     * The method generates and returns a Move based on a given Game, card type and other parameters.
     *
     * @param game            An object representing the current state of the game
     * @param selectedValue   An integer representing the selected Value
     * @param pieceId         An integer representing the identifier of the player's figure
     * @param isStarter       A boolean indicating whether the move is initiated by a starter card
     * @param opponentPieceId An optional parameter representing the identifier of an opponent's figure (can be null)
     * @param player          An object representing the player making the move
     * @return the first move of the ArrayList moves
     */
    public Move getMove(Game game, int selectedValue,
                        int pieceId, boolean isStarter, Integer opponentPieceId, Player player) {
        Figure figure = player.getFigureList().get(pieceId);
        Figure oppfigure = player.getFigureList().get(opponentPieceId);
        ArrayList<Move> moves = new ArrayList<>();
        switch (this.emulatedType) {
            case swapCard:
                moves.add(new Move(player, figure.getField(), oppfigure.getField(), true, this.usedType));
                break;
            case startCard1:
                /*fallthrough*/
            case startCard2:
                if (isStarter) {
                    moves.add(new Move(player, this.usedType));
                    break;
                }
                //else fallthrough
            case copyCard:
                /*fallthrough*/
            case magnetCard:
                /*fallthrough*/
            case oneToSeven:
                /*fallthrough*/
            default: //normal
                addStepMove(moves, selectedValue, figure, game, player, /*range*/ false);
                break;
        }
        return moves.get(0);
    }

    /**
     * The method that generates and adds possible moves for a given Figure
     *
     * @param game   An object representing the current state of the game
     * @param figure An object representing the game piece to be moved
     * @param moves  An ArrayList where the method adds possible moves
     * @param player An object representing the player to whom the figure belongs
     */
    //move generator for card
    public void getMoves(Game game, Figure figure, ArrayList<Move> moves, Player player) { //target figure
        Field to;
        switch (this.emulatedType) {
            case swapCard:
                if (figure.isOnBench() || figure.isInHouse()) break;
                for (Player opponent : game.getPlayerList()) {
                    if (opponent.getClientId() == figure.getClientId()) continue; // don't swap with own figure
                    for (int j = 0; j < opponent.getFigureList().size(); j++) {
                        Figure oppfigure = opponent.getFigureList().get(j);
                        if (!oppfigure.isOnBench() && !oppfigure.isInHouse() && oppfigure.getField().getType() != FieldType.START) {
                            moves.add(new Move(player, figure.getField(), oppfigure.getField(), true, this.usedType));
                        }
                    }
                }
                break;
            case magnetCard:
                if (figure.isOnBench() || figure.isInHouse()) break;
                to = figure.getField();
                Field next = to.getNext();
                while (next.isEmpty()) {
                    to = next;
                    next = next.getNext();
                }
                if (figure.getField() != to) { //move that does nothing allowed???
                    moves.add(new Move(player, figure.getField(), to, false, this.usedType));
                }
                break;
            case oneToSeven:
                if (figure.isOnBench()) break;
                addStepMove(moves, 7, figure, game, player, /*range*/ true);
                break;
            case plusMinus4:
                if (figure.isOnBench()) break;
                addStepMove(moves, 4, figure, game, player, false);
                addStepMove(moves, -4, figure, game, player, false);
                break;
            case startCard1:
                if (figure.isOnBench()
                        && (player.getStartField().isEmpty()
                        || player.getStartField().getFigure().getClientId() != figure.getClientId())) {
                    moves.add(new Move(player, this.usedType));
                } else if (!figure.isOnBench()) {
                    addStepMove(moves, 13, figure, game, player, false);
                }
                break;
            case startCard2:
                if (figure.isOnBench()
                        && (player.getStartField().isEmpty()
                        || player.getStartField().getFigure().getClientId() != figure.getClientId())) {
                    moves.add(new Move(player, this.usedType));
                } else if (!figure.isOnBench()) {
                    addStepMove(moves, 1, figure, game, player, false);
                    addStepMove(moves, 11, figure, game, player, false);
                }
                break;
            case copyCard:
                int inx = game.getPile().size() - 1;
                for (int i = inx; i > 0; i--) {
                    Card lastcard = game.getPile().get(game.getPile().size() - 1);
                    if (lastcard != Card.copyCard) {
                        // lastCard.getMoves(game, figure, moves);//bug, sets wrong usedCard
                        this.emulatedType = lastcard;
                        this.getMoves(game, figure, moves, player);
                        break;
                    }
                }
                break;
            default: //normal
                if (figure.isOnBench()) break;
                addStepMove(moves, this.getOrdinal(), figure, game, player, false);
                break;
        }
    }

    /**
     * Prints the type of the card
     */
    public void printTyp() {
        System.out.println("usedType " + this.usedType + "emulatedType " + this.emulatedType);
    }

    /**
     * Gets the ordinal of the card
     */
    public int getOrdinal() {
        switch (this.usedType) {
            case card2:
                return 2;
            case card3:
                return 3;
            case card5:
                return 5;
            case card6:
                return 6;
            case card8:
                return 8;
            case card9:
                return 9;
            case card10:
                return 10;
            case card12:
                return 12;
            default: //unreachable
                return 0;
        }

    }

}
