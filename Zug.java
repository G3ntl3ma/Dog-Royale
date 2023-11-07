public class Zug  {

    Field from;
    Field to;
    boolean swapfigs;
    boolean start; //ignore from field just put a fig on start
    Player player;
    Card cardused;
    
    public Zug(Player player, Field from, Field to, boolean swapfigs, Card cardused) {
	this.player = player;
	this.from = from;
	this.to = to;
	this.swapfigs = swapfigs;
	this.start = false;
	this.cardused = cardused;
    }

    public Zug(Player player, Card cardused) { //start move
	this.player = player;
	this.start = true;
	this.cardused = cardused;
    }

    public void printz(){
	System.out.println(this.from.val + "-" + this.to.val);
    }

    public void execute(Game game) {
	// System.out.println("cards num before " + this.player.cards.size());
	player.cards.remove(this.cardused);
	game.pile.add(this.cardused);
	// System.out.println("cards num after 1 " + this.player.cards.size());
	if(swapfigs) {
	    Figure temp = to.figure;
	    Player opponent = game.players.get(to.figure.col);
	    //set figure of field
	    to.figure = from.figure;
	    from.figure = temp;

	    //set field of figures
	    to.figure.field = to;
	    from.figure.field = from;

	    if (to.typ == 'k') {
		System.out.println("player " + player.col + " draw card!");
		player.draw(game);
	    }
	    if (from.typ == 'k') {
		System.out.println("player " + opponent.col + " draw card!");
		player.draw(game);
	    }
	}
	else if(start) {
	    //TODO assert figs in bank > 0
	    // System.out.println("start move");
	    // System.out.println("figs in bank before " + this.player.figsinbank);
	    Figure figure = this.player.getfirstinbank();
	    Field to = player.startfield;
	    figure.inbank = false;
	    figure.inhouse = false;
	    
	    if (!to.empty) {
		Player opponent = game.players.get(to.figure.col);
		opponent.figsinbank++;
		//set field of figure
		to.figure.inbank = true;
	    }

	    //set figure of field
	    player.startfield.setfigure(figure); //get first figure not on field from player
	    this.player.figsinbank--;

	    //TODO assert figcol == playercol
	    // System.out.println("figs in bank " + this.player.figsinbank);
	    // System.out.println("fig col " + figure.col);
	    // System.out.println("plyer col " + this.player.col);
	}
	else { //normal move
	    if (!to.empty) {
		Player opponent = game.players.get(to.figure.col);
		opponent.figsinbank++;
		to.figure.inbank = true;
	    }

	    if (to.typ == 'h' && from.typ != 'h') {
		player.figsinhouse++;
		from.figure.inhouse = true;
	    }

	    //possible??
	    if (to.typ != 'h' && from.typ == 'h') {
		player.figsinhouse--;
		from.figure.inhouse = false;
	    }
	    
	    from.empty = true;
	    to.setfigure(from.figure);

	    if (to.typ == 'k') {
		System.out.println("player " + player.col + " karte ziehen!");
		player.draw(game);
	    }

	}

	game.nextplayer();
	// System.out.println("cards num after 2 " + this.player.cards.size());
    }

    public void print() {
	System.out.print("card type " + this.cardused.typ + " ");
	if(this.from != null) {
	    System.out.print("from " + this.from.val + " ");
	}
	if(this.to != null) {
	    System.out.print("to " + this.to.val + " ");
	}
	System.out.println("swap figs " + this.swapfigs + " start " + this.start + " player color " + this.player.col);
    }
}
