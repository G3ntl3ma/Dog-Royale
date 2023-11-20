import java.net.Socket;
import java.io.*;

public class EchoClient {

    public static void main(String[] args) {
 
	try {
	    System.out.println("Client started");
	    Socket soc = new Socket("localhost", 4323);
	    BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
	    PrintWriter out = new PrintWriter(soc.getOutputStream(), true /*autoflus*/);
	    BufferedReader in = new BufferedReader(new InputStreamReader(soc.getInputStream()));
	    while (true) {
		System.out.println("Enter string");
		String str = userInput.readLine();
		System.out.println("entered " + str);
		out.println(str);
	    }
	}
	catch(Exception e) {
	    e.printStackTrace();
	}
    }
}
