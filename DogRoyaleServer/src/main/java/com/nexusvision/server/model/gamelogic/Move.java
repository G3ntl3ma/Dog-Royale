package com.nexusvision.server.model.gamelogic;

import com.nexusvision.server.model.enums.Card;
import com.nexusvision.server.model.enums.FieldType;
import com.nexusvision.server.model.messages.game.BoardState;
import lombok.Data;

/**
 * This class generates Moves
 *
 * @author dgehse
 */
@Data
public final class Move {

    private final Field from;
    private final Field to;
    private final boolean isSwapMove;
    private final boolean isStartMove; //ignore from field just put a fig on isStartMove
    private final Player player;
    private final Card cardUsed;
    private final Figure playerFigure;
    private final int selectedValue;

    /**
     * Constructor for the Move
     *
     * @param player     An object representing the player
     * @param from       An object representing the field the move starts
     * @param to         An object representing the field the move ends
     * @param isSwapMove A Boolean indicating if it is a swap move or not
     * @param cardUsed   An object representing the card used for the move
     */
    public Move(Player player, Field from, Field to, boolean isSwapMove, Card cardUsed, int selectedValue) {
        this.player = player;
        this.from = from;
        this.to = to;
        this.isSwapMove = isSwapMove;
        this.isStartMove = false;
        this.cardUsed = cardUsed;
        if(from != null) {
            this.playerFigure = from.getFigure();
        }
        else {
            this.playerFigure = player.getFirstOnBench();
        }
        this.selectedValue = selectedValue;
    }

    public Move(Player player, Field from, Field to, boolean isSwapMove, Card cardUsed) {
        this.player = player;
        this.from = from;
        this.to = to;
        this.isSwapMove = isSwapMove;
        this.isStartMove = false;
        this.cardUsed = cardUsed;
        if(from != null) {
            this.playerFigure = from.getFigure();
        }
        else {
            this.playerFigure = player.getFirstOnBench();
        }
        this.selectedValue = -1;
    }

    public Integer getPieceId() {
        if (playerFigure == null) return null;
        else return playerFigure.getFigureId();
    }

    public int _getSelectedValue(int mainfields) {
        // formula
        // (startfieldofplayervalue + tofieldvalue - firsthousefieldinx) - currentfieldvalue
        if (isStartMove){
            return -1;
        }
        if(to.getType() == FieldType.HOUSE && from.getType() != FieldType.HOUSE) {
            int houseFieldId = to.getFieldId() - player.getHouseFirstIndex();
            System.out.println("houseFieldId " + houseFieldId);
            int toValue = player.getStartField().getFieldId() + houseFieldId;
            int fromvalue = from.getFieldId();
            System.out.println("toValue " + toValue + "fromvalue " + fromvalue);
            return toValue - fromvalue;
        }
        else {
            // return (to.getFieldId() - from.getFieldId() + mainfields) %mainfields;
            return to.getFieldId() - from.getFieldId(); 
        }
    }

    /**
     * Constructor for the Move
     *
     * @param player   An object representing the player
     * @param cardUsed An object representing the card used for the move
     */
    public Move(Player player, Card cardUsed) { //isStartMove move
        this.player = player;
        this.isStartMove = true;
        this.cardUsed = cardUsed;
        this.from = null;
        this.to = player.getStartField();
        this.isSwapMove = false;
        if(from != null) {
            this.playerFigure = from.getFigure();
        }
        else {
            this.playerFigure = player.getFirstOnBench();
        }
        this.selectedValue = -1;
    }

    /**
     * Checks if the move equals another move
     *
     * @return A Boolean, which is true when the moves are equal
     */
    public boolean equal(Move move) {
        //TODO readability
        if (this.from == move.from && this.to == move.to && this.isSwapMove == move.isSwapMove
                && this.isStartMove == move.isStartMove &&
                this.player == move.player && this.cardUsed == move.cardUsed) {
            return true;
        }

        if (this.from == null || move.from == null || this.to == null || move.to == null) {
            return this.from == null && move.from == null && this.to == null && move.to == null &&
                    this.isSwapMove == move.isSwapMove && this.isStartMove == move.isStartMove
                    && this.player.getClientId() == move.player.getClientId() && this.cardUsed == move.cardUsed;
        }

        return this.from.getFieldId() == move.from.getFieldId() && this.to.getFieldId() == move.to.getFieldId() &&
                this.isSwapMove == move.isSwapMove && this.isStartMove == move.isStartMove
                && this.player.getClientId() == move.player.getClientId() && this.cardUsed == move.cardUsed;
    }

    /**
     * Executes a move, removing and adding cards, if it is a swap move execute it,
     * if it is a start move execute it
     * otherwise execute a normal move(updating figures, handling house, draw cards ect.)
     *
     * @param game An object representing the game
     */
    public UndoMove execute(Game game) {
        // System.out.println("player " + this.player.getClientId() + " execute the following card: " + this.cardUsed);
        boolean hascard = this.player.getCardList().contains(this.cardUsed);
        // System.out.println("has card " + hascard);
        if(!hascard) {
            System.out.println("player " + this.player.getClientId() + " doesnt hold the card he is trying to play " + this.cardUsed);
            for(Card card : player.getCardList()) {
                System.out.print(card + " ");
            }
            System.out.println("");
            System.exit(42);
        }

        //NOTE: UndoMove is never actually used
        // Figure playerFigure = null; //TODO
        game.getDrawnCards().clear();
        Figure opponentFigure = null;
        Card playerDrawnCard= null;
        Card opponentDrawnCard= null;
        Card lastCardOnPile = null;
        if(!game.getPile().isEmpty()) {
            lastCardOnPile= game.getLastCardOnPile();
        }
        int LastMoveCountFigureMovedIntoHouse = this.getPlayer().getLastMoveCountFigureMovedIntoHouse();
        //game.setDrawnCard(null);

        // System.out.println("cards num before " + this.player.cards.size());
        if (cardUsed != null) {
            player.getCardList().remove(cardUsed);
            game.getPile().add(cardUsed);
            if(cardUsed != Card.copyCard) game.setLastCardOnPile(cardUsed);
            BoardState.DiscardItem discardItem = new BoardState.DiscardItem();
            discardItem.setClientId(player.getClientId());
            discardItem.setCard(cardUsed.ordinal());
            game.getPileInfo().add(discardItem);
        }
        // System.out.println("cards num after 1 " + this.player.cards.size());
        if (isSwapMove) {
            opponentFigure = to.getFigure();
            //get player object of figure
            //Player opponent = game.getPlayerList().get(to.getFigure().getOwnerId());
            Player opponent = to.getFigure().getPlayerObjectByOwner(game);
            //set figure of field
            // playerFigure = from.getFigure();
            to.setFigure(playerFigure);
            from.setFigure(opponentFigure);

            //set field of figures
            to.getFigure().setField(to);
            from.getFigure().setField(from);

            if (from.getType() == FieldType.DRAW) {
                // System.out.println("player " + opponent.col + " draw card!");
                opponentDrawnCard = game.getDeck().get(game.getDeck().size()-1);
                opponent.draw(game);
            }
            //important order to ensure that the drawn card of the current player is set
            if (to.getType() == FieldType.DRAW) {
                // System.out.println("player " + player.color + " draw card!");
                playerDrawnCard = game.getDeck().get(game.getDeck().size()-1);
                player.draw(game);
            }

        } else if (isStartMove) {
            //TODO assert figs in bank > 0
            // System.out.println("isStartMove move");
            // System.out.println("figs in bank before " + this.player.figuresInBank);
            Field to = player.getStartField(); //TODO maybe set the to field somewhere else
            playerFigure.setOnBench(false);
            playerFigure.setInHouse(false);

            if (!to.isEmpty()) {
                Player opponent = to.getFigure().getPlayerObjectByOwner(game);
                opponent.setFiguresInBank(opponent.getFiguresInBank() + 1);
                //set field of figure
                to.getFigure().setOnBench(true);
            }

            //set figure of field
            // player.getStartField().setFigure(figure); //get first figure not on field from player
            to.setFigure(playerFigure);
            this.player.setFiguresInBank(player.getFiguresInBank() - 1);

            //game.occupied[player.getPlayerId()] = true; //unused

            //TODO assert figcol == playercol
            // System.out.println("figs in bank " + this.player.figuresInBank);
            // System.out.println("fig col " + figure.color);
            // System.out.println("plyer col " + this.player.color);
        } else { //normal move
            assert to != null;
            if (!to.isEmpty()) {
                Player opponent = to.getFigure().getPlayerObjectByOwner(game);
                //Player opponent = game.getPlayerList().get(to.getFigure().getOwnerId());
                opponent.setFiguresInBank(opponent.getFiguresInBank() + 1);
                to.getFigure().setOnBench(true);
            }

            //unused
            //if (to.getType() == FieldType.HOUSE) {
            //   player.setHouseOccupationIndex(to.getFieldId());
            //}

            assert from != null;
            if (to.getType() == FieldType.HOUSE && from.getType() != FieldType.HOUSE) {
                player.setLastMoveCountFigureMovedIntoHouse(game.getMovesMade());
                from.getFigure().setInHouse(true);
                player.incFiguresInHouse();
            }

            //moving out of house not possible but
            // if (to.getType() != FieldType.HOUSE && from.getType() == FieldType.HOUSE) {
            // player.setFiguresInHouse(player.getFiguresInHouse() - 1);
            // from.getFigure().setInHouse(false);
            // }

            // playerFigure = from.getFigure();
            to.setFigure(playerFigure);
            from.setEmpty();

            if (to.getType() == FieldType.DRAW) {
                // System.out.println("player " + player.color + " karte ziehen!");
                playerDrawnCard = game.getDeck().get(game.getDeck().size()-1);
                player.draw(game);
            }

            //if (from.getType() == FieldType.START) {
            //    game.occupied[player.getPlayerId()] = false;
            //}
            //if (to.getType() == FieldType.START) {
            //    game.occupied[player.getPlayerId()] = true;
            //}
        }

        // System.out.println("before increasemoves");
        game.increaseMovesCounter(1);
        // game.nextPlayer();
        // System.out.println("cards num after 2 " + this.player.cards.size());
        return new UndoMove(this, playerFigure, opponentFigure,playerDrawnCard, opponentDrawnCard, lastCardOnPile, LastMoveCountFigureMovedIntoHouse);
    }

    /**
     * Prints information of the move
     */
    public void printMove() {
        System.out.print("card type " + this.cardUsed + " ");
        if (this.from != null) {
            System.out.print("from " + this.from.getFieldId() + " ");
        }
        if (this.to != null) {
            System.out.print("to " + this.to.getFieldId() + " ");
        }
        System.out.println("swap figs " + this.isSwapMove + " isStartMove " + this.isStartMove + " player.color " + this.player.getClientId());
    }

    /**
     * Gets the Field type
     *
     * @return Enum field type
     */
    public FieldType getFieldTypeTo() {
        assert this.to != null;
        return this.to.getType();
    }
}
