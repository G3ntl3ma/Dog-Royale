public class Figure  {

    Field field;
    boolean inbank; //ignore field if inbank
    boolean inhouse; //cant be swapped for example
    int col;
    
    public Figure(int col) {
	this.inbank = true;
	this.inhouse = false;
	this.col = col;
    }

}
