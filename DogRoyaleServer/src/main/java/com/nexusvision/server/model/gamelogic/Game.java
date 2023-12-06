package com.nexusvision.server.model.gamelogic;

//import com.nexusvision.server.model.messages.menu.ReturnLobbyConfig;
import com.nexusvision.server.model.enums.CardType;
import com.nexusvision.server.model.enums.FieldType;
import com.nexusvision.server.model.enums.Penalty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public final class Game {

    ArrayList<Player> players; //TODO should not be arraylist
    private Field[] board;
    ArrayList<Card> deck;
    ArrayList<Card> pile;
    private final int figureCount;
    private final int initialHandCardCount;
    private int mainFieldCount;
    private int playerToStartColor;
    private int playerToMoveColor;
    final int maximumTotalMoves;
    int movesMade;
    int playersRemaining;
    private int round; //round counter
    
    // private final int maximumGameDuration; //in gamelobby class
    private final Penalty consequences;

    private int[] startIndexs; //indeces of startFields, unused
    boolean[] occupied; //unused
    
    public Game(String conf, int figureCount, int initialHandCardCount, int maximumTotalMoves ,int consequences) {
	this.players = new ArrayList<>();
	this.deck = new ArrayList<>();
	this.pile = new ArrayList<>();
	this.playerToMoveColor = 0;
	this.playerToStartColor = 0;
	this.movesMade = 0;
	this.maximumTotalMoves = maximumTotalMoves;
	this.figureCount = figureCount;
	this.initialHandCardCount = initialHandCardCount;
	this.round = 0;
	this.consequences = Penalty.values()[consequences];
	init(conf);
    }
    
    public void increaseMovesCounter() {
	this.movesMade++;
    }

    public void reshuffle() {
	// System.out.println("reshuffle start deck size " + this.deck.size() + " pile size " + this.pile.size());
	this.deck.addAll(this.pile);
	//throw away hand cards
	for (int i = 0; i < this.players.size(); i++) {
	    this.deck.addAll(this.players.get(i).cards);
	    this.players.get(i).cards = new ArrayList<>();
	}
	this.pile = new ArrayList<>();
	Collections.shuffle(this.deck);
	// System.out.println("reshuffle done deck size " + this.deck.size() + " pile size " + this.pile.size());	
    }

    public void reinit() { //for next round
	//assert not everyone is excluded
	this.playersRemaining = 0;
	for (int i = 0; i < this.players.size(); i++) {
	    this.players.get(i).outThisRound = false;
	    if(!this.players.get(i).excluded) {
		this.playersRemaining++;
	    }
	}
	int excludedCount = 0;
	do {
	    this.playerToStartColor = (this.playerToStartColor + 1) % this.players.size();
	    if(excludedCount >= this.players.size()) {
		//game over
		break;
	    }
	    excludedCount++;
	} while(!this.players.get(this.playerToStartColor).excluded);
       

	this.playerToMoveColor = this.playerToStartColor;
	this.round++;
	// System.out.println(this.round);
    }

    public void discardHandCards() {
	Player player = this.getCurrentPlayer();
	if (player.cards.size() > 0) {
	    if (this.pile.size() > 0) {
		//keep the last card the same
		//pop
		Card pop = this.pile.remove(this.pile.size() - 1);
		this.pile.addAll(player.cards);
		//readd
		this.pile.add(pop);
	    }
	    else {
		this.pile.addAll(player.cards);
	    }
	    player.cards = new ArrayList<>();
	}
    }

    public void distributeCards() {
	for (int i = 0; i < this.initialHandCardCount; i++) {
	    for (int j = 0; j < this.players.size(); j++) {
		this.players.get(j).draw(this);
	    }
	}
    }

    public void printBoard() {
	System.out.println("BOARD=================");
	System.out.println("player to move " + this.playerToMoveColor);
	for (int i = 0; i < this.players.size(); i++) {
	    Player p = this.players.get(i);
	    p.printInfo();
	    p.printHouse();
	}
	for (int i = 0; i < this.mainFieldCount; i++) {
	    System.out.print(i+"-");
	}
	System.out.println("");
	for (int i = 0; i < this.mainFieldCount; i++) {
	    // Field f = this.board.get(i);
	    Field f = this.board[i];
	    System.out.print(f.type + "-");
	}
	System.out.println("");
	for (int i = 0; i < this.mainFieldCount; i++) {
	    Field f = this.board[i];
	    if(!f.isEmpty()) System.out.print(f.figure.color+"-");
	    else         System.out.print("_"+"-");
	}
	System.out.println("");
	this.printTotalCards();
	System.out.println("===================");
    }

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

    private void init(String conf) { //set board and players
	int players = 0;
	for (int i = 0; i < conf.length(); i++) {
	    if (conf.charAt(i) == 's') players++;
	}
	
	int max = conf.length(); //TODO ??
	int fieldcount = conf.length();
	this.mainFieldCount = max;


	int totalfieldcount = fieldcount + figureCount * players; //playercount
	this.board = new Field[totalfieldcount];

	this.startIndexs = new int[players];
	this.occupied = new boolean[players];

	// System.out.println("conf string length " + max);
	
	for (int i = 0; i < max; i++) {
	    this.board[i] = new Field(i, conf.charAt(i));
	}

	//add players Player
	for (int plyrcol = 0; plyrcol < players; plyrcol++) { 
	    this.players.add(new Player(plyrcol, figureCount));
	}
	int seenstarts = 0;
	for (int i = 0; i < max; i++) {
	    int prev = ((i - 1) + max) % max;
	    int next = (i + 1) % max;
	    
	    this.board[i].setNext(this.board[next]);
	    this.board[i].setPrev(this.board[prev]);
	    // this.board[i].settype(conf.charAt(i));
	    
	    //TODO add house fields
	    if (conf.charAt(i) == 's') {
		// this.players.get(seenstarts++).startField = this.board.get(i); //init starts
		this.startIndexs[seenstarts] = i;
		this.occupied[seenstarts] = false;
		this.players.get(seenstarts).startField = this.board[i]; //init starts
		int off = fieldcount;
		this.players.get(seenstarts).houseFirstIndex = fieldcount;
		for (int j = off; j < figureCount + off; j++) {
		    // this.board.add(new Field(fieldcount++, 'h'));
		    this.board[fieldcount] = new Field(fieldcount, FieldType.HOUSE);

		    fieldcount++;
		}
		for (int j = off; j < figureCount-1 + off; j++) {
		    this.board[j].setNext(this.board[j+1]);
		    // this.board[j+1].setPrev(this.board[j]); //house fields dont have prev field
		}
		seenstarts++;
		this.board[i].setHouse(this.board[off]);
		// this.board[off].setPrev(this.board[i]); //house fields dont have prev field
	    }
	}
	this.playersRemaining = this.players.size();
	
    }

    public Player getCurrentPlayer() {
	return this.players.get(this.playerToMoveColor);
    }

    public boolean nextPlayer() {
	int count = 0;
	if(this.playersRemaining == 0) return false;
	//get next player who is not out yet if there is anyone
	do {
 	    this.playerToMoveColor = (this.playerToMoveColor + 1) % this.players.size();
	    if(count >= this.players.size()) {
		return false;
	    }
	    count++;
	} while(getCurrentPlayer().excluded || getCurrentPlayer().outThisRound);

	return true;
    }

    public void printTotalCards() {
	int handsum = 0;
	for (int i = 0; i < this.players.size(); i++) {
	    handsum += this.players.get(i).cards.size();
	}
	int totalsum = this.deck.size() + this.pile.size() + handsum;
	System.out.println("total cards in game " + totalsum + " deck " + this.deck.size() + " pile " + this.pile.size() + " hands " + handsum);
	if(totalsum != 110) {
	    throw new RuntimeException("total sum of cards is " + totalsum + " instead of 110");
	}
    }

    public int compare(Player p1, Player p2) {
	if(p1.figuresInHouse > p2.figuresInHouse) return 1;
	if(p1.figuresInHouse < p2.figuresInHouse) return -1;
	if(p1.lastMoveCountFigureMovedIntoHouse < p2.lastMoveCountFigureMovedIntoHouse) return 1;
	if(p1.lastMoveCountFigureMovedIntoHouse > p2.lastMoveCountFigureMovedIntoHouse) return -1;
	//0 figures moved in
	return 0;
    }

    public ArrayList<Player> getOrder(ArrayList<Player> playerWinOrder) {
	//iterate through players in random order to make ties random order
	ArrayList<Player> randomOrderPlayers = new ArrayList<>(this.players);
	Collections.shuffle(randomOrderPlayers);
	for (int i = 0; i < randomOrderPlayers.size(); i++) {
	    //find first 1, insert before
	    boolean foundInx = false;
	    for (int j = 0; j < playerWinOrder.size(); j++) {
		if(compare(playerWinOrder.get(j), randomOrderPlayers.get(i)) == 1) {
		    playerWinOrder.add(j, randomOrderPlayers.get(i));
		    foundInx = true;
		    break;
		}
	    }
	    //if no 1 then append
	    if(!foundInx) {
		playerWinOrder.add(randomOrderPlayers.get(i));
	    }
	}
	return playerWinOrder;	
    }

    public ArrayList<Player> getWinners() {
	ArrayList<Player> playerWinOrder = new ArrayList<>();
	boolean gameOver = false;
	
	for (int i = 0; i < this.players.size(); i++) {
	    if(this.players.get(i).figuresInHouse == this.figureCount) {
		gameOver = true;
		break;
	    }
	}

	if (this.movesMade >= this.maximumTotalMoves) gameOver = true;
	if (!this.nextPlayer()) gameOver = true;

	if(gameOver) {
	    getOrder(playerWinOrder);
	    return playerWinOrder;
	}
	playerWinOrder.add(null);
	return playerWinOrder;
    }

    public void incrementRound() {
	this.round++;
    }

    public Card getLastCard() {
	return pile.get(pile.size() - 1);
    }

    public boolean roundOver() {
	return this.playersRemaining == 0;
    }

    public Field getField(int inx) {
	return this.board[inx];
    }

    //TODO CardType is Card in interface doc
    
    //if move not legal do nothing and execute handle illegal move
    public Move getMove(boolean skip, CardType card, int selectedValue,
						int pieceId, boolean isStarter, Integer opponentPieceId) {
	Player player = this.getCurrentPlayer();
	if(card == null || skip) return null;
	//check if cardtype in cards
	Card foundCard = null;
	for (int i = 0; i < player.cards.size(); i++) {
	    if(player.cards.get(i).type == card) {
		foundCard = player.cards.get(i);
		break;
	    }
	}
	if(foundCard == null) return null;
	Move move = foundCard.getMove(this, selectedValue, pieceId, isStarter, opponentPieceId, player);
	//for m in legalmoves see if functionally the same
	ArrayList<Move> moves = new ArrayList<>();
	player.generateMoves(this, moves); //TODO only need to gen moves for a particular card type
	for (int i = 0; i < moves.size(); i++) {
	    if(moves.get(i).equal(move)) {
		return move;
	    }
	}
	return null;
    }

    public boolean tryMove(boolean skip, int cardOrdinal, int selectedValue,
				 int pieceId, boolean isStarter, Integer opponentPieceId) {
	CardType card = CardType.values()[cardOrdinal];
	Move move = getMove(skip, card, selectedValue, pieceId, isStarter, opponentPieceId);
	if(move==null) {
	    handleIllegalMove();
	    return false;
	}
	else {
	    move.execute(this);
	    return true;
	}
    }

    //exclude from round
    public void excludeFromRound(Player player) {
	this.discardHandCards(); //of current player
	this.playersRemaining--;
	this.nextPlayer();
    }

    //kick player
    public void excludeFromGame(Player player) {
	player.setExclude();
	this.playersRemaining--;
	this.nextPlayer();
    }

    //handle invalid move
    public void handleIllegalMove() {
	//TODO if consequences for illegal move
	if(consequences == Penalty.kickFromGame) {
	    excludeFromGame(this.getCurrentPlayer());
	}
	else if(consequences == Penalty.excludeFromRound) {
	    excludeFromRound(this.getCurrentPlayer());
	}
	else {
	}
    }

    public Integer getHousePosition(int playerId, int pieceId) {
	Figure f = this.players.get(playerId).figures.get(pieceId);
	if(!f.isInHouse) return null;
	return this.players.size() - (f.field.val - this.players.get(playerId).houseFirstIndex) + 1;
    }
}

