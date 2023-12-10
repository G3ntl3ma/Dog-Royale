package com.example.myapplication.handler;

import com.example.myapplication.controller.ClientController;
import com.example.myapplication.handler.messageHandler.game.*;
import com.example.myapplication.handler.messageHandler.menu.*;
import com.example.myapplication.messages.game.*;
import com.example.myapplication.messages.menu.*;
import com.example.myapplication.messages.sync.*;
import com.google.gson.*;
import com.google.gson.stream.MalformedJsonException;

import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


/**
 * ServerHandler, handles the messages between client and server
 * @author tabbi
 */


public class ServerHandler extends Handler implements Runnable {
    private static  ServerHandler instance ;
    private static final Logger logger = LogManager.getLogger(ServerHandler.class);
//    private static final Object lock = new Object();  // For thread-safety
    private Socket serverSocket;
    private final PrintWriter broadcaster;
    private final ClientController clientController;


    @Getter
    public enum State {
        CONNECT_MENU,
        REQUEST_FIND_TOURNAMENT,
        REQUEST_GAME_LIST,
        MAIN_MENU,
        TOURNAMENT_MENU,//TODO change to this state when the user clicks on Tournaments button
        GAMES_MENU,//TODO change to this state when the user clicks on Games button
        LOBBY,
        GAME,
    }
    @Getter
    @Setter //TODO remove
    private State expectedState = State.CONNECT_MENU;
    Gson gson = new GsonBuilder()
            .registerTypeAdapter(Object.class, new NewLineAppendingSerializer<>())
            .create();

    public static synchronized ServerHandler getInstance(Socket socket) {
        if (instance == null) {
            instance = new ServerHandler(socket);
        }
        return instance;}
    private ServerHandler(Socket socket) {
        clientController = ClientController.getInstance();
        this.serverSocket = socket;
        PrintWriter writer;
        try {
            writer = new PrintWriter(serverSocket.getOutputStream(), false);
        } catch (Exception e) {
            writer = null;
        }
        broadcaster = writer;
}




    @Override
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(serverSocket.getInputStream()));
            PrintWriter writer = new PrintWriter(
                    serverSocket.getOutputStream(), false);

            String request, response;
            while (true) {

                if ((request = reader.readLine()) != null
                        && (response = handle(request)) != null) {
                    logger.info("this is the message from server: " + request);
                    writer.println(response);
                    writer.flush();
                }
            }
        }catch (JsonSyntaxException | MalformedJsonException e) {
            // Handle MalformedJsonException
            logger.error("The request is not in json format: " + e.getMessage());}
        catch (IOException e) {
            logger.error("Error while trying to read the server message: " + e.getMessage());
        } finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                logger.error("Error while trying to close the connection: " + e.getMessage());
            }
        }
    }
    /**
     * Writes the specified message to the broadcaster
     */
    public synchronized void broadcast(String message) {

            try (PrintWriter writer = this.broadcaster) {
                if (writer != null) {
                    writer.write(message);
                    writer.flush();
                } else {
                    logger.error("Error broadcasting message: broadcaster is null");
                }
            } catch (Exception e) {
                logger.error("Error broadcasting message", e);
            }

    }


    private String handle(String request) {
        try {
            JsonObject jsonRequest = JsonParser.parseString(request).getAsJsonObject();
            if (!isTypeInt(jsonRequest)) {
                return handleError("Received no valid type");
            }
            int type = jsonRequest.get("type").getAsInt();
            if (type == TypeMenu.connectedToServer.getOrdinal()) {
                return handleConnectedToServer(request);
            } else if (type == TypeMenu.returnFindTournament.getOrdinal()) {
                return handleReturnTournamentInfo(request);
            } else if (type == TypeMenu.returnGameList.getOrdinal()) {
                return handleReturnGameList(request);
            } else if (type == TypeMenu.connectedToGame.getOrdinal()) {
                return handleConnectedToGame(request);
            } else if (type == TypeMenu.returnLobbyConfig.getOrdinal()) {
                return handleReturnLobbyConfig(request);
            } else if (type == TypeMenu.error.getOrdinal()) {
                return handleError(request);
            } else if (type == TypeMenu.returnTechData.getOrdinal()) {
                handleReturnTechData(request);
            } else if (type == TypeGame.boardState.getOrdinal()) {
                return handleBoardState(request);
            } else if (type == TypeGame.updateDrawCards.getOrdinal()) {
                return handleUpdateDrawCards(request);
            } else if (type == TypeGame.moveValid.getOrdinal()) {
                return handleMoveValid(request);
            } else if (type == TypeGame.freeze.getOrdinal()) {
                return handleFreeze(request);
            } else if (type == TypeGame.unfreeze.getOrdinal()) {
                return handleUnfreeze(request);
            } else if (type == TypeGame.cancel.getOrdinal()) {
                return handleCancel(request);
            } else if (type == TypeGame.joinObs.getOrdinal()) {
                return handleJoinObs(request);
            } else if (type == TypeGame.liveTimer.getOrdinal()) {
                return handleLiveTimer(request);
            } else if (type == TypeGame.turnTimer.getOrdinal()) {
                return handleTurnTimer(request);
            } else {
                return handleError("The request has no valid type");
            }

        } catch (JsonSyntaxException e) {
            return handleError("The request is not in json format", e);
        } catch (HandlingException e) {
            return handleError("Failed to handle the request", e.getType(), e);
        }
        return request;
    }
    private String handleConnectedToServer(String request) throws HandlingException {
        if (expectedState != State.CONNECT_MENU) {
            return handleError("Received wrong type, didn't expect connectedToServer");
        }
        logger.info("Trying to handle ConnectedToServer");
        try {
            ConnectedToServer connectedToServer = gson.fromJson(request, ConnectedToServer.class);
            String response = new ConnectedToServerHandler().handle(connectedToServer);
            expectedState = State.REQUEST_FIND_TOURNAMENT;
            logger.info("ConnectedToServer was handled successfully ");
            return response;
        } catch (JsonSyntaxException e) {
            return handleError("Wrong message format from type connectedToServer",
                    TypeMenu.connectedToServer.getOrdinal(), e);
        }
    }
    private String handleReturnTournamentInfo(String request) throws HandlingException{
        if (expectedState !=State.REQUEST_FIND_TOURNAMENT){
            return handleError("Received wrong type, didn't expect ReturnFindTournament");
        }
        logger.info("Trying to handle ReturnFindTournament");
        try{
            ReturnFindTournament returnFindTournament = gson.fromJson(request, ReturnFindTournament.class);
            String response = new ReturnFindTournamentHandler().handle(returnFindTournament);
            expectedState = State.REQUEST_GAME_LIST;
            logger.info("ReturnTournamentInfo was handled successfully");
            return response;
        }catch (JsonSyntaxException e){
            return handleError("Wrong message format from type ReturnTournamentInfo",
                    TypeMenu.returnFindTournament.getOrdinal(), e);
        }
    }
    private String handleReturnGameList(String request) throws HandlingException{
        if (expectedState != State.REQUEST_GAME_LIST){
            return handleError("Received wrong type, didn't expect ReturnGameList");
        }
        logger.info("Trying to handle ReturnGameList");
        try{
            ReturnGameList returnGameList = gson.fromJson(request, ReturnGameList.class);
            String response = new ReturnGameListHandler().handle(returnGameList);
            expectedState = State.MAIN_MENU;
            ClientController.getInstance().navigateToFirstFragment();//after establishing connection and getting tournamentinfo and gamelist, this makes the window change to next.
            logger.info("ReturnGameList was handled successfully");
            return response;
        }catch(JsonSyntaxException e){
            return handleError("Wrong message format from type ReturnGameList",
                    TypeMenu.returnGameList.getOrdinal(), e);
        }
    }
    private String handleConnectedToGame(String request) throws HandlingException{
        if (expectedState != State.GAMES_MENU){
            return handleError("Received wrong type, didn't expect ConnectedToGame");
        }
        logger.info("Trying to handle ConnectedToGame");
        try{
            ConnectedToGame connectedToGame = gson.fromJson(request,ConnectedToGame.class);
            String response = new ConnectedToGameHandler().handle(connectedToGame);
            if (connectedToGame.isSuccess()){
                expectedState = State.LOBBY;
                ClientController.getInstance().navigateToLobby();

            }
            logger.info("ConnectedToGame was handled successfully");
            return response;
        }catch(JsonSyntaxException e){
            return handleError("Wrong message format from type ConnectedToGame",
                    TypeMenu.connectedToGame.getOrdinal(), e);
    }}
    private String handleReturnLobbyConfig(String request) throws HandlingException{
        if (expectedState != State.LOBBY){
            return handleError("Received wrong type, didn't expect ReturnLobbyConfig");
        }
        logger.info("Trying to handle ReturnLobbyConfig");
        try{
            ReturnLobbyConfig returnLobbyConfig = gson.fromJson(request, ReturnLobbyConfig.class);
            String response = new ReturnLobbyConfigHandler().handle(returnLobbyConfig);
            logger.info("ReturnGameList was handled successfully");
            return response;
        }catch (JsonSyntaxException e){
            return handleError("Wrong message format from type ReturnLobbyConfig",
                    TypeMenu.returnLobbyConfig.getOrdinal(), e);
        }
    }
    private String handleBoardState(String request) throws HandlingException{
        switch (expectedState){
            case LOBBY:
                try{
                    BoardState boardState = gson.fromJson(request, BoardState.class);
                    String response = new FirstBoardStateHandler().handle(boardState);
                    expectedState = State.GAME;
                    ClientController.getInstance().navigateToGame();
                    logger.info("FirstBoardState was handled successfully");
                    return response;//update message
                }catch (JsonSyntaxException e){
                    return handleError("Wrong message format from type BoardState",
                            TypeGame.boardState.getOrdinal(), e);
                }
            case GAME:
                try{
                    BoardState boardState = gson.fromJson(request, BoardState.class);
                    String response = new BoardStateHandler().handle(boardState);//if gameOver it shows the end screen with winners and takes you back to connect menu
                    if(boardState.getGameOver()){expectedState = State.CONNECT_MENU;}
                    logger.info("BoardState was handled successfully");
                    return response;//update message
                }catch (JsonSyntaxException e){
                    return handleError("Wrong message format from type BoardState",
                            TypeGame.boardState.getOrdinal(), e);
                }
            default:
                return handleError("Received wrong type, didn't expect BoardState");
        }
    }
    private String handleUpdateDrawCards(String request) throws HandlingException{
        if (expectedState != State.GAME){
            return handleError("Received wrong type, didn't expect UpdateDrawCards");
        }
        try{
            UpdateDrawCards updateDrawCards = gson.fromJson(request, UpdateDrawCards.class);
            String response = new UpdateDrawCardsHandler().handle(updateDrawCards);
            logger.info("UpdateDrawCards was handled successfully");
            return response;//update message
        }catch (JsonSyntaxException e){
            return handleError("Wrong message format from type UpdateDrawCards",
                    TypeGame.updateDrawCards.getOrdinal(), e);
        }

    }
    private String handleMoveValid(String request) throws HandlingException{
        if (expectedState != State.GAME){
            return handleError("Received wrong type, didn't expect MoveValid");
        }
        try{
            MoveValid moveValid = gson.fromJson(request, MoveValid.class);
            String response = new MoveValidHandler().handle(moveValid);
            logger.info("UpdateDrawCards was handled successfully");
            return response;
        }catch (JsonSyntaxException e){
            return handleError("Wrong message format from type MoveValid",
                    TypeGame.moveValid.getOrdinal(), e);
        }

    }
    private String handleFreeze(String request) throws HandlingException{
        if (expectedState != State.GAME){
            return handleError("Received wrong type, didn't expect Freeze");
        }
        new FreezeHandler().handle();// Freezes game, doesn't need argument
        return "Game frozen";
    }
    private String handleUnfreeze(String request) throws HandlingException{
        if (expectedState != State.GAME){
            return handleError("Received wrong type, didn't expect Unfreeze");
        }
        new UnfreezeHandler().handle();// Freezes game, doesn't need argument
        return "Game unfrozen";
    }
    private String handleCancel(String request) throws HandlingException{
        if (expectedState != State.GAME){
            return handleError("Received wrong type, didn't expect Cancel");
        }
        logger.info("Trying to handle Cancel");
        try{
            Cancel cancel = gson.fromJson(request, Cancel.class);
            String response = new CancelHandler().handle(cancel);
            logger.info("ReturnGameList was handled successfully");
            expectedState = State.CONNECT_MENU;
            return response;
        }catch (JsonSyntaxException e){
            return handleError("Wrong message format from type Cancel",
                    TypeGame.cancel.getOrdinal(), e);
        }
    }
    private String handleJoinObs(String request)throws HandlingException{
        if (expectedState != State.GAME){
            return handleError("Received wrong type, didn't expect JoinObs");
        }
        logger.info("Trying to handle JoinObs");
        try {
            JoinObs joinObs = gson.fromJson(request, JoinObs.class);
            String response = new JoinObsHandler().handle(joinObs);
            logger.info("JoinObs was handled successfully");
            return response;
        }catch (JsonSyntaxException e){
            return handleError("Wrong message format from type JoinObs",
                    TypeGame.joinObs.getOrdinal(), e);
        }
    }
    private String handleLiveTimer(String request) throws HandlingException{
        if (expectedState != State.GAME){
            return handleError("Received wrong type, didn't expect LiveTimer");
        }
        logger.info("Trying to handle LiveTimer");
        try {
            LiveTimer liveTimer = gson.fromJson(request, LiveTimer.class);
            String response = new LiveTimerHandler().handle(liveTimer);
            logger.info("livaTimer was handled successfully");
            return response;
        }catch (JsonSyntaxException e){
            return handleError("Wrong message format from type LiveTimer",
                    TypeGame.liveTimer.getOrdinal(), e);
        }
    }
    private String handleTurnTimer(String request) throws HandlingException{
        if (expectedState != State.GAME){
            return handleError("Received wrong type, didn't expect TurnTimer");
        }
        logger.info("Trying to handle TurnTimer");
        try {
            TurnTimer TurnTimer = gson.fromJson(request, TurnTimer.class);
            String response = new TurnTimerHandler().handle(TurnTimer);
            logger.info("TurnTimer was handled successfully");
            return response;
        }catch (JsonSyntaxException e){
            return handleError("Wrong message format from type TurnTimer",
                    TypeGame.turnTimer.getOrdinal(), e);
        }

    }
    private void handleReturnTechData(String request) throws HandlingException{
        return ;
    }
    private boolean isTypeInt(JsonObject jsonRequest) {
        return jsonRequest.has("type")
                && jsonRequest.get("type").isJsonPrimitive()
                && jsonRequest.get("type").getAsJsonPrimitive().isNumber()
                && jsonRequest.get("type").getAsNumber().doubleValue()
                == (int) jsonRequest.get("type").getAsNumber().doubleValue();
    }






}
