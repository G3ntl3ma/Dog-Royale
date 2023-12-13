package com.example.myapplication.controller;

import static com.example.myapplication.handler.messageHandler.menu.MenuMessageHandler.gson;

import android.os.AsyncTask;

import com.example.myapplication.Game_board;
import com.example.myapplication.SpectateGames;
import com.example.myapplication.StartScreen;
import com.example.myapplication.StartingGames;
import com.example.myapplication.WaitingScreen;
import com.example.myapplication.handler.HandlingException;
import com.example.myapplication.handler.ServerHandler;

import com.example.myapplication.messages.menu.ConnectToServer;
import com.example.myapplication.messages.menu.JoinGameAsObserver;
import com.example.myapplication.messages.menu.TypeMenu;
import com.example.myapplication.messages.sync.LeaveObs;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lombok.Getter;
import lombok.Setter;

/**
 * Client controller that ?creates a socket and connects to server?
 */

public class ClientController {

    private static final String SERVER_ADDRESS = "192.168.0.208"; // TODO change with real ip
    private static final int SERVER_PORT = 8082; // TODO TODO change with real Port
    @Getter
    @Setter
    private static int clientID;
    @Getter
    private static final ClientController instance = new ClientController();
    private  static final Logger logger = LogManager.getLogger(ClientController.class);

    private StartScreen startScreen;
    private static final Executor executor = Executors.newSingleThreadExecutor();
    private static final Executor executor2 = Executors.newSingleThreadExecutor();


    private StartingGames startingGames;//instance that is sent to now what window to control
    private SpectateGames spectateGames;
    private boolean startingGamesActive;//to know what was the last active window so the controller can act accordingly, like navigateToNextfragment
    private boolean spectateGamesActive;
    @Getter@Setter
    private WaitingScreen waitingScreen;
    private ServerHandler serverHandler;

    private ClientController() {

    }
    public void startClient(){
        ServerHandler serverHandler = new ServerHandler();
        setServerHandler(serverHandler);
        executor.execute(serverHandler);
    }



    private void setServerHandler(ServerHandler serverHandler) {
        this.serverHandler = serverHandler;
    }



    public void sendConnectToServerRequest(String username, StartScreen startScreen) throws HandlingException{
        ConnectToServer connectToServer = new ConnectToServer();
        connectToServer.setType(TypeMenu.connectToServer.getOrdinal());
        connectToServer.setName(username);
        connectToServer.setObserver(true);
        this.startScreen = startScreen;
        logger.info("trying to send connectToServer message");
        if(gson.toJson(connectToServer) == null){
            logger.info("message is null");
        }
            serverHandler.broadcast(gson.toJson(connectToServer));
    }
    public void getWaitingScreen(WaitingScreen waitingScreen){
        this.waitingScreen = waitingScreen;
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
//TODO
        }
    }
    public void navigateToLobby(){
        if(this.startingGamesActive){
            startingGames.navigateToLobby();
        } else if (this.spectateGamesActive) {
            spectateGames.navigateToLobby();
        }
    }
    public void navigateToGame(){
        waitingScreen.navigateToGame();
    }
    private void sendLeaveObs(){
        LeaveObs leaveObs = new LeaveObs();
        serverHandler.setExpectedState(ServerHandler.State.GAMES_MENU);
    }


}
