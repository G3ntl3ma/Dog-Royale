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

    public void execute() { //TODO game arg??
	if(swapfigs) {
	    System.out.println("TODO: implement swapfigs");
	}
	else if(start) {
	    //TODO assert figs in bank > 0
	    System.out.println("start move");
	    System.out.println("figs in bank before " + this.player.figsinbank);
	    Figure figure = this.player.getfirstinbank();
	    figure.inbank = false;
	    figure.inhouse = false;
	    player.startfield.setfigure(figure); //get first figure not on field from player
	    this.player.figsinbank -= 1;

	    //TODO assert figcol == playercol
	    System.out.println("figs in bank " + this.player.figsinbank);
	    System.out.println("fig col " + figure.col);
	    System.out.println("plyer col " + this.player.col);
	}
	else {
	    //can increase decrease figsinhouse
	    //if figsinhouse==figcount won
	    System.out.println("TODO: implement normal move");
	}
    }
}
