import java.util.ArrayList;
import java.util.Arrays;

public class Field  {

    int val; //id
    Figure figure;
    boolean empty; //ignore figure if empty
    char typ;
    ArrayList<Field> next = new ArrayList<>();
    ArrayList<Field> prev = new ArrayList<>();
    
    public Field(int val) {
	this.val = val;
	this.empty = true; 
    }

    public void printval() {
	System.out.println(this.val);
    }

    public void printvals() {
	System.out.println("this val");
	System.out.println(this.val);
	System.out.println("next val");
	for (int i = 0; i < this.next.size(); i++) {
	    this.next.get(i).printval();
	}
	System.out.println("prev val");
	for (int i = 0; i < this.prev.size(); i++) {
	    this.prev.get(i).printval();
	}

    }
    public void printtyp() {

	switch (typ) {
	case 'n':
	    System.out.println("normal");
	    break;
	    
	case 'k':
	    System.out.println("karteziehen");
	    break;

	case 's':
	    System.out.println("start");
	    break;

	case 'h':
	    System.out.println("haus");
	    break;
	}	

    }
    
    public void printtyps() {
	System.out.println("this typ");
	System.out.println(this.typ);
	System.out.println("next typ");
	for (int i = 0; i < this.next.size(); i++) {
	    this.next.get(i).printtyp();
	}
	System.out.println("prev typ");
	for (int i = 0; i < this.prev.size(); i++) {
	    this.prev.get(i).printtyp();
	}
    }

    public void addnext(Field field) {
	this.next.add(field);
    }

    public void addprev(Field field) {
	this.prev.add(field);
    }

    public void setfigure(Figure figure) {
	this.figure = figure;
	this.empty = false;
    }

    public void setempty() {
	this.empty = true;
    }

    public void settyp(char typ) {
	this.typ = typ;
    }
}
