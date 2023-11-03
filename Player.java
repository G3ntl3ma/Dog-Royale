import java.util.ArrayList;

public class Player  {

    ArrayList<Figure> figures = new ArrayList<>();
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
	return this.figures.get(0);
    }

    public void printinfo() {
	System.out.println("player " + this.col + " figbank " + this.figsinbank + " figs in house " + this.figsinhouse);
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
}
