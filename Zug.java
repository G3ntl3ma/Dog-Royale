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

    public void execute(Game game) { //TODO game arg??
	if(swapfigs) {
	    Figure temp = to.figure;
	    Player opponent = game.players.get(to.figure.col);
	    to.figure = from.figure;
	    from.figure = temp;

	    if (to.typ == 'k') {
		System.out.println("player " + player.col + " draw card!");
		player.draw(game.deck);
	    }
	    if (from.typ == 'k') {
		System.out.println("player " + opponent.col + " draw card!");
		player.draw(game.deck);
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
		to.figure.inbank = true;
	    }
	    
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

	    if (to.typ == 'h') {
		player.figsinhouse++;
		from.figure.inhouse = true;
	    }
	    
	    from.empty = true;
	    to.setfigure(from.figure);

	    if (to.typ == 'k') {
		System.out.println("not implemented: player " + player.col + " karte ziehen!");
	    }

	}

	game.nextplayer();
	player.cards.remove(this.cardused);
	game.pile.add(this.cardused);
    }

    public void print() {
	if(this.from != null) {
	    this.from.printval();
	}
	if(this.to != null) {
	    this.to.printval();
	}
	System.out.println("swap figs " + this.swapfigs + " start " + this.start + " player color " + this.player.col);
    }
}
