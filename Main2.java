import java.io.*;

public class Main2 {
    public static void main(String[] args) {
	try {
	    System.out.println("before client start");
	    Client2 client = new Client2("localhost", 4222);
	    System.out.println("after client start");
	    client.send("yo"); 
	}
	catch (IOException e) {};
    }
}
