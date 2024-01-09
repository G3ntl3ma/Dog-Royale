package com.nexusvision.server.model.gamelogic;

import com.nexusvision.server.model.enums.Card;
import com.nexusvision.server.model.enums.FieldType;

public class AntiMove {
    private final Move move;
    //figure that was killt/swapped
    private final Figure opponentFigure;
    private final Card playerDrawnCard;
    private final Card opponentDrawnCard;
    private final Card lastCardOnPile;

    public AntiMove(Move move, Figure opponentFigure, Card playerDrawnCard, Card opponentDrawnCard, Card lastCardOnPile) {
        this.move = move;
        this.opponentFigure = opponentFigure;
        this.playerDrawnCard = playerDrawnCard;
        this.opponentDrawnCard = opponentDrawnCard;
        this.lastCardOnPile = lastCardOnPile;
    }

    public void execute(Game game) {
        //TODO undo the move
        Field to = move.getTo();
        Field from = move.getFrom();
        Player opponent = opponentFigure.getPlayer();
        Figure playerFigure = to.getFigure();

        //remove drawn cards from hand and put back into deck

        //TODO must be reverse order of the move order
        if (this.playerDrawnCard != null) {
            opponent.getCardList().remove(this.playerDrawnCard);
            game.getDeck().add(this.playerDrawnCard); //TODO make sure this is the
        }
        if (this.opponentDrawnCard != null) {
            opponent.getCardList().remove(this.opponentDrawnCard);
            game.getDeck().add(this.opponentDrawnCard); //TODO make sure this is the
        }
        //decrease moves counter
        game.increaseMovesCounter(-1);

        //restore last card
        game.setDrawnCard(null); //TODO

        //give back used card to hand
        if (move.getCardUsed() != null) {
            move.getPlayer().getCardList().add(move.getCardUsed());
            game.getPile().remove(move.getCardUsed());
        }

        //put figure back
        if (this.opponentFigure != null) {
            opponent.setFiguresInBank(opponent.getFiguresInBank() - 1);
            //set field of figure
            to.setFigure() = opponentFigure;
            opponentFigure.setOnBench(false);
        }


        //TODO set figure of field


        if (move.isSwapMove()) {
            from.setFigure(playerFigure);

        } else if (move.isStartMove()) {
            //Figure figure = player.getFirstOnBench();
            Field to = move.getPlayer().getStartField();

            //set playerFigure to be in bank
            playerFigure.setOnBench(true);
            //figure.setInHouse(false);
            move.getPlayer().setFiguresInBank(move.getPlayer().getFiguresInBank() + 1);
        } else { //normal move
            if (this.opponentFigure != null) {
                opponent.setFiguresInBank(opponent.getFiguresInBank() - 1);
                //set field of figure
                to.setFigure(opponentFigure);
                opponentFigure.setOnBench(false);
            }

            if (to.getType() == FieldType.HOUSE && from.getType() != FieldType.HOUSE) {
                move.getPlayer().setFiguresInHouse(player.getFiguresInHouse() - 1);
                move.getPlayer().setLastMoveCountFigureMovedIntoHouse(game.getMovesMade());
                playerFigure.setInHouse(false);
            }

            //moving out of house not possible but
            if (to.getType() != FieldType.HOUSE && from.getType() == FieldType.HOUSE) {
                move.getPlayer().setFiguresInHouse(move.getPlayer().getFiguresInHouse() - 1);
                move.getFrom().getFigure().setInHouse(false);
            }

            to.setFigure(opponentFigure); //can be null
            from.setFigure(playerFigure);

            //already handled
            //if (move.getTo().getType() == FieldType.DRAW) {
                //was draw
            //}
    }


    }
}
