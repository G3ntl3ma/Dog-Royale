import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public final class Game {

    ArrayList<Player> players;
    private Field[] board;
    ArrayList<Card> deck;
    ArrayList<Card> pile;
    private final int figureCount;
    private final int initialHandCardCount;
    private int mainFieldCount;
    private int playerToStartColor;
    private int playerToMoveColor;
    int playersRemaining;

    private int[] startIndexs; //indeces of startFields, unused
    boolean[] occupied; //unused
    
    public Game(String conf, int figureCount, int initialHandCardCount) {
	this.players = new ArrayList<>();
	this.deck = new ArrayList<>();
	this.pile = new ArrayList<>();
	this.playerToMoveColor = 0;
	this.playerToStartColor = 0;
	this.figureCount = figureCount;
	this.initialHandCardCount = initialHandCardCount;
	init(conf);
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

    public void reinit() {
	this.playerToStartColor = (this.playerToStartColor + 1) % this.players.size();
	this.playerToMoveColor = this.playerToStartColor;
	this.playersRemaining = this.players.size();
    }

    public void discardHandCards() {
	Player player = this.getCurrentPlayer();
	if (player.cards.size() > 0) {
	    if (this.pile.size() > 0) {
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
	    this.deck.add(new Card(CardType.NORMAL_2));
	    this.deck.add(new Card(CardType.NORMAL_3));
	    this.deck.add(new Card(CardType.PLUS_MINUS_4));
	    this.deck.add(new Card(CardType.NORMAL_5));
	    this.deck.add(new Card(CardType.NORMAL_6));
	    this.deck.add(new Card(CardType.RANGE_7));
	    this.deck.add(new Card(CardType.NORMAL_8));
	    this.deck.add(new Card(CardType.NORMAL_9));
	    this.deck.add(new Card(CardType.NORMAL_10));
	    this.deck.add(new Card(CardType.NORMAL_12));
	    this.deck.add(new Card(CardType.SWAP));
	    this.deck.add(new Card(CardType.COPY));
	}

	for (int i = 0; i < 10; i++) {
	    this.deck.add(new Card(CardType.START_13));
	    this.deck.add(new Card(CardType.START_1_11));
	}
	for (int i = 0; i < 6; i++) {
	    this.deck.add(new Card(CardType.MAGNET));
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
		    this.board[j+1].setPrev(this.board[j]);
		}
		seenstarts++;
		this.board[i].setHouse(this.board[off]);
		this.board[off].setPrev(this.board[i]);
	    }
	}
	this.playersRemaining = this.players.size();
	
    }

    public Player getCurrentPlayer() {
	return this.players.get(this.playerToMoveColor);
    }

    public void nextPlayer() {
	this.playerToMoveColor = (this.playerToMoveColor + 1) % this.players.size();
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

    public Player getWinner() {
	for (int i = 0; i < this.players.size(); i++) {
	    if(this.players.get(i).figuresInHouse == this.figureCount) {
		return this.players.get(i);
	    }
	}
	return null;
    }
}

