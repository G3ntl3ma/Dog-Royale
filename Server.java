import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import java.util.HashMap;
import java.util.ArrayList;

public class Server {

    
    public static void main(String[] args) throws IOException {
	HashMap<String, ArrayList<String>> messageLog = new HashMap<String, ArrayList<String>>();

        ServerSocket serverSocket = null;

        boolean listeningSocket = true;
        try {
            serverSocket = new ServerSocket(4323);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 4323");
        }

        while(listeningSocket){
            Socket clientSocket = serverSocket.accept();
            MiniServer mini = new MiniServer(clientSocket, messageLog);
            mini.start(); //startttt
        }
        serverSocket.close();       
    }

}
