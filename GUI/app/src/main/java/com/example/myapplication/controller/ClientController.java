package com.example.myapplication.controller;

import static com.example.myapplication.handler.messageHandler.menu.MenuMessageHandler.gson;

import android.os.AsyncTask;

import com.example.myapplication.Game_board;
import com.example.myapplication.RunningGames;
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

    private final ExecutorService executorService = Executors.newFixedThreadPool(100);
    private StartingGames startingGames;//instance that is sent to now what window to control
    private RunningGames spectateGames;
    private boolean startingGamesActive;//to know what was the last active window so the controller can act accordingly, like navigateToNextfragment
    private boolean spectateGamesActive;
    @Getter@Setter
    private WaitingScreen waitingScreen;
    private ServerHandler serverHandler;

    private ClientController() {
        new SocketInitializationTask().execute();

    }

    private void initializeServerHandler(Socket socket) {
        this.serverHandler = ServerHandler.getInstance(socket);



    }

    private static class SocketInitializationTask extends AsyncTask<Void, Void, Socket> {
        @Override
        protected Socket doInBackground(Void... params) {
            try {
                String serverAddress = "192.168.0.208";
                int serverPort = 8082;
                return new Socket(serverAddress, serverPort);
            } catch (IOException e) {
                logger.error("Failed connection");
                // Handle exception
                return null;
            }
        }

        @Override
        protected void onPostExecute(Socket socket) {
            if (socket != null) {
                instance.initializeServerHandler(socket);
                ServerHandler serverHandler = ServerHandler.getInstance(socket);
                executor.execute(serverHandler);

            }
        }
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
        try{
            serverHandler.broadcast(gson.toJson(connectToServer));
        }catch (Exception e){

            throw new HandlingException("Exception while handling ",
                    e, connectToServer.getType());
        }



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
        } else if (gameFragment instanceof RunningGames) {
            this.spectateGames = (RunningGames) gameFragment;
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
