import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.io.*;
import java.util.HashMap;
import java.util.ArrayList;

public class MiniServer extends Thread{

    private Socket socket = null;
    private SocketAddress ip = null;
    private String ipString = null;
    private HashMap<String, ArrayList<String>> messageLog = null;

    public MiniServer(Socket socket,  HashMap<String, ArrayList<String>> messageLog) {

        super("MiniServer");
        this.socket = socket;
        this.ip = socket.getRemoteSocketAddress();
	this.ipString = this.ip.toString();
	this.messageLog = messageLog;
    }

    public void run(){
	while(true) {
	    try {
		BufferedReader in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
		String str = in.readLine();
		if (str == null) break; //???
		//if not in keys
		if(messageLog.get(this.ipString) == null) {
		    this.messageLog.put(this.ipString, new ArrayList<String>());
		}
		
		this.messageLog.get(this.ipString).add(str);

		if(str.equals("log")) {
		    for (String s : this.messageLog.keySet()) {
			System.out.println("key: " + s);
			for (int i = 0; i < this.messageLog.get(s).size(); i++) {
			    System.out.println(" value" + i + ": " + this.messageLog.get(s).get(i));
			}
		    }

		}

	    }
	    catch(Exception e) {
	    }
	}
    }

}
