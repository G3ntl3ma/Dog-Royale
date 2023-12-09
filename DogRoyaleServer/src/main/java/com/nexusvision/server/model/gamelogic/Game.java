package com.nexusvision.server.model.gamelogic;

//import com.nexusvision.server.model.messages.menu.ReturnLobbyConfig;

import com.nexusvision.server.model.enums.CardType;
import com.nexusvision.server.model.enums.FieldType;
import com.nexusvision.server.model.enums.Penalty;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;

/**
 * This class represents the game and manages com.nexusvision.server.model.gamelogic.Player, Cards, Moves and more
 *
 * @author dgehse
 */

@Data
public final class Game {
    private final int figuresPerPlayer;
    private final int initialCardsPerPlayer;
    private final int maximumTotalMoves;
    // private final int maximumGameDuration; //in gamelobby class
    private final Penalty consequences;
    boolean[] occupied; //unused
    private ArrayList<Player> players;
    private Field[] board;
    private ArrayList<Card> deck;
    private ArrayList<Card> pile;
    private int mainFieldCount;
    private int playerToStartColor;
    private int playerToMoveId;
    private Card drawnCard;
    private ArrayList<Card> discardedCards;
    private int movesMade;
    private int playersRemaining;
    private int round; //round counter
    private boolean firstMoveOfRound;
    private int[] startIndexes; //indeces of startFields, unused

    /**
     * The Constructor initializes Games
     *
     * @param conf                  A String holding information about the board and players
     * @param figuresPerPlayer      An Integer representing the number of figures in the game
     * @param initialCardsPerPlayer An Integer representing the initial number of cards each player should have in their hand
     * @param maximumTotalMoves     An integer representing the maximum number of moves allowed this game
     * @param consequences          An object representing the consequences for illegal moves
     */
    public Game(String conf, int figuresPerPlayer, int initialCardsPerPlayer, int maximumTotalMoves, int consequences) {
        this.players = new ArrayList<>();
        this.deck = new ArrayList<>();
        this.pile = new ArrayList<>();
        this.playerToMoveId = 0;
        this.playerToStartColor = 0;
        this.movesMade = 0;
        this.maximumTotalMoves = maximumTotalMoves;
        this.figuresPerPlayer = figuresPerPlayer;
        this.initialCardsPerPlayer = initialCardsPerPlayer;
        this.round = 0;
        this.consequences = Penalty.values()[consequences];
        init(conf);
    }

    /**
     * Increases the value of movesMade by 1
     */
    public void increaseMovesCounter() {
        this.movesMade++;
    }

    /**
     * Add all cards from the pile and players hand to the deck and reshuffle
     */
    public void reshuffle() {
        // System.out.println("reshuffle start deck size " + this.deck.size() + " pile size " + this.pile.size());
        this.deck.addAll(this.pile);
        //throw away hand cards
        for (Player player : this.players) {
            this.deck.addAll(player.getCards());
            player.setCards(new ArrayList<>());
        }
        this.pile = new ArrayList<>();
        Collections.shuffle(this.deck);
        // System.out.println("reshuffle done deck size " + this.deck.size() + " pile size " + this.pile.size());
    }

    /**
     * Proper initialization for the next round considering excluded players and the starting player
     */
    public void reInit() { //for next round
        //assert not everyone is excluded
        this.playersRemaining = 0;
        for (Player player : this.players) {
            player.setOutThisRound(false);
            if (!player.isExcluded()) {
                this.playersRemaining++;
            }
        }
        int excludedCount = 0;
        do {
            this.playerToStartColor = (this.playerToStartColor + 1) % this.players.size();
            if (excludedCount >= this.players.size()) {
                //game over
                break;
            }
            excludedCount++;
        } while (!this.players.get(this.playerToStartColor).isExcluded());


        this.playerToMoveId = this.playerToStartColor;
        this.round++;
        // System.out.println(this.round);
    }

    /**
     * Discarding a player's hand considering the state of the pile
     */
    public ArrayList<Card> discardHandCards() {
        Player player = this.getCurrentPlayer();
        this.discardedCards = new ArrayList<>(player.getCards());
        if (!player.getCards().isEmpty()) {
            if (!this.pile.isEmpty()) {
                //keep the last card the same
                //pop
                Card pop = this.pile.remove(this.pile.size() - 1);
                this.pile.addAll(player.getCards());
                //read
                this.pile.add(pop);
            } else {
                this.pile.addAll(player.getCards());
            }
            player.setCards(new ArrayList<>());
        }
        return discardedCards;
    }

    /**
     * Distribute Cards to the players
     */
    public void distributeCards() {
        for (int i = 0; i < this.initialCardsPerPlayer; i++) {
            for (Player player : this.players) {
                player.draw(this);
            }
        }
        this.firstMoveOfRound = true;
    }

    /**
     * Print the current state of the game
     */
    public void printBoard() {
        System.out.println("BOARD=================");
        System.out.println("player to move " + this.playerToMoveId);
        for (Player p : this.players) {
            p.printInfo();
            p.printHouse();
        }
        for (int i = 0; i < this.mainFieldCount; i++) {
            System.out.print(i + "-");
        }
        System.out.println("");
        for (int i = 0; i < this.mainFieldCount; i++) {
            // Field f = this.board.get(i);
            Field f = this.board[i];
            System.out.print(f.getType() + "-");
        }
        System.out.println("");
        for (int i = 0; i < this.mainFieldCount; i++) {
            Field f = this.board[i];
            if (!f.isEmpty()) System.out.print(f.getFigure().getColor() + "-");
            else System.out.print("_" + "-");
        }
        System.out.println("");
        this.printTotalCards();
        System.out.println("===================");
    }

    /**
     * Set up the initial deck, defining types and quantities of the cards
     */
    public void initDeck() {
        for (int i = 0; i < 7; i++) {
            this.deck.add(new Card(CardType.card2));
            this.deck.add(new Card(CardType.card3));
            this.deck.add(new Card(CardType.plusMinus4));
            this.deck.add(new Card(CardType.card5));
            this.deck.add(new Card(CardType.card6));
            this.deck.add(new Card(CardType.oneToSeven));
            this.deck.add(new Card(CardType.card8));
            this.deck.add(new Card(CardType.card9));
            this.deck.add(new Card(CardType.card10));
            this.deck.add(new Card(CardType.card12));
            this.deck.add(new Card(CardType.swapCard));
            this.deck.add(new Card(CardType.copyCard));
        }

        for (int i = 0; i < 10; i++) {
            this.deck.add(new Card(CardType.startCard1));
            this.deck.add(new Card(CardType.startCard2));
        }
        for (int i = 0; i < 6; i++) {
            this.deck.add(new Card(CardType.magnetCard));
        }

        // Collections.shuffle(this.deck, new Random(666)); //deterministic seed
        Collections.shuffle(this.deck); //undeterministic
    }

    /**
     * Set up the initial game state, Count the numbers of players,
     * initialize board, Create fields and players,
     * connect fields and set up player starting positions and set house fields
     *
     * @param conf A String holding information about the board and players
     */
    private void init(String conf) { //set board and players, first move
        int players = 0;
        for (int i = 0; i < conf.length(); i++) {
            if (conf.charAt(i) == 's') players++;
        }

        int max = conf.length(); //TODO ??
        int fieldCount = conf.length();
        this.mainFieldCount = max;


        int totalFieldCount = fieldCount + figuresPerPlayer * players; //playerCount
        this.board = new Field[totalFieldCount];

        this.startIndexes = new int[players];
        this.occupied = new boolean[players];

        // System.out.println("conf string length " + max);

        for (int i = 0; i < max; i++) {
            this.board[i] = new Field(i, conf.charAt(i));
        }

        //add players Player
        for (int playerCol = 0; playerCol < players; playerCol++) {
            this.players.add(new Player(playerCol, figuresPerPlayer));
        }
        int seenStarts = 0;
        for (int i = 0; i < max; i++) {
            int prev = ((i - 1) + max) % max;
            int next = (i + 1) % max;

            this.board[i].setNext(this.board[next]);
            this.board[i].setPrev(this.board[prev]);
            // this.board[i].settype(conf.charAt(i));

            //TODO add house fields
            if (conf.charAt(i) == 's') {
                // this.players.get(seenStarts++).startField = this.board.get(i); //init starts
                this.startIndexes[seenStarts] = i;
                this.occupied[seenStarts] = false;
                this.players.get(seenStarts).setStartField(this.board[i]); //init starts
                int off = fieldCount;
                this.players.get(seenStarts).setHouseFirstIndex(fieldCount);
                for (int j = off; j < figuresPerPlayer + off; j++) {
                    // this.board.add(new Field(fieldCount++, 'h'));
                    this.board[fieldCount] = new Field(fieldCount, FieldType.HOUSE);

                    fieldCount++;
                }
                for (int j = off; j < figuresPerPlayer - 1 + off; j++) {
                    this.board[j].setNext(this.board[j + 1]);
                    // this.board[j+1].setPrev(this.board[j]); //house fields dont have prev field
                }
                seenStarts++;
                this.board[i].setHouse(this.board[off]);
                // this.board[off].setPrev(this.board[i]); //house fields dont have prev field
            }
        }
        this.playersRemaining = this.players.size();

    }

    /**
     * Retrieve the current player
     *
     * @return The current player
     */
    public Player getCurrentPlayer() {
        return this.players.get(this.playerToMoveId);
    }

    /**
     * Determine and set the next player
     *
     * @return true as long as there are remaining players
     */
    public boolean nextPlayer() {
        this.firstMoveOfRound = false;
        int count = 0;
        if (this.playersRemaining == 0) return false;
        //get next player who is not out yet if there is anyone
        do {
            this.playerToMoveId = (this.playerToMoveId + 1) % this.players.size();
            if (count >= this.players.size()) {
                return false;
            }
            count++;
        } while (getCurrentPlayer().isExcluded() || getCurrentPlayer().isOutThisRound());

        return true;
    }

    /**
     * Print information about Cards in total and in players hands
     */
    public void printTotalCards() {
        int handSum = 0;
        for (Player player : this.players) {
            handSum += player.getCards().size();
        }
        int totalSum = this.deck.size() + this.pile.size() + handSum;
        System.out.println("total cards in game " + totalSum + " deck " + this.deck.size() + " pile " + this.pile.size() + " hands " + handSum);
        if (totalSum != 110) {
            throw new RuntimeException("total sum of cards is " + totalSum + " instead of 110");
        }
    }

    /**
     * Compares 2 players based on criteria figures in house and
     * last move count when a figure was moved into the house
     *
     * @param p1 An object representing a player
     * @param p2 An object representing another player
     * @return An Integer representing which player is ahead in terms of figures in the house
     */
    public int compare(Player p1, Player p2) {
        if (p1.getFiguresInHouse() > p2.getFiguresInHouse()) return 1;
        if (p1.getFiguresInHouse() < p2.getFiguresInHouse()) return -1;
        if (p1.getLastMoveCountFigureMovedIntoHouse() < p2.getLastMoveCountFigureMovedIntoHouse()) return 1;
        if (p1.getLastMoveCountFigureMovedIntoHouse() > p2.getLastMoveCountFigureMovedIntoHouse()) return -1;
        //0 figures moved in
        return 0;
    }

    /**
     * Determines all the winners ordered from highest to lowest
     *
     * @return An ArrayList with all the winners
     */
    public ArrayList<Player> getOrder(ArrayList<Player> playerWinOrder) {
        //iterate through players in random order to make ties random order
        ArrayList<Player> randomOrderPlayers = new ArrayList<>(this.players);
        Collections.shuffle(randomOrderPlayers);
        for (Player randomOrderPlayer : randomOrderPlayers) {
            //find first 1, insert before
            boolean foundInx = false;
            for (int j = 0; j < playerWinOrder.size(); j++) {
                if (compare(playerWinOrder.get(j), randomOrderPlayer) == 1) {
                    playerWinOrder.add(j, randomOrderPlayer);
                    foundInx = true;
                    break;
                }
            }
            //if no 1 then append
            if (!foundInx) {
                playerWinOrder.add(randomOrderPlayer);
            }
        }
        return playerWinOrder;
    }


    public boolean checkOver() {
        for (Player player : this.players) {
            if (player.getFiguresInHouse() == this.figuresPerPlayer) {
                return true;
            }
        }

        if (this.movesMade >= this.maximumTotalMoves) return true;
        return !this.nextPlayer();
    }

    public ArrayList<Player> getWinners() {
        ArrayList<Player> playerWinOrder = new ArrayList<>();

        if (this.checkOver()) {
            getOrder(playerWinOrder);
            return playerWinOrder;
        }
        playerWinOrder.add(null);
        return playerWinOrder;
    }

    /**
     * Increments round by 1
     */
    public void incrementRound() {
        this.round++;
    }

    /**
     * Gets last card of the pile
     */
    public Card getLastCard() {
        return pile.get(pile.size() - 1);
    }

    /**
     * Determines if the round is over
     *
     * @return A Boolean, when true the round is over
     */
    public boolean roundOver() {
        return this.playersRemaining == 0;
    }

    /**
     * Get a specific field object in the board array
     *
     * @return An object representing the field at index 'inx'
     */
    public Field getField(int inx) {
        return this.board[inx];
    }

    /**
     * Checks for legal moves and retrieves moves if legal
     *
     * @param skip            A Boolean indicating whether to skip the move
     * @param card            An Enum representing the type of the card
     * @param selectedValue   An Integer representing a selected value for the move
     * @param pieceId         An Integer representing the id of the piece
     * @param isStarter       A Boolean indicating whether the move is the starting move
     * @param opponentPieceId An optional Integer representing the identifier of the opponent's piece
     * @return An object representing the Move to be executed, null if no cards left or illegal move
     */
    public Move getMove(boolean skip, CardType card, int selectedValue,
                        int pieceId, boolean isStarter, Integer opponentPieceId) {
        Player player = this.getCurrentPlayer();
        if (card == null || skip) return null;
        //check if cardType in cards
        Card foundCard = null;
        for (int i = 0; i < player.getCards().size(); i++) {
            if (player.getCards().get(i).getType() == card) {
                foundCard = player.getCards().get(i);
                break;
            }
        }
        if (foundCard == null) return null;
        Move move = foundCard.getMove(this, selectedValue, pieceId, isStarter, opponentPieceId, player);
        //for m in legalMoves see if functionally the same
        ArrayList<Move> moves = new ArrayList<>();
        player.generateMoves(this, moves); //TODO only need to gen moves for a particular card type
        for (Move value : moves) {
            if (value.equal(move)) {
                return move;
            }
        }
        return null;
    }

    /**
     * Attempts to execute a move
     *
     * @param skip            A Boolean indicating whether to skip the move
     * @param cardOrdinal     An Enum representing the type of the card
     * @param selectedValue   An Integer representing a selected value for the move
     * @param pieceId         An Integer representing the id of the piece
     * @param isStarter       A Boolean indicating whether the move is the starting move
     * @param opponentPieceId An optional Integer representing the identifier of the opponent's piece
     * @return A Boolean indicating if move was legal or not
     */
    public boolean tryMove(boolean skip, int cardOrdinal, int selectedValue,
                           int pieceId, boolean isStarter, Integer opponentPieceId) {
        CardType card = CardType.values()[cardOrdinal];
        Move move = getMove(skip, card, selectedValue, pieceId, isStarter, opponentPieceId);
        if (move == null) {
            handleIllegalMove();
            return false;
        } else {
            move.execute(this);
            return true;
        }
    }

    /**
     * Excludes a specific player from the round
     *
     * @param player An object representing the player to exclude from the round
     */
    public void excludeFromRound(Player player) { //return cards??
        this.playersRemaining--;
        this.discardHandCards(); //of current player
        // this.nextPlayer();
    }

    /**
     * Excludes a specific player from the game
     *
     * @param player An object representing the player to exclude from the game
     */
    public void excludeFromGame(Player player) {
        player.setExclude();
        this.playersRemaining--;
        this.discardHandCards();
        //TODO throw away cards
    }

    /**
     * Handles illegal move and applies penalties
     */
    public void handleIllegalMove() {
        if (consequences == Penalty.kickFromGame) {
            excludeFromGame(this.getCurrentPlayer());
        } else if (consequences == Penalty.excludeFromRound) {
            excludeFromRound(this.getCurrentPlayer());
        } else {
        }
    }

    /**
     * Calculates position of a player's figure in the house
     *
     * @param playerId An Integer representing Id of the player
     * @param pieceId  An Integer representing Id of a figure
     * @return An Integer indicating the position of the figure in the house
     */
    public Integer getHousePosition(int playerId, int pieceId) {
        Figure f = this.players.get(playerId).getFigures().get(pieceId);
        if (!f.isInHouse()) return null;
        return this.players.size() - (f.getField().getVal() - this.players.get(playerId).getHouseFirstIndex()) + 1;
    }
}

