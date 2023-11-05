import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Game {

    ArrayList<Player> players;
    ArrayList<Field> board;
    ArrayList<Card> deck;
    ArrayList<Card> pile;
    int figurecount;
    int mainfields; //number of fields that arent bank or house
    int playertomove;
    int playersremaining;
    
    public Game() {
	this.players = new ArrayList<>();
	this.board = new ArrayList<>();
	this.deck = new ArrayList<>();
	this.pile = new ArrayList<>();
	this.playertomove = 0;
    }

    public void distributecards() {
	for (int i = 0; i < 5; i++) {
	    for (int j = 0; j < this.players.size(); j++) {
		this.players.get(j).draw(this.deck);
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
	    Field f = this.board.get(i);
	    System.out.print(f.typ+"-");
	}
	System.out.println("");
	for (int i = 0; i < this.mainfields; i++) {
	    Field f = this.board.get(i);
	    if(!f.empty) System.out.print(f.figure.col+"-");
	    else         System.out.print("_"+"-");
	}
	System.out.println("");
	System.out.println("===================");
    }

    public void initdeck() {
	for (int i = 0; i < 7; i++) {
	    for (int j = 2; j <= 12; j++) {
		if(j == 4 || j == 7 || j == 11 ) continue;
		this.deck.add(new Card('n', j));
	    }
	    this.deck.add(new Card('4'));
	    this.deck.add(new Card('7'));
	    this.deck.add(new Card('s'));
	    this.deck.add(new Card('c'));
	}

	for (int i = 0; i < 10; i++) {
	    this.deck.add(new Card('t'));
	    this.deck.add(new Card('e'));
	}
	for (int i = 0; i < 6; i++) {
	    this.deck.add(new Card('m'));
	}
	
	Collections.shuffle(this.deck, new Random(666)); //deterministic seed
	// Collections.shuffle(this.deck); //undeterministic
    }

    public void init(String conf, int figurecount) { //set board and players
	this.figurecount = figurecount;
	
	int players = 0;
	for (int i = 0; i < conf.length(); i++) {
	    if (conf.charAt(i) == 's') players++;
	}
	
	int max = conf.length(); //TODO ??
	int fieldcount = conf.length();
	this.mainfields = max;

	System.out.println("conf string length " + max);
	
	for (int i = 0; i < max; i++) {
	    this.board.add(new Field(i));
	}

	//add players Player
	for (int plyrcol = 0; plyrcol < players; plyrcol++) { 
	    this.players.add(new Player(plyrcol, figurecount));
	}
	int seenstarts = 0;
	for (int i = 0; i < max; i++) {
	    int prev = ((i - 1) + max) % max;
	    int next = (i + 1) % max;
	    this.board.get(i).addnext(this.board.get(next));
	    this.board.get(i).addprev(this.board.get(prev));
	    this.board.get(i).settyp(conf.charAt(i));
	    //TODO add house fields
	    if (conf.charAt(i) == 's') {
		this.players.get(seenstarts++).startfield = this.board.get(i); //init starts
		int off = fieldcount;
		for (int j = off; j < figurecount + off; j++) {
		    this.board.add(new Field(fieldcount++, 'h'));
		}
		for (int j = off; j < figurecount-1 + off; j++) {
		    this.board.get(j).addnext(this.board.get(j+1));
		    this.board.get(j+1).addprev(this.board.get(j));
		}
		
		this.board.get(i).addhouse(this.board.get(off));
		this.board.get(off).addprev(board.get(i)); //can move out of house??
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

}

