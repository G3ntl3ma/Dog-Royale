package com.nexusvision.server.handler;

import com.google.gson.*;


import com.nexusvision.server.controller.ServerController;
import com.nexusvision.server.handler.message.menu.*;
import com.nexusvision.server.model.messages.menu.*;
import com.nexusvision.utils.NewLineAppendingSerializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.Socket;

/**
 * ClientHandler, which deals with the given ClientSocket
 * and communicates with it.
 *
 * @author felixwr
 */
public class ClientHandler extends Handler implements Runnable {
    private static final Logger logger = LogManager.getLogger(ClientHandler.class);

    private final Socket clientSocket;
    private final ServerController serverController;
    private final int clientID;
    
    private PrintWriter broadcaster;

    private enum State {
        CONNECT_TO_SERVER,
        REQUEST_GAME_LIST_AND_TOURNAMENT_INFO,
        REQUEST_GAME_LIST,
        REQUEST_TOURNAMENT_INFO,
        REQUEST_JOIN,
        REQUEST_FIND_TOURNAMENT,
        REQUEST_TECH_DATA,
        START_GAME;
        // TODO: Add requests for game
    };
    
    private State expectedState = State.CONNECT_TO_SERVER;

    Gson gson = new GsonBuilder()
            .registerTypeAdapter(Object.class, new NewLineAppendingSerializer<>())
            .create();

    /**
     * Constructor for the clienthandler
     *
     * @param clientSocket An instance representing the socket connection to a client
     * @param clientID An integer representing the Id for the client associated with this ClientHandler
     */
    public ClientHandler(Socket clientSocket, int clientID) {
        serverController = ServerController.getInstance();
        this.clientSocket = clientSocket;
        this.clientID = clientID;
	//this might block because iirc you need a buffered reader first, not sure
        try {
            this.broadcaster = new PrintWriter(clientSocket.getOutputStream(), false);
        }
        catch(Exception e) {
            this.broadcaster = null;
        }
    }

    /**
     * Represents the logic for handling incoming messages from a client
     *
     */
    @Override
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter writer = new PrintWriter(
                    clientSocket.getOutputStream(), false);

            String request, response;
            while (true) {
                if ((request = reader.readLine()) != null) {
                    response = handle(request);
                    writer.println(response);
                    writer.flush();
                }
            }
        } catch (IOException e) {
            logger.error("Error while trying to read the client message: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                logger.error("Error while trying to close the connection: " + e.getMessage());
            }
        }
    }

    /**
     * Writes the specified message to the broadcaster
     *
     */
    public void broadcast(String message) {
	this.broadcaster.write(message);
	this.broadcaster.flush();
    }

    private String handle(String request) {
        try {
            JsonObject jsonRequest = JsonParser.parseString(request).getAsJsonObject();
            if (!isTypeInt(jsonRequest)) {
                return handleError("Received no valid type");
            }
            int type = jsonRequest.get("type").getAsInt();
            if (type == TypeMenue.connectToServer.getOrdinal()) {
                return handleConnectToServer(request);
            } else if (type == TypeMenue.requestGameList.getOrdinal()
                    || type == TypeMenue.requestTournamentInfo.getOrdinal()) {
                return handleRequestGameListAndTournamentInfo(request, type);
            } else if (type == TypeMenue.joinGameAsObserver.getOrdinal()) {
                return handleJoinGameAsObserver(request);
            } else if (type == TypeMenue.joinGameAsParticipant.getOrdinal()) {
                return handleJoinGameAsParticipant(request);
            } else if (type == TypeMenue.requestTechData.getOrdinal()) {
                return handleRequestTechData(request);
            }
            // TODO: ALl other cases
            else {
                return handleError("The request has no valid type");
            }
        } catch (JsonSyntaxException e) {
            return handleError("The request is not in json format", e);
        } catch (HandlingException e) {
            return handleError("Failed to handle the request", e.getType(), e);
        }
    }

    private String handleConnectToServer(String request) throws HandlingException {
        if (expectedState != State.CONNECT_TO_SERVER) {
            return handleError("Received wrong type, didn't expect connectToServer");
        }
        logger.info("Trying to connect client " + clientID);
        try {
            ConnectToServer connectToServer = gson.fromJson(request, ConnectToServer.class);
            String response = new ConnectToServerHandler().handle(connectToServer, clientID);
            expectedState = State.REQUEST_GAME_LIST_AND_TOURNAMENT_INFO;
            logger.info("Client " + clientID + " connected successfully");
            return response;
        } catch (JsonSyntaxException e) {
            return handleError("Wrong message format from type connectToServer",
                    TypeMenue.connectToServer.getOrdinal(), e);
        }
    }

    private String handleRequestGameListAndTournamentInfo(String request, int type) throws HandlingException {
        switch (expectedState) {
            case REQUEST_GAME_LIST_AND_TOURNAMENT_INFO:
                if (type == TypeMenue.requestGameList.getOrdinal()) {
                    return handleRequestGameList(request, State.REQUEST_TOURNAMENT_INFO);
                }
                return handleRequestTournamentInfo(request, State.REQUEST_GAME_LIST);
            case REQUEST_GAME_LIST:
                if (type != TypeMenue.requestGameList.getOrdinal()) {
                    return handleError("Received requestTournamentInfo but expected requestGameList",
                            TypeMenue.returnTournamentInfo.getOrdinal());
                }
                return handleRequestGameList(request, State.REQUEST_JOIN);
            case REQUEST_TOURNAMENT_INFO:
                if (type != TypeMenue.requestTournamentInfo.getOrdinal()) {
                    return handleError("Received requestGameList but expected requestTournamentInfo",
                            TypeMenue.requestGameList.getOrdinal());
                }
                return handleRequestTournamentInfo(request, State.REQUEST_JOIN);
            default:
                return handleError("Received wrong type, expected requestGameList or requestTournamentInfo");
        }
    }

    private String handleRequestGameList(String request, State nextState) throws HandlingException {
        logger.info("Trying to handle game list request");
        try {
            RequestGameList requestGameList = gson.fromJson(request, RequestGameList.class);
            String response = new RequestGameListHandler().handle(requestGameList, clientID);
            expectedState = nextState;
            logger.info("com.nexusvision.server.model.gamelogic.Game list request was successful");
            return response;
        } catch (JsonSyntaxException e) {
            return handleError("Wrong message format from type requestGameList",
                    TypeMenue.requestGameList.getOrdinal(), e);
        }
    }

    private String handleRequestTournamentInfo(String request, State nextState) throws HandlingException {
        logger.info("Trying to handle tournament info request");
        try {
            RequestTournamentInfo requestTournamentInfo = gson.fromJson(request, RequestTournamentInfo.class);
            String response = new RequestTournamentInfoHandler().handle(requestTournamentInfo, clientID);
            expectedState = nextState;
            logger.info("Tournament info request was successful");
            return response;
        } catch (JsonSyntaxException e) {
            return handleError("Wrong message format from type requestTournamentInfo",
                    TypeMenue.requestTournamentInfo.getOrdinal(), e);
        }
    }

    private String handleJoinGameAsObserver(String request) throws HandlingException {
        if (expectedState != State.REQUEST_JOIN) {
            return handleError("Received wrong type, didn't expect joinGameAsObserver");
        }
        logger.info("Trying to handle join game as observer request");
        try {
            JoinGameAsObserver joinGameAsObserver = gson.fromJson(request, JoinGameAsObserver.class);
            String response = new JoinGameAsObserverHandler().handle(joinGameAsObserver, clientID);
            expectedState = State.START_GAME;
            logger.info("Join game as observer was successful");
            return response;
        } catch (JsonSyntaxException e) {
            return handleError("Wrong message format from type joinGameAsObserver",
                    TypeMenue.joinGameAsObserver.getOrdinal(), e);
        }
    }

    private String handleJoinGameAsParticipant(String request) throws HandlingException {
        if (expectedState != State.REQUEST_JOIN) {
            return handleError("Received wrong type, didn't expect joinGameAsParticipant");
        }
        logger.info("Trying to handle join game as participant request");
        try {
            JoinGameAsParticipant joinGameAsParticipant = gson.fromJson(request, JoinGameAsParticipant.class);
            String response = new JoinGameAsParticipantHandler().handle(joinGameAsParticipant, clientID);
            expectedState = State.START_GAME;
            logger.info("Join game as participant was successful");
            return response;
        } catch (JsonSyntaxException e) {
            return handleError("Wrong message format from type joinGameAsParticipant",
                    TypeMenue.joinGameAsParticipant.getOrdinal(), e);
        }
    }

    private String handleRequestTechData(String request) throws HandlingException {
        logger.info("Trying to handle tech data request");
        try {
            RequestTechData requestTechData = gson.fromJson(request, RequestTechData.class);
            String response = new RequestTechDataHandler().handle(requestTechData, clientID);
            logger.info("Request tech data was successful");
            return response;
        } catch (JsonSyntaxException e) {
            return handleError("Wrong message format from type requestTechData",
                    TypeMenue.requestTechData.getOrdinal(), e);
        }
    }

    private boolean isTypeInt(JsonObject jsonRequest) {
        return jsonRequest.has("type")
                && jsonRequest.get("type").isJsonPrimitive()
                && jsonRequest.get("type").getAsJsonPrimitive().isNumber()
                && jsonRequest.get("type").getAsNumber().doubleValue()
                == (int) jsonRequest.get("type").getAsNumber().doubleValue();
    }
}
