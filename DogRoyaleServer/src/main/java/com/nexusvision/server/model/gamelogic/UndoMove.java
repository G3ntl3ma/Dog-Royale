package com.nexusvision.server.model.gamelogic;

import com.nexusvision.server.model.enums.Card;
import com.nexusvision.server.model.enums.FieldType;

public class UndoMove {
    private final Move move;
    //figure that was killed/swapped
    private final Figure opponentFigure;
    private final Figure playerFigure;
    private final Card playerDrawnCard;
    private final Card opponentDrawnCard;
    private final Card lastCardOnPile;
    private final int LastMoveCountFigureMovedIntoHouse;

    public UndoMove(Move move,Figure playerFigure, Figure opponentFigure, Card playerDrawnCard, Card opponentDrawnCard, Card lastCardOnPile, int LastMoveCountFigureMovedIntoHouse) {
        this.move = move;
        this.playerFigure = playerFigure;
        this.opponentFigure = opponentFigure;
        this.playerDrawnCard = playerDrawnCard;
        this.opponentDrawnCard = opponentDrawnCard;
        this.lastCardOnPile = lastCardOnPile;
        this.LastMoveCountFigureMovedIntoHouse = LastMoveCountFigureMovedIntoHouse;
    }

    public void execute(Game game) {
        //TODO undo the move
        //TODO set field of figure
        Field to = move.getTo(); //this can be null
        Field from = move.getFrom(); //this can be null
        Player player = move.getPlayer();
        Player opponent = null;
        if (opponentFigure != null) {
            int opponentId = opponentFigure.getOwnerId();
            opponent = game.getPlayerList().get(opponentId);
        }
        if(to == null) {
            to = player.getStartField();
        }
        // Figure playerFigure = to.getFigure();

        //remove drawn cards from hand and put back into deck
        //must be reverse order of the move order
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

        //restore last card on pile 
        game.setDrawnCard(this.lastCardOnPile); //TODO rename this function

        //give back used card to hand
        if (move.getCardUsed() != null) {
            move.getPlayer().getCardList().add(move.getCardUsed());
            game.getPile().remove(move.getCardUsed());
        }


        //TODO(?) field of figure
        //TODO rename figuresinbank function

        if (move.isSwapMove()) {
            from.setFigure(playerFigure);
        } else if (move.isStartMove()) { //put back on bench
            //Figure figure = player.getFirstOnBench();
            to = move.getPlayer().getStartField();
            //set playerFigure to be in bank
            playerFigure.setOnBench(true);
            //figure.setInHouse(false);
            move.getPlayer().setFiguresInBank(move.getPlayer().getFiguresInBank() + 1);
        } else { //normal move
            if (to.getType() == FieldType.HOUSE && from.getType() != FieldType.HOUSE) {
                player.setFiguresInHouse(player.getFiguresInHouse() - 1);
                player.setLastMoveCountFigureMovedIntoHouse(this.LastMoveCountFigureMovedIntoHouse);
                playerFigure.setInHouse(false);
            }
            //moving out of house not possible 
            // if (to.getType() != FieldType.HOUSE && from.getType() == FieldType.HOUSE) {
                // move.getPlayer().setFiguresInHouse(move.getPlayer().getFiguresInHouse() + 1);
                // move.getFrom().getFigure().setInHouse(false);
            // }

            from.setFigure(playerFigure);
        }
        //put opponent figure back on field
        //figure was swapped or kill
        to.setFigure(opponentFigure); //set to null if there was no figure
        if (this.opponentFigure != null) {
            opponent.setFiguresInBank(opponent.getFiguresInBank() - 1);
            opponentFigure.setOnBench(false);
        }
        
    }
}
