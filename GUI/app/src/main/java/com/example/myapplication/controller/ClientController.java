package com.example.myapplication.controller;

import static com.example.myapplication.handler.messageHandler.menu.MenuMessageHandler.gson;

import com.example.myapplication.Game_board;
import com.example.myapplication.SpectateGames;
import com.example.myapplication.StartScreen;
import com.example.myapplication.StartingGames;
import com.example.myapplication.handler.HandlingException;
import com.example.myapplication.handler.ServerHandler;

import com.example.myapplication.messages.menu.ConnectToServer;
import com.example.myapplication.messages.menu.JoinGameAsObserver;
import com.example.myapplication.messages.menu.TypeMenu;
import com.example.myapplication.messages.sync.LeaveObs;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lombok.Getter;
import lombok.Setter;

/**
 * Client controller that ?creates a socket and connects to server?
 */

public class ClientController {

    private static final String SERVER_ADDRESS = "127.0.0.1"; // TODO change with real ip
    private static final int SERVER_PORT = 8080; // TODO TODO change with real Port
    @Getter
    @Setter
    private static int clientID;
    @Getter
    private static final ClientController instance = new ClientController();
    private  static final Logger logger = LogManager.getLogger(ClientController.class);
    @Getter
    private  ServerHandler serverHandler ;
    private StartScreen startScreen;

    private final ExecutorService executorService = Executors.newFixedThreadPool(100);
    private StartingGames startingGames;//instance that is sent to now what window to control
    private SpectateGames spectateGames;
    private boolean startingGamesActive;//to know what was the last active window so the controller can act accordingly, like navigateToNextfragment
    private boolean spectateGamesActive;

    private ClientController() {
        try {
            // Establish a connection to the server when the ClientController is initiated.
            Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
//            logger.info("Socket started successfully");
            ServerHandler  serverHandler = new ServerHandler(socket);
            this.serverHandler = serverHandler;
            executorService.submit(serverHandler);
        } catch (IOException e) {
           logger.error(e.getStackTrace());
        }
    }

    public void sendConnectToServerRequest(String username, StartScreen startScreen) throws HandlingException{
        ConnectToServer connectToServer = new ConnectToServer();
        connectToServer.setType(TypeMenu.connectToServer.getOrdinal());
        connectToServer.setName(username);
        connectToServer.setIsObserver(true);
        this.startScreen = startScreen;
        try{
            serverHandler.broadcast(gson.toJson(connectToServer));
        }catch (Exception e){

//            throw new HandlingException("Exception while handling BoardState",
//                    e, message.getType());
        }



    }
    public void navigateToFirstFragment(){
        startScreen.navigateToFirstFragment();
    }
    public void sendJoinGameAsObserver(int gameId, Object gameFragment ){
        JoinGameAsObserver joinGameAsObserver = new JoinGameAsObserver();
        joinGameAsObserver.setClientId(ClientController.getClientID());
        joinGameAsObserver.setGameId(gameId);

        if(gameFragment instanceof StartingGames){
            this.startingGames = (StartingGames) gameFragment;
            this.startingGamesActive = true;
            this.spectateGamesActive = false;
        } else if (gameFragment instanceof SpectateGames) {
            this.spectateGames = (SpectateGames) gameFragment;
            this.startingGamesActive = false;
            this.spectateGamesActive = true;
        }

        try{
            serverHandler.broadcast(gson.toJson(joinGameAsObserver));
        }catch (Exception e){

        }
    }
    public void navigateToLobby(){
        if(this.startingGamesActive){
            startingGames.navigateToLobby();
        } else if (this.spectateGamesActive) {
            spectateGames.navigateToLobby();
        }
    }
    private void sendLeaveObs(){
        LeaveObs leaveObs = new LeaveObs();
        serverHandler.setExpectedState(ServerHandler.State.GAMES_MENU);
    }


}
