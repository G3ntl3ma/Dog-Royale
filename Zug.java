public class Zug  {

    Field from;
    Field to;
    boolean swapfigs;
    boolean start; //ignore from field just put a fig on start
    Player player;
    
    public Zug(Player player, Field from, Field to, boolean start,boolean swapfigs) {
	this.player = player;
	this.from = from;
	this.to = to;
	this.swapfigs = swapfigs;
	this.start = start;
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
		System.out.println("player " + player.col + " karte ziehen!");
	    }
	    if (from.typ == 'k') {
		System.out.println("player " + opponent.col + " karte ziehen!");
	    }
	}
	else if(start) {
	    //TODO assert figs in bank > 0
	    System.out.println("start move");
	    System.out.println("figs in bank before " + this.player.figsinbank);
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
	    this.player.figsinbank -= 1;

	    //TODO assert figcol == playercol
	    System.out.println("figs in bank " + this.player.figsinbank);
	    System.out.println("fig col " + figure.col);
	    System.out.println("plyer col " + this.player.col);
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
		System.out.println("player " + player.col + " karte ziehen!");
	    }

	}
    }
}
