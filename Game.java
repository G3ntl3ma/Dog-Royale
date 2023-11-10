import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Game {

    ArrayList<Player> players;
    // ArrayList<Field> board;
    Field[] board;
    int[] startinxs; //indeces of startfields
    boolean[] occupied;
    ArrayList<Card> deck;
    ArrayList<Card> pile;
    int playertostart;
    int figurecount;
    int handcardcount;
    int mainfields; //number of fields that arent bank or house
    int playertomove;
    int playersremaining;
    
    public Game() {
	this.players = new ArrayList<>();
	// this.board = new ArrayList<>();
	this.deck = new ArrayList<>();
	this.pile = new ArrayList<>();
	this.playertomove = 0;
	this.playertostart = 0;
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
	this.playertostart = (this.playertostart + 1) % this.players.size();
	this.playertomove = this.playertostart;
	this.playersremaining = this.players.size();
    }

    public void discardhandcards() {
	Player player = this.getcurplayer();
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

    public void distributecards() {
	for (int i = 0; i < this.handcardcount; i++) {
	    for (int j = 0; j < this.players.size(); j++) {
		this.players.get(j).draw(this);
	    }
	}
    }

    public void printboard() {
	System.out.println("BOARD=================");
	System.out.println("player to move " + this.playertomove);
	for (int i = 0; i < this.players.size(); i++) {
	    Player p = this.players.get(i);
	    p.printinfo();
	    p.printhouse();
	}
	for (int i = 0; i < this.mainfields; i++) {
	    System.out.print(i+"-");
	}
	System.out.println("");
	for (int i = 0; i < this.mainfields; i++) {
	    // Field f = this.board.get(i);
	    Field f = this.board[i];
	    System.out.print(f.typ+"-");
	}
	System.out.println("");
	for (int i = 0; i < this.mainfields; i++) {
	    Field f = this.board[i];
	    if(!f.empty) System.out.print(f.figure.col+"-");
	    else         System.out.print("_"+"-");
	}
	System.out.println("");
	this.printtotalcards();
	System.out.println("===================");
    }

    public void initdeck() {
	for (int i = 0; i < 7; i++) {
	    this.deck.add(new Card(Cardtype.NORMAL_2));
	    this.deck.add(new Card(Cardtype.NORMAL_3));
	    this.deck.add(new Card(Cardtype.PLUS_MINUS_4));
	    this.deck.add(new Card(Cardtype.NORMAL_5));
	    this.deck.add(new Card(Cardtype.NORMAL_6));
	    this.deck.add(new Card(Cardtype.RANGE_7));
	    this.deck.add(new Card(Cardtype.NORMAL_8));
	    this.deck.add(new Card(Cardtype.NORMAL_9));
	    this.deck.add(new Card(Cardtype.NORMAL_10));
	    this.deck.add(new Card(Cardtype.NORMAL_12));
	    this.deck.add(new Card(Cardtype.SWAP));
	    this.deck.add(new Card(Cardtype.COPY));
	}

	for (int i = 0; i < 10; i++) {
	    this.deck.add(new Card(Cardtype.START_13));
	    this.deck.add(new Card(Cardtype.START_1_11));
	}
	for (int i = 0; i < 6; i++) {
	    this.deck.add(new Card(Cardtype.MAGNET));
	}
	
	// Collections.shuffle(this.deck, new Random(666)); //deterministic seed
	Collections.shuffle(this.deck); //undeterministic
    }

    public void init(String conf, int figurecount,int handcardcount) { //set board and players
	this.figurecount = figurecount;
	this.handcardcount = handcardcount;
	int players = 0;
	for (int i = 0; i < conf.length(); i++) {
	    if (conf.charAt(i) == 's') players++;
	}
	
	int max = conf.length(); //TODO ??
	int fieldcount = conf.length();
	this.mainfields = max;


	int totalfieldcount = fieldcount + figurecount * players; //playercount
	this.board = new Field[totalfieldcount];

	this.startinxs = new int[players];
	this.occupied = new boolean[players];

	// System.out.println("conf string length " + max);
	
	for (int i = 0; i < max; i++) {
	    // this.board.add(new Field(i));
	    this.board[i] = new Field(i);
	}

	//add players Player
	for (int plyrcol = 0; plyrcol < players; plyrcol++) { 
	    this.players.add(new Player(plyrcol, figurecount));
	}
	int seenstarts = 0;
	for (int i = 0; i < max; i++) {
	    int prev = ((i - 1) + max) % max;
	    int next = (i + 1) % max;
	    
	    // this.board.get(i).addnext(this.board.get(next));
	    // this.board.get(i).addprev(this.board.get(prev));
	    // this.board.get(i).settyp(conf.charAt(i));

	    this.board[i].addnext(this.board[next]);
	    this.board[i].addprev(this.board[prev]);
	    this.board[i].settyp(conf.charAt(i));
	    
	    //TODO add house fields
	    if (conf.charAt(i) == 's') {
		// this.players.get(seenstarts++).startfield = this.board.get(i); //init starts
		this.startinxs[seenstarts] = i;
		this.occupied[seenstarts] = false;
		this.players.get(seenstarts).startfield = this.board[i]; //init starts
		int off = fieldcount;
		this.players.get(seenstarts).houseinx = fieldcount;
		for (int j = off; j < figurecount + off; j++) {
		    // this.board.add(new Field(fieldcount++, 'h'));
		    this.board[fieldcount] = new Field(fieldcount, 'h');

		    fieldcount++;
		}
		for (int j = off; j < figurecount-1 + off; j++) {
		    // this.board.get(j).addnext(this.board.get(j+1));
		    // this.board.get(j+1).addprev(this.board.get(j));

		    this.board[j].addnext(this.board[j+1]);
		    this.board[j+1].addprev(this.board[j]);
		}
		seenstarts++;
		// this.board.get(i).addhouse(this.board.get(off));
		// this.board.get(off).addprev(board.get(i)); //can move out of house??
		this.board[i].addhouse(this.board[off]);
		this.board[off].addprev(this.board[i]);
	    }
	}
	this.playersremaining = this.players.size();
	
    }

    public Player getcurplayer() {
	return this.players.get(this.playertomove);
    }

    public void nextplayer() {
	this.playertomove = (this.playertomove + 1) % this.players.size();
    }

    public void printtotalcards() {
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

    public Player getwinner() {
	for (int i = 0; i < this.players.size(); i++) {
	    if(this.players.get(i).figsinhouse == this.figurecount) {
		return this.players.get(i);
	    }
	}
	return null;
    }
}

