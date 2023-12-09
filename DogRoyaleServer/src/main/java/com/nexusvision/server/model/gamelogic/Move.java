package com.nexusvision.server.model.gamelogic;

import com.nexusvision.server.model.enums.FieldType;

/**
 * This class generates Moves
 *
 * @author dgehse
 */
public final class Move {

    private final Field from;
    private final Field to;
    private final boolean isSwapMove;
    private final boolean isStartMove; //ignore from field just put a fig on isStartMove
    private final Player player;
    private final Card cardUsed;

    /**
     * Constructor for the Move
     *
     * @param player     An object representing the player
     * @param from       An object representing the field the move starts
     * @param to         An object representing the field the move ends
     * @param isSwapMove A Boolean indicating if it is a swap move or not
     * @param cardUsed   An object representing the card used for the move
     */
    public Move(Player player, Field from, Field to, boolean isSwapMove, Card cardUsed) {
        this.player = player;
        this.from = from;
        this.to = to;
        this.isSwapMove = isSwapMove;
        this.isStartMove = false;
        this.cardUsed = cardUsed;
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
        this.to = null;
        this.isSwapMove = false;
    }

    /**
     * Checks if the move equals another move
     *
     * @return A Boolean, which is true when the moves are equal
     */
    public boolean equal(Move move) {
        //TODO readability
        if (this.from == move.from && this.to == move.to && this.isSwapMove == move.isSwapMove && this.isStartMove == move.isStartMove &&
                this.player == move.player && this.cardUsed == move.cardUsed) {
            return true;
        }

        if (this.from == null || move.from == null || this.to == null || move.to == null) {
            return this.from == null && move.from == null && this.to == null && move.to == null &&
                    this.isSwapMove == move.isSwapMove && this.isStartMove == move.isStartMove
                    && this.player.getPlayerId() == move.player.getPlayerId() && this.cardUsed.getType() == move.cardUsed.getType();
        }


        return this.from.getVal() == move.from.getVal() && this.to.getVal() == move.to.getVal() &&
                this.isSwapMove == move.isSwapMove && this.isStartMove == move.isStartMove
                && this.player.getPlayerId() == move.player.getPlayerId() && this.cardUsed.getType() == move.cardUsed.getType();
    }

    /**
     * Executes a move, removing and adding cards, if it is a swap move execute it,
     * if it is a start move execute it
     * otherwise execute a normal move(updating figures, handling house, draw cards ect.)
     *
     * @param game An object representing the game
     */
    public void execute(Game game) {
        game.setDrawnCard(null);
        // System.out.println("cards num before " + this.player.cards.size());
        if (this.cardUsed != null) {
            player.getCards().remove(this.cardUsed);
            game.getPile().add(this.cardUsed);
        }
        // System.out.println("cards num after 1 " + this.player.cards.size());
        if (isSwapMove) {
            assert to != null;
            Figure temp = to.getFigure();
            Player opponent = game.getPlayers().get(to.getFigure().getColor());
            //set figure of field
            assert from != null;
            to.setFigure(from.getFigure());
            from.setFigure(temp);

            //set field of figures
            to.getFigure().setField(to);
            from.getFigure().setField(from);


            if (from.getType() == FieldType.DRAW) {
                // System.out.println("player " + opponent.col + " draw card!");
                opponent.draw(game);
            }
            //important order to ensure that the drawn card of the current player is set
            if (to.getType() == FieldType.DRAW) {
                // System.out.println("player " + player.color + " draw card!");
                player.draw(game);
            }

        } else if (isStartMove) {
            //TODO assert figs in bank > 0
            // System.out.println("isStartMove move");
            // System.out.println("figs in bank before " + this.player.figuresInBank);
            Figure figure = this.player.getFirstFigureInBank();
            Field to = player.getStartField();
            figure.setOnBench(false);
            figure.setInHouse(false);

            if (!to.isEmpty()) {
                Player opponent = game.getPlayers().get(to.getFigure().getColor());
                opponent.setFiguresInBank(opponent.getFiguresInBank() + 1);
                //set field of figure
                to.getFigure().setOnBench(true);
            }

            //set figure of field
            player.getStartField().setFigure(figure); //get first figure not on field from player
            this.player.setFiguresInBank(player.getFiguresInBank() - 1);

            game.occupied[player.getPlayerId()] = true;

            //TODO assert figcol == playercol
            // System.out.println("figs in bank " + this.player.figuresInBank);
            // System.out.println("fig col " + figure.color);
            // System.out.println("plyer col " + this.player.color);
        } else { //normal move
            assert to != null;
            if (!to.isEmpty()) {
                Player opponent = game.getPlayers().get(to.getFigure().getColor());
                opponent.setFiguresInBank(opponent.getFiguresInBank() + 1);
                to.getFigure().setOnBench(true);
            }

            if (to.getType() == FieldType.HOUSE) {
                player.setHouseOccupationIndex(to.getVal());
            }

            assert from != null;
            if (to.getType() == FieldType.HOUSE && from.getType() != FieldType.HOUSE) {
                player.setFiguresInHouse(player.getFiguresInHouse() + 1);
                player.setLastMoveCountFigureMovedIntoHouse(game.getMovesMade());
                from.getFigure().setInHouse(true);
            }

            //possible??
            if (to.getType() != FieldType.HOUSE && from.getType() == FieldType.HOUSE) {
                player.setFiguresInHouse(player.getFiguresInHouse() - 1);
                from.getFigure().setInHouse(false);
            }

            // from.empty() = true;
            to.setFigure(from.getFigure());
            from.setEmpty();


            if (to.getType() == FieldType.DRAW) {
                // System.out.println("player " + player.color + " karte ziehen!");
                player.draw(game);
            }

            if (from.getType() == FieldType.START) {
                game.occupied[player.getPlayerId()] = false;
            }
            if (to.getType() == FieldType.START) {
                game.occupied[player.getPlayerId()] = true;
            }
        }

        game.increaseMovesCounter();
        // game.nextPlayer();
        // System.out.println("cards num after 2 " + this.player.cards.size());
    }

    /**
     * Prints information of the move
     */
    public void printmove() {
        System.out.print("card type " + this.cardUsed.getType() + " ");
        if (this.from != null) {
            System.out.print("from " + this.from.getVal() + " ");
        }
        if (this.to != null) {
            System.out.print("to " + this.to.getVal() + " ");
        }
        System.out.println("swap figs " + this.isSwapMove + " isStartMove " + this.isStartMove + " player.color " + this.player.getPlayerId());
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
