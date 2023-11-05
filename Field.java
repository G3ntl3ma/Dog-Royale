import java.util.ArrayList;
import java.util.Arrays;

public class Field  {

    int val; //id
    Figure figure;
    boolean empty; //ignore figure if empty
    char typ;
    // ArrayList<Field> next = new ArrayList<>();
    Field next;
    Field house;
    Field prev;

    
    public Field(int val) {
	this.val = val;
	this.empty = true; 
    }

    public Field(int val, char typ) {
	this.val = val;
	this.typ = typ;
	this.empty = true; 
    }
    
    
    public void printval() {
	System.out.println(this.val);
    }

    public void printvals() {
	System.out.println("this val");
	System.out.println(this.val);
	System.out.println("next val");
	this.next.printval();
	System.out.println("prev val");
	this.prev.printval();

    }
    public void printtyp() {

	switch (typ) {
	case 'n':
	    System.out.println("normal");
	    break;
	    
	case 'k':
	    System.out.println("draw card");
	    break;

	case 's':
	    System.out.println("start");
	    break;

	case 'h':
	    System.out.println("house");
	    break;
	}	

    }
    
    public void printtyps() {
	System.out.println("this typ");
	System.out.println(this.typ);
	System.out.println("next typ");
	this.next.printtyp();
	System.out.println("prev typ");
	this.prev.printtyp();
    }

    public void addnext(Field field) {
	this.next = field;
    }

    public void addhouse(Field field) {
	this.house = field;
    }

    public void addprev(Field field) { //TODO rename to set
	this.prev = field;
    }

    public void setfigure(Figure figure) {
	this.figure = figure;
	figure.field = this;
	this.empty = false;
    }

    public void setempty() {
	this.empty = true;
    }

    public void settyp(char typ) {
	this.typ = typ;
    }
}
