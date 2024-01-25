package com.nexusvision.server.model.gamelogic;

//import com.nexusvision.server.model.messages.menu.ReturnLobbyConfig;

import com.nexusvision.server.model.enums.Card;
import com.nexusvision.server.model.enums.FieldType;
import com.nexusvision.server.model.utils.PlayerElement;
import com.nexusvision.server.service.CardService;
import com.nexusvision.server.service.KickService;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


/**
 * This class represents the game and manages TournamentPlayer, Cards, Moves and more
 *
 * @author dgehse
 */

@Log4j2
@Data // TODO: Should this really be @Data??
public final class Game {
    private final LobbyConfig lobbyConfig;
    private final int lobbyId;

    private boolean[] occupied; //unused
    private ArrayList<Player> playerList;
    private Field[] board;
    private ArrayList<Card> deck;
    private ArrayList<Card> pile;
    private int mainFieldCount;
    private int playerToStartColor;
    private int playerToMoveId;
    private Card drawnCard; // for server communication
    private ArrayList<Card> discardedCardList; // for server communication
    private int movesMade;
    private int playersRemaining;
    private int round; //round counter
    private boolean firstMoveOfRound;
    private int[] startIndexes; //indexes of startFields, unused

    private CardService cardService;
    private KickService kickService;

    /**
     * The constructor initializes games
     *
     * @param lobbyConfig The lobby config
     * @param lobbyId The lobbyId
     */
    public Game(LobbyConfig lobbyConfig, int lobbyId) {
        this.lobbyConfig = lobbyConfig;
        this.lobbyId = lobbyId;
        this.playerList = new ArrayList<>();
        this.deck = new ArrayList<>();
        this.pile = new ArrayList<>();
        this.playerToMoveId = 0; // TODO: Should this really be the first player? Check please
        this.playerToStartColor = 0;
        this.movesMade = 0;
        this.round = 0;
        this.cardService = new CardService(null);
        this.kickService = new KickService();
        //constructing a string that can be parsed by the game
        String conf = buildConf();
        init(conf);
    }

    private String buildConf() {
        StringBuilder fieldStringBuild = new StringBuilder();
        fieldStringBuild.append("n".repeat(Math.max(0, lobbyConfig.getFieldSize())));

        for (int i = 0; i < lobbyConfig.getStartFields().getPositions().size(); i++) {
            fieldStringBuild.setCharAt(i, 's');
        }

        for (int i = 0; i < lobbyConfig.getDrawCardFields().getPositions().size(); i++) {
            fieldStringBuild.setCharAt(i, 'k');
        }

        return fieldStringBuild.toString();
    }

    /**
     * Increases the value of movesMade by 1
     */
    public void increaseMovesCounter(int arg) {
        this.movesMade += arg;
    }

    /**
     * Add all cards from the pile and players hand to the deck and reshuffle
     */
    public void reshuffle() {
        // System.out.println("reshuffle start deck size " + this.deck.size() + " pile size " + this.pile.size());
        deck.addAll(pile);
        //throw away hand cards
        for (Player player : playerList) {
            deck.addAll(player.getCardList());
            player.setCardList(new ArrayList<>());
        }
        pile = new ArrayList<>();
        Collections.shuffle(deck);
        // System.out.println("reshuffle done deck size " + this.deck.size() + " pile size " + this.pile.size());
    }

    /**
     * shuffle all variables that are unknown to the player
     */
    public void shuffleUnknown(Player player) { // TODO: Check this method
        Collections.shuffle(deck);
        for (Player opponent : this.playerList) {
            if (opponent != player) {
                // Collections.shuffle(opponent.getCardList());
            }
        }
    }

    /**
     * Proper initialization for the next round considering excluded players and the starting player
     */
    public void reInit() { //for next round
        //assert not everyone is excluded
        for (Player player : playerList) {
            player.setOutThisRound(false);
        }
        int excludedCount = 0;
        // TODO: What if game over
        playerToMoveId = (playerToStartColor + 1) % playerList.size(); // TODO: Check if this is correct
        round++;
        // System.out.println(this.round);
    }

    /**
     * Discarding the current player's hand considering the state of the pile
     */
    public ArrayList<Card> discardHandCards() {
        Player player = getCurrentPlayer();
        discardedCardList = new ArrayList<>(player.getCardList());
        if (!player.getCardList().isEmpty()) {
            if (!pile.isEmpty()) {
                //keep the last card the same
                //pop
                Card pop = pile.remove(pile.size() - 1);
                pile.addAll(player.getCardList());
                //read
                pile.add(pop);
            } else {
                pile.addAll(player.getCardList());
            }
            player.setCardList(new ArrayList<>());
        }
        return discardedCardList;
    }

    /**
     * Distribute Cards to the players
     */
    public void distributeCards() {
        for (int i = 0; i < lobbyConfig.getInitialCardsPerPlayer(); i++) {
            for (Player player : playerList) {
                player.draw(this);
            }
        }
        firstMoveOfRound = true;
    }

    /**
     * Print the current state of the game
     */
//    public void printBoard() { // TODO: Fix prints
//        System.out.println("BOARD=================");
//        System.out.println("player to move " + playerToMoveId);
//        System.out.println("players remaining " + playersRemaining);
//        for (TournamentPlayer p : playerList) {
//            p.printInfo();
//            p.printHouse();
//        }
//        for (int i = 0; i < this.mainFieldCount; i++) {
//            System.out.print(i + "-");
//        }
//        System.out.println();
//        for (int i = 0; i < mainFieldCount; i++) {
//            // Field f = this.board.get(i);
//            Field f = board[i];
//            System.out.print(f.getType().toString().charAt(0) + "-");
//        }
//        System.out.println();
//        for (int i = 0; i < mainFieldCount; i++) {
//            Field f = this.board[i];
//            if (!f.isEmpty()) System.out.print(f.getFigure().getOwnerId() + "-");
//            else System.out.print("_" + "-");
//        }
//        System.out.println();
//        printTotalCards();
//        System.out.println("===================");
//    }

    /**
     * Set up the initial deck, defining types and quantities of the cards
     */
    public void initDeck() {
        for (int i = 0; i < 7; i++) {
            this.deck.add(Card.card2);
            this.deck.add(Card.card3);
            this.deck.add(Card.plusMinus4);
            this.deck.add(Card.card5);
            this.deck.add(Card.card6);
            this.deck.add(Card.oneToSeven);
            this.deck.add(Card.card8);
            this.deck.add(Card.card9);
            this.deck.add(Card.card10);
            this.deck.add(Card.card12);
            this.deck.add(Card.swapCard);
            this.deck.add(Card.copyCard);
        }

        for (int i = 0; i < 10; i++) {
            this.deck.add(Card.startCard1);
            this.deck.add(Card.startCard2);
        }
        for (int i = 0; i < 6; i++) {
            this.deck.add(Card.magnetCard);
        }

        // Collections.shuffle(this.deck, new Random(666)); //deterministic seed
        Collections.shuffle(this.deck); // not deterministic
    }

    /**
     * Set up the initial game state, count the numbers of players,
     * initialize board, create fields and players,
     * connect fields and set up player starting positions and set house fields
     *
     * @param conf A String holding information about the board and players
     */
    private void init(String conf) { //set board and players, first move
        int players = 0;
        for (int i = 0; i < conf.length(); i++) {
            if (conf.charAt(i) == 's') players++;
        }

        int fieldCount = conf.length();

        System.out.println("fieldCount" + fieldCount);
        this.mainFieldCount = fieldCount;

        int totalFieldCount = fieldCount + lobbyConfig.getFiguresPerPlayer() * players; //playerCount
        this.board = new Field[totalFieldCount];

        this.startIndexes = new int[players];
        this.occupied = new boolean[players];

        // System.out.println("conf string length " + max);

        for (int i = 0; i < fieldCount; i++) {
            this.board[i] = new Field(i, conf.charAt(i));
        }

        //add players
        List<Integer> playerOrder = lobbyConfig.getPlayerOrder().getClientIdList();
        for (int playerOrderIndex = 0; playerOrderIndex < playerOrder.size(); playerOrderIndex++) {
            this.playerList.add(new Player(playerOrder.get(playerOrderIndex), lobbyConfig.getFiguresPerPlayer()));
        }
        int seenStarts = 0;
        for (int i = 0; i < conf.length(); i++) {
            int prev = ((i - 1) + conf.length()) % conf.length();
            int next = (i + 1) % conf.length();
            // System.out.println("iter"+i);
            this.board[i].setNext(this.board[next]);
            this.board[i].setPrev(this.board[prev]);
            // this.board[i].setType(conf.charAt(i));

            if (conf.charAt(i) == 's') {
                // this.players.get(seenStarts++).startField = this.board.get(i); //init starts
                this.startIndexes[seenStarts] = i;
                this.occupied[seenStarts] = false;
                this.playerList.get(seenStarts).setStartField(this.board[i]); //init starts
                int off = fieldCount;
                this.playerList.get(seenStarts).setHouseFirstIndex(fieldCount);
                for (int j = off; j < lobbyConfig.getFiguresPerPlayer() + off; j++) {
                    this.board[fieldCount++] = new Field(fieldCount, FieldType.HOUSE);
                }
                for (int j = off; j < lobbyConfig.getFiguresPerPlayer() - 1 + off; j++) {
                    this.board[j].setNext(this.board[j + 1]);
                    // this.board[j+1].setPrev(this.board[j]); //house fields don't have prev field
                }
                seenStarts++;
                this.board[i].setHouse(this.board[off]);
                // this.board[off].setPrev(this.board[i]); //house fields don't have prev field
            }
        }
        this.playersRemaining = this.playerList.size();
    }

    /**
     * Retrieve the current player
     *
     * @return The current player
     */
    public Player getCurrentPlayer() {
        return playerList.get(playerToMoveId);
    }

    /**
     * Determine and set the next player
     *
     * @return true as long as there are remaining players
     */
    public boolean nextPlayer() {
        firstMoveOfRound = false;
        int count = 0;
        if (playersRemaining == 0) return false;
        // System.out.println("playersRemaining " + playersRemaining);
        //get next player who is not out yet if there is anyone
        do {
            playerToMoveId = (playerToMoveId + 1) % playerList.size();
            if (count >= playerList.size()) {
                System.out.println("BUG: UNREACHABLE");
                System.exit(423);
                return false; //unreachable
            }
            count++;
        } while (getCurrentPlayer().isOutThisRound());

        return true;
    }

    /**
     * Print information about Cards in total and in players hands
     */
    public void printTotalCards() {
        int handSum = 0;
        for (Player player : playerList) {
            handSum += player.getCardList().size();
        }
        int totalSum = deck.size() + pile.size() + handSum;
        System.out.println("total cards in game " + totalSum + " deck " + deck.size() + " pile " + pile.size() + " hands " + handSum);
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
    public ArrayList<Integer> getWinnerOrder() {
        //iterate through players in random order to make ties random order
        ArrayList<Player> randomOrderPlayers = new ArrayList<>(playerList);
        Collections.shuffle(randomOrderPlayers);

        ArrayList<Player> playerWinOrder = new ArrayList<>();
        ArrayList<Integer> playerIdWinOrder = new ArrayList<>();
        for (Player player : randomOrderPlayers) {
            //find first 1, insert before
            boolean inserted = false;
            for (int i = 0; i < playerWinOrder.size(); i++) {
                if (compare(playerWinOrder.get(i), player) == 1) {
                    playerWinOrder.add(i, player);
                    inserted = true;
                    break;
                }
            }
            //if no 1 then append
            if (!inserted) {
                playerWinOrder.add(player);
            }
        }
        return null; // (ArrayList<Integer>) playerWinOrder.stream().map(TournamentPlayer::getPlayerId).collect(Collectors.toList()); // TODO: This doesn't work
    }

    /**
     * True if the game is over
     *
     * @return true if the game is over
     */
    public boolean checkGameOver() {
        for (Player player : playerList) {
            if (player.getFiguresInHouse() == lobbyConfig.getFiguresPerPlayer()) {
                // System.out.println("game over: full house");
                return true;
            }
        }

        if (movesMade >= lobbyConfig.getMaximumTotalMoves()) {
            // System.out.println("game over: maximumtotalmoves reached");
            return true;
        }
        return false;
        // boolean noNext = !this.nextPlayer();
        // if (noNext) {
        //     System.out.println("game over: no next player");
        // }
        // return noNext;
    }

    // Eventuell nicht nötig, da man sowieso erst einmal checken muss, ob das Spiel zu Ende ist bevor man
    // sich winner holen möchte
//    public ArrayList<TournamentPlayer> getWinners() {
//        ArrayList<TournamentPlayer> playerWinOrder;
//
//        if (checkGameOver()) {
//            playerWinOrder = getWinnerOrder();
//            return playerWinOrder;
//        }
//        playerWinOrder.add(null);
//        return playerWinOrder;
//    }

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
        int playersRemaining2 = 0;
        for (Player player : this.playerList) {
            if (!player.isOutThisRound()) playersRemaining2++;
        }
        if (playersRemaining2 != playersRemaining) {
            System.out.println("inkonsistent gamestate playersremaining"); // TODO: Why is this German?!?!
            System.exit(235);
        }
        return playersRemaining == 0;
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
     * @param skip            A boolean indicating whether to skip the move
     * @param card            An enum representing the type of the card
     * @param selectedValue   An integer representing a selected value for the move
     * @param pieceId         An integer representing the id of the piece
     * @param isStarter       A boolean indicating whether the move is the starting move
     * @param opponentPieceId An optional integer representing the identifier of the opponent's piece
     * @return An object representing the move to be executed, null if no cards left or illegal move
     */
    public Move getMove(boolean skip, Card card, int selectedValue,
                        int pieceId, boolean isStarter, Integer opponentPieceId) {
        cardService.setType(card);
        Player player = getCurrentPlayer();
        if (card == null || skip) return null;
        //check if cardType in cards
        Card foundCard = null;
        for (int i = 0; i < player.getCardList().size(); i++) {
            if (player.getCardList().get(i) == card) {
                foundCard = player.getCardList().get(i);
                break;
            }
        }
        if (foundCard == null) return null;
        cardService.setType(foundCard);
        Move move = cardService.getMove(this, selectedValue, pieceId, isStarter, opponentPieceId, player);
        //for m in legalMoves see if functionally the same
        ArrayList<Move> moves = player.generateMoves(this); //TODO only need to gen moves for a particular card type
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
        Card card = Card.getType(cardOrdinal);
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
        if (player.isOutThisRound()) {
            System.out.println("error trying to exclude a player who is already out");
            System.exit(40);
        }
        playersRemaining--;
        player.setOutThisRound(true);
        discardHandCards(); //of current player
        // this.nextPlayer();
    }

    /**
     * Excludes a specific player from the game
     *
     * @param player An object representing the player to exclude from the game
     */
    public void excludeFromGame(Player player) {
        if (player.isOutThisRound()) {
            log.error("error trying to exclude a player from game who is already out");
            System.exit(41);
        }
        kickService.kick(lobbyConfig, getClientIdFromPlayer(player), lobbyId);
        playerList.remove(player);

        this.playersRemaining--;
        this.discardHandCards();
        //TODO Send message to throw away cards
    }

    private Integer getClientIdFromPlayer(Player player) {
        return lobbyConfig.getPlayerOrder().getOrder().get(playerList.indexOf(player)).getClientId();
    }

    /**
     * Handles illegal move and applies penalties
     */
    public void handleIllegalMove() {
        switch (lobbyConfig.getConsequencesForInvalidMove()) {
            case kickFromGame:
                excludeFromGame(this.getCurrentPlayer());
                break;
            case excludeFromRound:
                excludeFromRound(this.getCurrentPlayer());
                break;
        }
    }

    /**
     * Calculates position of a player's figure in the house
     *
     * @param playerId An Integer representing id of the player
     * @param pieceId  An Integer representing id of a figure
     * @return An Integer indicating the position of the figure in the house
     */
    public Integer getHousePosition(int playerId, int pieceId) {
        Figure f = this.playerList.get(playerId).getFigureList().get(pieceId);
        if (!f.isInHouse()) return null;
        return this.playerList.size() - (f.getField().getFieldId() - this.playerList.get(playerId).getHouseFirstIndex()) + 1;
    }

    public void removePlayerFromBoard(Player player) {
        //don't touch figures in house for winner order
        for (Figure figure : player.getFigureList()) {
            figure.setField(null);
        }
        for (Field field : this.board) {
            field.setFigure(null);
        }
    }

    public Move getRandomMove() {
        ArrayList<Move> moves = getCurrentPlayer().generateMoves(this);
        if (moves.isEmpty()) return null;
        return moves.get(0);
    }

    public ArrayList<Move> getMoves() {
        return this.getCurrentPlayer().generateMoves(this);
    }

    public ArrayList<Integer> hash() {
        //get hash of board
        ArrayList<Integer> deckValues = this.deck.stream()
                .map(card -> card.ordinal())
                .sorted()
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);

        ArrayList<Integer> boardValues = Arrays.stream(this.board)
                .map(field -> field.hash())
                .sorted()
                .collect(Collectors.toCollection(ArrayList::new));

        ArrayList<Integer> pileValues = this.pile.stream()
                .map(card -> card.ordinal())
                .sorted()
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);

        ArrayList<Integer> playerValues = this.playerList.stream()
                .map(player -> player.hash())
                .sorted()
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);

        ArrayList<Integer> variables = new ArrayList<>();
        variables.add(mainFieldCount);
        variables.add(playerToStartColor);
        variables.add(playerToMoveId);
        variables.add(movesMade);
        variables.add(playersRemaining);
        variables.add(round);
        variables.add(firstMoveOfRound ? 0 : 1);
        // if (drawnCard != null) {
        //     variables.add(drawnCard.ordinal());
        // }
        // else {
        //     variables.add(-1);
        // }
        variables.add(playerValues.hashCode());
        variables.add(deckValues.hashCode());
        variables.add(pileValues.hashCode());
        variables.add(boardValues.hashCode());
        // System.out.println("variables for hashing" + variables);

        return variables;
    }

    public void makeMove(Move move) {
        if (move == null) {
            System.out.println("exclude");
            excludeFromRound(getCurrentPlayer());
            return;
        }
        System.out.println("EXECUTE");
        move.execute(this);
    }

}

