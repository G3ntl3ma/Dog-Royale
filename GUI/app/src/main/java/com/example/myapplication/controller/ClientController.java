<<<<<<< Updated upstream
package com.example.myapplication.controller;

import static com.example.myapplication.handler.messageHandler.menu.MenuMessageHandler.gson;

import com.example.myapplication.Game_board;
import com.example.myapplication.StartScreen;
import com.example.myapplication.StartingGames;
import com.example.myapplication.handler.HandlingException;
import com.example.myapplication.handler.ServerHandler;
import com.example.myapplication.messages.menu.AbstractMenuMessage;
import com.example.myapplication.messages.menu.ConnectToServer;
import com.example.myapplication.messages.menu.ConnectToGame;
import com.example.myapplication.messages.menu.ConnectedToServer;
import com.example.myapplication.messages.menu.JoinGameAsObserver;
import com.example.myapplication.messages.menu.JoinGameAsParticipant;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lombok.Getter;

/**
 * Client controller that ?creates a socket and connects to server?
 */

public class ClientController {
    private static final String SERVER_ADDRESS = "127.0.0.1"; // TODO change with real ip
    private static final int SERVER_PORT = 8080; // TODO TODO change with real Port
    @Getter
    private static final ClientController instance = new ClientController();
    //private  static final Logger logger = LogManager.getLogger(ClientController.class);
    @Getter
    private  ServerHandler serverHandler ;
    private StartScreen startScreen;
    private final ExecutorService executorService = Executors.newFixedThreadPool(100);

    private ClientController() {
        try {
            // Establish a connection to the server when the ClientController is initiated.
            Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
//            logger.info("Socket started successfully");
            ServerHandler  serverHandler = new ServerHandler(socket);
            this.serverHandler = serverHandler;
            executorService.submit(serverHandler);
        } catch (IOException e) {
//           logger.error(e.getStackTrace());
        }
    }

    public void sendConnectToServerRequest(ConnectToServer message, StartScreen startScreen) throws HandlingException{
        this.startScreen = startScreen;
        try{
            serverHandler.broadcast(gson.toJson(message));
        }catch (Exception e){

            throw new HandlingException("Exception while handling BoardState",
                    e, message.getType());
        }



    }
    public void navigateToFirstFragment(){
        startScreen.navigateToFirstFragment();
    }
    public void sendConnectToGame(JoinGameAsObserver message, StartingGames startingGames){

        
    }
    public void navigateToLobby(){

    }


}
=======
//package com.example.myapplication.controller;
//
//import static com.example.myapplication.handler.messageHandler.menu.MenuMessageHandler.gson;
//
//import com.example.myapplication.handler.HandlingException;
////import com.example.myapplication.handler.ServerHandler;
//import com.example.myapplication.messages.game.TypeGame;
//import com.example.myapplication.messages.menu.AbstractMenuMessage;
//import com.example.myapplication.messages.menu.ConnectToServer;
//import com.example.myapplication.messages.menu.TypeMenu;
//
////import org.apache.logging.log4j.LogManager;
////import org.apache.logging.log4j.Logger;
//
//import java.io.IOException;
//import java.net.Socket;
//import java.net.UnknownHostException;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//import lombok.Data;
//import lombok.Getter;
//
///**
// * Client controller that ?creates a socket and connects to server?
// */
//
//public class ClientController {
//    private static final String SERVER_ADDRESS = "127.0.0.1"; // TODO change with real ip
//    private static final int SERVER_PORT = 8080; // TODO TODO change with real Port
//    @Getter
//    private static final ClientController instance = new ClientController();
//    //private  static final Logger logger = LogManager.getLogger(ClientController.class);
//    @Getter
//    private  ServerHandler serverHandler ;
//    private final ExecutorService executorService = Executors.newFixedThreadPool(100);
//
//    private ClientController() {
//        try {
//            // Establish a connection to the server when the ClientController is initiated.
//            Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
////            logger.info("Socket started successfully");
//            ServerHandler  serverHandler = new ServerHandler(socket);
//            this.serverHandler = serverHandler;
//            executorService.submit(serverHandler);
//        } catch (IOException e) {
////           logger.error(e.getStackTrace());
//        }
//    }
//
//    public void sendMenuRequest(AbstractMenuMessage message) throws HandlingException{
//        try{
//            serverHandler.broadcast(gson.toJson(message));
//        }catch (Exception e){
//            throw new HandlingException("Exception while handling BoardState",
//                    e, message.getType());
//        }
//    }
//
//
//}
>>>>>>> Stashed changes
