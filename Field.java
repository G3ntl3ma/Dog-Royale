import java.util.ArrayList;
import java.util.Arrays;

public class Field  {

    int val; //TODO better name 
    Figure figure;
    // boolean empty; //ignore figure if empty
    char type;
    Field next;
    Field house;
    Field prev;

    
    public Field(int val) {
	this.val = val;
	this.figure = null;
	// this.empty = true; 
    }

    public Field(int val, char type) {
	this.val = val;
	this.type = type;
	this.figure = null;
	// this.empty = true; 
    }

    public boolean isEmpty() {
	return this.figure == null;
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
    public void printtype() {

	switch (type) {
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
    
    public void printtypes() {
	System.out.println("this type");
	System.out.println(this.type);
	System.out.println("next type");
	this.next.printtype();
	System.out.println("prev type");
	this.prev.printtype();
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
	// this.empty = false;
    }

    public void setEmpty() {
	this.figure = null;
    }

    public void settype(char type) {
	this.type = type;
    }
}
