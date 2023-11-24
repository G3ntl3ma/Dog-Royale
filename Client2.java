import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;
    
public class Client2 {
    private ConnectionToServer server;
    private LinkedBlockingQueue<Object> messages;
    private Socket socket;

    public Client2(String IPAddress, int port) throws IOException{
        socket = new Socket(IPAddress, port);
        messages = new LinkedBlockingQueue<Object>();
	System.out.println("before new connection to server");
        server = new ConnectionToServer(socket);
	System.out.println("after new connection to server");

        Thread messageHandling = new Thread() {
            public void run(){
                while(true){
                    try{
                        Object message = messages.take();
                        // Do some handling here...
                        System.out.println("Message Received: " + message);
                    }
                    catch(InterruptedException e){ }
                }
            }
        };

        messageHandling.setDaemon(true);
        messageHandling.start();
    }

    private class ConnectionToServer {
        ObjectInputStream in;
        ObjectOutputStream out;
        Socket socket;

        ConnectionToServer(Socket socket) throws IOException {
	    System.out.println("instantiate connection to server");
            this.socket = socket;
	    //output before input otherwise it wont work 
            out = new ObjectOutputStream(socket.getOutputStream());
	    in = new ObjectInputStream(socket.getInputStream());

            Thread read = new Thread(){
                public void run(){
                    while(true){
                        try{
                            Object obj = in.readObject();
                            messages.put(obj);
                        }
                        catch(IOException e){ e.printStackTrace(); }
			catch(ClassNotFoundException e){ e.printStackTrace(); }
			catch(InterruptedException e){ e.printStackTrace(); }
                    }
                }
            };

            read.setDaemon(true);
            read.start();
        }

        private void write(Object obj) {
            try{
                out.writeObject(obj);
            }
            catch(IOException e){ e.printStackTrace(); }
        }


    }

    public void send(Object obj) {
        server.write(obj);
    }
}
