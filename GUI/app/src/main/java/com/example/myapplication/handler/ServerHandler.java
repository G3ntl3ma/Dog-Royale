package GUI.app.src.main.java.com.example.myapplication.handler;

import GUI.app.src.main.java.com.example.myapplication.handler.messageHandler.menu.ConnectedToServerHandler;
import GUI.app.src.main.java.com.example.myapplication.handler.messageHandler.menu.ReturnFindTournamentHandler;
import GUI.app.src.main.java.com.example.myapplication.handler.messageHandler.menu.ReturnGameListHandler;
import GUI.app.src.main.java.com.example.myapplication.messages.game.TypeGame;
import GUI.app.src.main.java.com.example.myapplication.messages.menu.TypeMenu;
import com.example.myapplication.messages.menu.*;
import com.google.gson.*;
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
    private static final Logger logger = LogManager.getLogger(ServerHandler.class);
    private final Socket serverSocket;
    @Getter
    @Setter
    private static int clientID;
    private enum State {
        CONNECT_MENU,
        REQUEST_FIND_TOURNAMENT,
        REQUEST_GAME_LIST,
        MAIN_MENU,
        TOURNAMENT_MENU,
        GAMES_MENU,
        LOBBY,
        GAME,
    }

    private State expectedState = State.CONNECT_MENU;
    Gson gson = new GsonBuilder()
            .registerTypeAdapter(Object.class, new NewLineAppendingSerializer<>())
            .create();

    public ServerHandler(Socket serverSocket)
    {
        this.serverSocket = serverSocket;
    }
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(serverSocket.getInputStream()));
            PrintWriter writer = new PrintWriter(
                    serverSocket.getOutputStream(), false);

            String request, response;
            while (true) {
                if ((request = reader.readLine()) != null) {
                    response = handle(request);
                    writer.println(response);
                    writer.flush();
                }
            }
        } catch (IOException e) {
            logger.error("Error while trying to read the server message: " + e.getMessage());
        } finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                logger.error("Error while trying to close the connection: " + e.getMessage());
            }
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
            } else if (type == TypeMenu.returnLobbyConfig.getOrdinal()) {
                return handleReturnLobbyConfig(request);
            } else if (type == TypeMenu.error.getOrdinal()) {
                return handleError(request);
            } else if (type == TypeMenu.returnTechData.getOrdinal()) {
                handleReturnTechData(request);
            } else if (type == TypeGame.boardState.getOrdinal()) {
                return handleBoardState(request);
            } else if (type == TypeGame.drawCards.getOrdinal()) {
                return handleDrawCards(request);
            } else if (type == TypeGame.updateDrawCards.getOrdinal()) {
                return handleUpdateDrawCards(request);
            } else if (type == TypeGame.moveValid.getOrdinal()) {
                return handleUpdateMoveValid(request);
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

    }
    private String handleConnectedToServer(String request) throws HandlingException {
        if (expectedState != State.CONNECT_MENU) {
            return handleError("Received wrong type, didn't expect connectedToServer");
        }
        logger.info("Trying to handle ConnectedToServer");
        try {
            com.example.myapplication.messages.menu.ConnectedToServer connectedToServer = gson.fromJson(request, com.example.myapplication.messages.menu.ConnectedToServer.class);
            String response = new ConnectedToServerHandler().handle(connectedToServer);//sets ID for the Client
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
            com.example.myapplication.messages.menu.ReturnFindTournament returnFindTournament = gson.fromJson(request, com.example.myapplication.messages.menu.ReturnFindTournament.class);
            String response = new ReturnFindTournamentHandler().handle(returnFindTournament);//sends RequestGameList
            expectedState = State.REQUEST_GAME_LIST;
            logger.info("RequestTournamentInfo was handled successfully");
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
            com.example.myapplication.messages.menu.ReturnGameList returnGameList = gson.fromJson(request, com.example.myapplication.messages.menu.ReturnGameList.class);
            String response = new ReturnGameListHandler().handle(returnGameList);
        }
    }





}
