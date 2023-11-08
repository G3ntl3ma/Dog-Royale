import java.util.ArrayList;

public class Player  {

    ArrayList<Figure> figures = new ArrayList<>();
    ArrayList<Card> cards = new ArrayList<>();
    Field startfield;
    int figsinbank;
    int figsinhouse;
    int col;
    
    public Player(int col, int figurecount) {
	this.figsinbank = figurecount;
	this.col = col;
	for (int i = 0; i < figurecount; i++) {
	    figures.add(new Figure(col));
	}
    }

    public Figure getfirstinbank() {
	for (int i = 0; i < figures.size(); i++) {
	    if (this.figures.get(i).inbank) {
		return this.figures.get(i);
	    }
	}

	System.out.println("unreachable getfirstinbank");
	System.exit(23);
	return this.figures.get(0);
    }

    public void printinfo() {
	System.out.print("player " + this.col + " figbank " + this.figsinbank + " figs in house " + this.figsinhouse);
	System.out.print(" cards ");
	this.printcards();
	System.out.println("");
    }

    public void printhouse() {
	Field house = this.startfield.house;
	System.out.print("house: ");
	while(house != null) {
	    if(!house.empty) System.out.print(house.figure.col+"-");
	    else         System.out.print("_"+"-");
	    house = house.next;
	}
	System.out.println("");
    }

    public void draw(Game game) {
	if(game.deck.size() == 0) {
	    game.reshuffle();
	}
	Card pop = game.deck.remove(game.deck.size() - 1);
	this.cards.add(pop);
    }

    public void printcards() {
	for (int i = 0; i < this.cards.size(); i++) {
	    System.out.print(this.cards.get(i).typ + " ");
	}
    }

    public void genmoves(Game game, ArrayList<Zug> moves) {
	// System.out.println("this player color " + this.col);
	for (int i = 0; i < this.cards.size(); i++) {
	    // System.out.println("card " + i + ": " + this.cards.get(i).typ);
	    for (int j = 0; j < this.figures.size(); j++) {
		// System.out.println("figure " + j);
		this.cards.get(i).getmoves(game, this.figures.get(j), moves); 
	    }
	}
    }
}
