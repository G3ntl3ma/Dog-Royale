import java.util.ArrayList;

public class Game {

    ArrayList<Player> players;
    ArrayList<Field> board;
    int figurecount;
    int mainfields;
    
    public Game() {
	this.players = new ArrayList<>();
	this.board = new ArrayList<>();
    }

    public void printboard() {
	System.out.println("BOARD=================");
	for (int i = 0; i < this.players.size(); i++) {
	    Player p = this.players.get(i);
	    p.printinfo();
	}
	for (int i = 0; i < this.mainfields; i++) {
	    Field f = this.board.get(i);
	    System.out.print(f.typ+"-");
	}
	System.out.println("");
	for (int i = 0; i < this.mainfields; i++) {
	    Field f = this.board.get(i);
	    if(!f.empty) System.out.print(f.figure.col+"-");
	    else         System.out.print(" "+"-");
	}
	System.out.println("");
	System.out.println("===================");
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
		
		this.board.get(i).addnext(this.board.get(off));
		this.board.get(off).addprev(board.get(i)); //can move out of house??
	    }
	}
	
    }


}

