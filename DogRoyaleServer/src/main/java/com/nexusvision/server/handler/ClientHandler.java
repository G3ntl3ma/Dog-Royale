package com.nexusvision.server.handler;

import com.google.gson.*;


import com.nexusvision.server.controller.ServerController;
import com.nexusvision.server.handler.message.menu.*;
import com.nexusvision.server.model.messages.menu.*;
import com.nexusvision.server.model.messages.menu.Error;
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
public class ClientHandler implements Runnable {
    private static final Logger logger = LogManager.getLogger(ClientHandler.class);

    private final Socket clientSocket;
    private final ServerController serverController;
    private final int clientID;

    private enum State {
        CONNECT_TO_SERVER,
        REQUEST_GAME_LIST_AND_TOURNAMENT_INFO,
        REQUEST_GAME_LIST,
        REQUEST_TOURNAMENT_INFO,
        REQUEST_JOIN,
        REQUEST_FIND_TOURNAMENT,
        REQUEST_TECH_DATA;
        // TODO: Add requests for game
    };

    private State expectedState = State.CONNECT_TO_SERVER;

    Gson gson = new GsonBuilder()
            .registerTypeAdapter(Object.class, new NewLineAppendingSerializer<>())
            .create();

    public ClientHandler(Socket clientSocket) {
        serverController = ServerController.getInstance();
        this.clientSocket = clientSocket;
        this.clientID = serverController.generateClientID();
    }

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
            } // TODO: ALl other cases
            else {
                return handleError("The request has no valid type");
            }
        } catch (JsonSyntaxException e) {
            return handleError("The request is not in json format", e);
        }
    }

    private String handleConnectToServer(String request) {
        logger.info("Trying to connect client " + clientID);
        if (expectedState != State.CONNECT_TO_SERVER) {
            return handleError("Received wrong type, expected connectToServer");
        }
        try {
            ConnectToServer connectToServer = gson.fromJson(request, ConnectToServer.class);
            String response = new ConnectToServerHandler().handle(connectToServer, clientID);
            expectedState = State.REQUEST_GAME_LIST_AND_TOURNAMENT_INFO;
            logger.info("Client " + clientID + " connected successfully");
            return response;
        } catch (JsonSyntaxException e) {
            return handleError("Wrong message format from type connectToServer",
                    TypeMenue.connectToServer, e);
        }
    }

    private String handleRequestGameListAndTournamentInfo(String request, int type) {
        switch (expectedState) {
            case REQUEST_GAME_LIST_AND_TOURNAMENT_INFO:
                if (type == TypeMenue.requestGameList.getOrdinal()) {
                    return handleRequestGameList(request, State.REQUEST_TOURNAMENT_INFO);
                }
                return handleRequestTournamentInfo(request, State.REQUEST_GAME_LIST);
            case REQUEST_GAME_LIST:
                if (type != TypeMenue.requestGameList.getOrdinal()) {
                    return handleError("Received requestTournamentInfo but expected requestGameList",
                            TypeMenue.returnTournamentInfo);
                }
                return handleRequestGameList(request, State.REQUEST_JOIN);
            case REQUEST_TOURNAMENT_INFO:
                if (type != TypeMenue.requestTournamentInfo.getOrdinal()) {
                    return handleError("Received requestGameList but expected requestTournamentInfo",
                            TypeMenue.requestGameList);
                }
                return handleRequestTournamentInfo(request, State.REQUEST_JOIN);
            default:
                return handleError("Received wrong type, expected requestGameList or requestTournamentInfo");
        }
    }

    private String handleRequestGameList(String request, State nextState) {
        try {
            RequestGameList requestGameList = gson.fromJson(request, RequestGameList.class);
            String response = new RequestGameListHandler().handle(requestGameList, clientID);
            expectedState = nextState;
            logger.info("Game list request was successful");
            return response;
        } catch (JsonSyntaxException e) {
            return handleError("Wrong message format from type requestGameList",
                    TypeMenue.requestGameList, e);
        }
    }

    private String handleRequestTournamentInfo(String request, State nextState) {
        try {
            RequestTournamentInfo requestTournamentInfo = gson.fromJson(request, RequestTournamentInfo.class);
            String response = new RequestTournamentInfoHandler().handle(requestTournamentInfo, clientID);
            expectedState = nextState;
            logger.info("Tournament info request was successful");
            return response;
        } catch (JsonSyntaxException e) {
            return handleError("Wrong message format from type requestTournamentInfo",
                    TypeMenue.requestTournamentInfo, e);
        }
    }

    private String handleError(String errorMessage) {
        logger.error(errorMessage);
        Error error = new Error();
        error.setType(TypeMenue.error.getOrdinal());
        error.setMessage(errorMessage);
        return gson.toJson(error, Error.class);
    }

    private String handleError(String errorMessage, TypeMenue type) {
        logger.error(errorMessage);
        Error error = new Error();
        error.setType(TypeMenue.error.getOrdinal());
        error.setType(type.getOrdinal());
        error.setMessage(errorMessage);
        return gson.toJson(error, Error.class);
    }

    private String handleError(String errorMessage, Exception e) {
        logger.error(errorMessage + ": " + e.getMessage());
        Error error = new Error();
        error.setType(TypeMenue.error.getOrdinal());
        error.setMessage(errorMessage);
        return gson.toJson(error, Error.class);
    }

    private String handleError(String errorMessage, TypeMenue type, Exception e) {
        logger.error(errorMessage + ": " + e.getMessage());
        Error error = new Error();
        error.setType(TypeMenue.error.getOrdinal());
        error.setType(type.getOrdinal());
        error.setMessage(errorMessage);
        return gson.toJson(error, Error.class);
    }

//    private void handleMenu(BufferedReader reader, PrintWriter writer) throws IOException {
//        handleConnectToServer(reader, writer);
//        handleRequestGameListAndTournamentInfo(reader, writer);
//    }

//    private void handleConnectToServer(BufferedReader reader, PrintWriter writer) throws IOException {
//        String request, response;
//        boolean isFinished = false;
//        logger.info("Trying to connect client " + clientID);
//        while (!isFinished) {
//            if ((request = reader.readLine()) != null) {
//                JsonObject jsonRequest = JsonParser.parseString(request).getAsJsonObject();
//                if (isFromType(jsonRequest, TypeMenue.connectToServer.getOrdinal())) {
//                    try {
//                        ConnectToServer connectToServer = gson.fromJson(request, ConnectToServer.class);
//                        response = new ConnectToServerHandler().handle(connectToServer, clientID);
//                        isFinished = true;
//                    } catch (JsonSyntaxException e) {
//                        logger.error("Wrong message format from type connectToServer" + e.getMessage());
//                        Error error = new Error();
//                        error.setType(TypeMenue.error.getOrdinal());
//                        error.setDataId(TypeMenue.connectToServer.getOrdinal());
//                        error.setMessage("Wrong message format");
//                        response = gson.toJson(error, Error.class);
//                    }
//                } else {
//                    logger.error("Received wrong or no type");
//                    Error error = new Error();
//                    error.setType(TypeMenue.error.getOrdinal());
//                    error.setDataId(TypeMenue.connectToServer.getOrdinal());
//                    error.setMessage("Wrong type");
//                    response = gson.toJson(error, Error.class);
//                }
//                writer.println(response);
//                writer.flush();
//            }
//        }
//        logger.info("Client " + clientID + " connected successfully");
//    }
//
//    private void handleRequestGameListAndTournamentInfo(BufferedReader reader, PrintWriter writer) throws IOException {
//        String request, response;
//        boolean isGameListFinished = false, isTournamentInfoFinished = false;
//        logger.info("Trying to provide game list and tournament info...");
//        while (!isGameListFinished || !isTournamentInfoFinished) {
//            if ((request = reader.readLine()) != null) {
//                JsonObject jsonRequest = JsonParser.parseString(request).getAsJsonObject();
//                if (!isGameListFinished && isFromType(jsonRequest, TypeMenue.requestGameList.getOrdinal())) {
//                    try {
//                        RequestGameList requestGameList = gson.fromJson(request, RequestGameList.class);
//                        response = new RequestGameListHandler().handle(requestGameList, clientID);
//                        isGameListFinished = true;
//                    } catch (JsonSyntaxException e) {
//                        logger.error("Wrong message format from type requestGameList" + e.getMessage());
//                        Error error = new Error();
//                        error.setType(TypeMenue.error.getOrdinal());
//                        error.setDataId(TypeMenue.requestGameList.getOrdinal());
//                        error.setMessage("Wrong message format");
//                        response = gson.toJson(error, Error.class);
//                    }
//                } else if (!isTournamentInfoFinished &&
//                        isFromType(jsonRequest, TypeMenue.requestTournamentInfo.getOrdinal())) {
//                    try {
//                        RequestTournamentInfo requestTournamentInfo = gson.fromJson(request, RequestTournamentInfo.class);
//                        response = new RequestTournamentInfoHandler().handle(requestTournamentInfo, clientID);
//                        isTournamentInfoFinished = true;
//                    } catch (JsonSyntaxException e) {
//                        logger.error("Wrong message format from type requestTournamentInfo" + e.getMessage());
//                        Error error = new Error();
//                        error.setType(TypeMenue.error.getOrdinal());
//                        error.setDataId(TypeMenue.requestTournamentInfo.getOrdinal());
//                        error.setMessage("Wrong message format");
//                        response = gson.toJson(error, Error.class);
//                    }
//                } else {
//                    logger.error("Received wrong or no type");
//                    Error error = new Error();
//                    error.setType(TypeMenue.error.getOrdinal());
//                    error.setMessage("Wrong type, expected either requestGameList or requestTournamentInfo");
//                    response = gson.toJson(error, Error.class);
//                }
//                writer.println(response);
//                writer.flush();
//            }
//        }
//        logger.info("Successfully provided game list and tournament info");
//    }

//    private void handleJoin(BufferedReader reader, PrintWriter writer) throws IOException {
//
//    }

    private boolean isTypeInt(JsonObject jsonRequest) {
        return jsonRequest.has("type")
                && jsonRequest.get("type").isJsonPrimitive()
                && jsonRequest.get("type").getAsJsonPrimitive().isNumber()
                && jsonRequest.get("type").getAsNumber().doubleValue()
                == (int) jsonRequest.get("type").getAsNumber().doubleValue();
    }

//    private String processMessage(String clientMessage) {
//        // Implement message processing logic here
//        String returnMessage = null;
//        try {
//            logger.info("try");
//            Gson gson = new Gson();
//            JsonElement jsonElement = JsonParser.parseString(clientMessage);
//            JsonObject jsonObject = jsonElement.getAsJsonObject();
//            //if in key
//            int typeInx = jsonObject.get("type").getAsInt() - 100; //minus 100
//            TypeMenue typeM = TypeMenue.values()[typeInx];
//            logger.info("typeM " + typeM);
//            switch (typeM) {
//                //case connectedToGame:
//                //   ConnectedToGame connectedToGame = gson.fromJson(clientMessage, ConnectedToGame.class);
//                //    break;
//                // case connectedToServer:
//                // ConnectedToServer connectedToServer = gson.fromJson(clientMessage, ConnectedToServer.class);
//                // break;
//                case connectToServer:
//                    ConnectToServer connectToServer = gson.fromJson(clientMessage, ConnectToServer.class);
//                    returnMessage = new ConnectToServerHandler().handle(connectToServer, clientID);
//                    logger.info("username set to ");
//                    this.logUsername();
//                    logger.info("isObserver bool ");
//                    this.logObserver();
//                    break;
//                case error:
//                    //Error error = gson.fromJson(clientMessage, Error.class); //Error class multiple choices
//                    break;
//                case findTournament:
//                    FindTournament findTournament = gson.fromJson(clientMessage, FindTournament.class);
//                    returnMessage = new FindTournamentHandler().handle(findTournament, clientID);
//                    break;
//                case joinGameAsObserver:
//                    JoinGameAsObserver joinGameAsObserver = gson.fromJson(clientMessage, JoinGameAsObserver.class);
//                    returnMessage = new JoinGameAsObserverHandler().handle(joinGameAsObserver, clientID);
//                    break;
//                case joinGameAsParticipant:
//                    JoinGameAsParticipant joinGameAsParticipant = gson.fromJson(clientMessage, JoinGameAsParticipant.class);
//                    returnMessage = new JoinGameAsParticipantHandler().handle(joinGameAsParticipant, clientID);
//                    break;
//                // case registeredForTournament:
//                // RegisteredForTournament registeredForTournament = gson.fromJson(clientMessage, RegisteredForTournament.class);
//                // break;
//                case registerForTournament:
//                    RegisterForTournament registerForTournament = gson.fromJson(clientMessage, RegisterForTournament.class);
//                    break;
//                case requestGameList:
//                    RequestGameList requestGameList = gson.fromJson(clientMessage, RequestGameList.class);
//                    break;
//                case requestTechData:
//                    RequestTechData requestTechData = gson.fromJson(clientMessage, RequestTechData.class);
//                    returnMessage = new RequestTechDataHandler().handle(requestTechData, clientID);
//                    break;
//                case requestTournamentInfo:
//                    RequestTournamentInfo requestTournamentInfo = gson.fromJson(clientMessage, RequestTournamentInfo.class);
//                    returnMessage = new RequestTournamentInfoHandler().handle(requestTournamentInfo, clientID);
//                    break;
//                case returnFindTournament:
//                    ReturnFindTournament returnFindTournament = gson.fromJson(clientMessage, ReturnFindTournament.class);
//                    break;
//                // case returnGameList:
//                // ReturnGameList returnGameList = gson.fromJson(clientMessage, ReturnGameList.class);
//                // returnMessage = new RequestGameListHandler().handle(requestGameList, clientID);
//                // break;
//                // case returnLobbyConfig:
//                // ReturnLobbyConfig returnLobbyConfig = gson.fromJson(clientMessage, ReturnLobbyConfig.class);
//                // break;
//                // case returnTechData:
//                // ReturnTechData returnTechData = gson.fromJson(clientMessage, ReturnTechData.class);
//                // break;
//                // case returnTournamentInfo:
//                // ReturnTournamentInfo returnTournamentInfo = gson.fromJson(clientMessage, ReturnTournamentInfo.class);
//                // break;
//                default:
//                    logger.info("type unknown");
//                    break;
//            }
//
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//
//        // Return a response message
//        if (returnMessage == null) returnMessage = "response not found, received: " + clientMessage;
//        return returnMessage;
//    }

}
