package com.nexusvision.server.handler;

import com.google.gson.*;
import com.nexusvision.server.controller.ServerController;
import com.nexusvision.server.handler.message.game.LeaveObsHandler;
import com.nexusvision.server.handler.message.game.LeavePlayerHandler;
import com.nexusvision.server.handler.message.game.MoveHandler;
import com.nexusvision.server.handler.message.game.ResponseHandler;
import com.nexusvision.server.handler.message.menu.*;
import com.nexusvision.server.model.messages.game.*;
import com.nexusvision.server.model.messages.menu.*;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * ClientHandler, which deals with the given ClientSocket
 * and communicates with it.
 *
 * @author felixwr
 */
@Log4j2
public class ClientHandler extends Handler implements Runnable {

    private final Socket clientSocket;
    private final ServerController serverController;
    private final int clientID;
    private final PrintWriter broadcaster;

    private State expectedState = State.CONNECT_TO_SERVER;

    /**
     * Constructor for <code>ClientHandler</code>
     *
     * @param clientSocket An instance representing the socket connection to a client
     * @param clientID     An integer representing the Id for the client associated with this ClientHandler
     */
    public ClientHandler(Socket clientSocket, int clientID) {
        serverController = ServerController.getInstance();
        this.clientSocket = clientSocket;
        this.clientID = clientID;
        //this might block because iirc you need a buffered reader first, not sure
        PrintWriter writer;
        try {
            writer = new PrintWriter(clientSocket.getOutputStream(), false);
        } catch (Exception e) {
            writer = null;
        }
        broadcaster = writer;
    }

    /**
     * Represents the logic for handling incoming messages from a client
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
                if ((request = reader.readLine()) != null
                        && (response = handle(request)) != null) {
                    writer.println(response);
                    writer.flush();
                }
            }
        } catch (IOException e) {
            log.error("Error while trying to read the client message: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                log.error("Error while trying to close the connection: " + e.getMessage());
            }
        }
    }

    /**
     * Writes the specified message to the broadcaster
     */
    public void broadcast(String message) {
        this.broadcaster.write(message);
        this.broadcaster.flush();
    }

    /**
     * Lets the client handler know that the game has started
     *
     * @return true if successful
     */
    public boolean startGame() {
        if (expectedState != State.WAITING_FOR_GAME_START) {
            return false;
        }
        expectedState = State.NO_MOVE;
        return true;
    }

    /**
     * Notifies the client handler that he needs to wait for a move
     *
     * @return true if notifying was successful
     */
    public boolean setWaitingForMove() {
        if (expectedState != State.NO_MOVE) {
            return false;
        }
        expectedState = State.WAITING_FOR_MOVE;
        return true;
    }

    /**
     * Responsible for processing and handling a client request
     *
     * @param request A String representing the request received from the client
     * @return A String representing a response to the client's request
     */
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
                    || type == TypeMenue.findTournament.getOrdinal()) {
                return handleRequestGameListAndFindTournament(request, type);
            } else if (type == TypeMenue.joinGameAsObserver.getOrdinal()) {
                return handleJoinGameAsObserver(request);
            } else if (type == TypeMenue.joinGameAsParticipant.getOrdinal()) {
                return handleJoinGameAsParticipant(request);
            } else if (type == TypeMenue.requestTechData.getOrdinal()) {
                return handleRequestTechData(request);
            } else if (type == TypeGame.response.getOrdinal()) {
                return handleResponse(request);
            } else if (type == TypeGame.leaveObs.getOrdinal()) {
                return handleLeaveObs(request);
            } else if (type == TypeGame.leavePlayer.getOrdinal()) {
                return handleLeavePlayer(request);
            }
            // TODO: Register for tournament, request tournament info
            else {
                return handleError("The request has no valid type");
            }
        } catch (JsonSyntaxException e) {
            return handleError("The request is not in json format", e);
        } catch (HandlingException e) {
            return handleError("Failed to handle the request", e.getType(), e);
        }
    }

    /**
     * Responsible for handling requests of type <code>connectToServer</code>
     *
     * @param request A String representing the request received from the client
     * @return A String representing a response to the client's <code>connectToServer</code> request
     */
    private String handleConnectToServer(String request) throws HandlingException {
        if (expectedState != State.CONNECT_TO_SERVER) {
            return handleError("Received wrong type, didn't expect connectToServer");
        }
        log.info("Trying to connect client " + clientID);
        try {
            ConnectToServer connectToServer = gson.fromJson(request, ConnectToServer.class);
            String response = new ConnectToServerHandler().handle(connectToServer, clientID);
            expectedState = State.REQUEST_GAME_LIST_AND_FIND_TOURNAMENT;
            log.info("Client " + clientID + " connected successfully");
            return response;
        } catch (JsonSyntaxException e) {
            return handleError("Wrong message format from type connectToServer",
                    TypeMenue.connectToServer.getOrdinal(), e);
        }
    }

    /**
     * Responsible for handling requests of type <code>requestGameList</code> and <code>findTournament</code>
     *
     * @param request A String representing the request received from the client
     * @param type An Integer representing the type of the request
     * @return A String representing a response to the client's <code>requestGameList</code> request or <code>findTournament</code> request
     */
    private String handleRequestGameListAndFindTournament(String request, int type) throws HandlingException {
        switch (expectedState) {
            case REQUEST_GAME_LIST_AND_FIND_TOURNAMENT:
                if (type == TypeMenue.requestGameList.getOrdinal()) {
                    return handleRequestGameList(request, State.FIND_TOURNAMENT);
                }
                return handleFindTournament(request, State.REQUEST_GAME_LIST);
            case REQUEST_GAME_LIST:
                if (type != TypeMenue.requestGameList.getOrdinal()) {
                    return handleError("Received requestTournamentInfo but expected requestGameList",
                            TypeMenue.findTournament.getOrdinal());
                }
                return handleRequestGameList(request, State.REQUEST_JOIN);
            case FIND_TOURNAMENT:
                if (type != TypeMenue.findTournament.getOrdinal()) {
                    return handleError("Received requestGameList but expected findTournament",
                            TypeMenue.requestGameList.getOrdinal());
                }
                return handleFindTournament(request, State.REQUEST_JOIN);
            default:
                return handleError("Received wrong type, expected requestGameList or findTournament");
        }
    }

    /**
     * Responsible for handling requests of type <code>requestGameList</code>
     *
     * @param request A String representing the request received from the client
     * @param nextState An object representing the next expected state after successfully handling the current request
     * @return A String representing a response to the client's <code>requestGameList</code> request
     */
    private String handleRequestGameList(String request, State nextState) throws HandlingException {
        log.info("Trying to handle game list request");
        try {
            RequestGameList requestGameList = gson.fromJson(request, RequestGameList.class);
            String response = new RequestGameListHandler().handle(requestGameList, clientID);
            expectedState = nextState;
            log.info("Game list request was successful");
            return response;
        } catch (JsonSyntaxException e) {
            return handleError("Wrong message format from type requestGameList",
                    TypeMenue.requestGameList.getOrdinal(), e);
        }
    }

    /**
     * Responsible for handling requests of type <code>findTournament</code>
     *
     * @param request A String representing the request received from the client
     * @param nextState An object representing the next expected state after successfully handling the current request
     * @return A String representing a response to the client's <code>findTournament</code> request
     */
    private String handleFindTournament(String request, State nextState) throws HandlingException {
        log.info("Trying to handle tournament info request");
        try {
            FindTournament findTournament = gson.fromJson(request, FindTournament.class);
            String response = new FindTournamentHandler().handle(findTournament, clientID);
            expectedState = nextState;
            log.info("Find tournament request was successful");
            return response;
        } catch (JsonSyntaxException e) {
            return handleError("Wrong message format from type findTournament",
                    TypeMenue.findTournament.getOrdinal(), e);
        }
    }

    /**
     * Responsible for handling requests of type <code>joinGameAsObserver</code>
     *
     * @param request A String representing the request received from the client
     * @return A String representing a response to the client's <code>joinGameAsObserver</code> request
     */
    private String handleJoinGameAsObserver(String request) throws HandlingException {
        if (expectedState != State.REQUEST_JOIN) {
            return handleError("Received wrong type, didn't expect joinGameAsObserver");
        }
        log.info("Trying to handle join game as observer request");
        try {
            JoinGameAsObserver joinGameAsObserver = gson.fromJson(request, JoinGameAsObserver.class);
            String response = new JoinGameAsObserverHandler().handle(joinGameAsObserver, clientID);
            expectedState = State.WAITING_FOR_GAME_START;
            log.info("Join game as observer was successful");
            return response;
        } catch (JsonSyntaxException e) {
            return handleError("Wrong message format from type joinGameAsObserver",
                    TypeMenue.joinGameAsObserver.getOrdinal(), e);
        }
    }

    /**
     * Responsible for handling requests of type <code>joinGameAsParticipant</code>
     *
     * @param request A String representing the request received from the client
     * @return A String representing a response to the client's <code>joinGameAsParticipant</code> request
     */
    private String handleJoinGameAsParticipant(String request) throws HandlingException {
        if (expectedState != State.REQUEST_JOIN) {
            return handleError("Received wrong type, didn't expect joinGameAsParticipant");
        }
        log.info("Trying to handle join game as participant request");
        try {
            JoinGameAsParticipant joinGameAsParticipant = gson.fromJson(request, JoinGameAsParticipant.class);
            String response = new JoinGameAsParticipantHandler().handle(joinGameAsParticipant, clientID);
            expectedState = State.WAITING_FOR_GAME_START;
            log.info("Join game as participant was successful");
            return response;
        } catch (JsonSyntaxException e) {
            return handleError("Wrong message format from type joinGameAsParticipant",
                    TypeMenue.joinGameAsParticipant.getOrdinal(), e);
        }
    }

    /**
     * Responsible for handling requests of type <code>requestTechData</code>
     *
     * @param request A String representing the request received from the client
     * @return A String representing a response to the client's <code>requestTechData</code> request
     */
    private String handleRequestTechData(String request) throws HandlingException {
        log.info("Trying to handle tech data request");
        try {
            RequestTechData requestTechData = gson.fromJson(request, RequestTechData.class);
            String response = new RequestTechDataHandler().handle(requestTechData, clientID);
            log.info("Request tech data was successful");
            return response;
        } catch (JsonSyntaxException e) {
            return handleError("Wrong message format from type requestTechData",
                    TypeMenue.requestTechData.getOrdinal(), e);
        }
    }

    /**
     * Responsible for handling requests of type <code>response</code>
     *
     * @param request A String representing the request received from the client
     * @return A String representing a response to the client's <code>response</code> message
     * @throws HandlingException If anything goes wrong while handling the message
     */
    private String handleResponse(String request) throws HandlingException {
        if (expectedState != State.NO_MOVE && expectedState != State.WAITING_FOR_MOVE) {
            return handleError("Didn't expect message of type response", TypeGame.response.getOrdinal());
        }
        log.info("Received response of client " + clientID);
        try {
            Response response = gson.fromJson(request, Response.class);
            return new ResponseHandler().handle(response, clientID);
        } catch (JsonSyntaxException e) {
            return handleError("Wrong message format from type response",
                    TypeMenue.requestTechData.getOrdinal(), e);
        }
    }

    /**
     * Responsible for handling requests of type <code>move</code>
     *
     * @param request A String representing the request received from the client
     * @return A String representing a response to the client's <code>move</code> message
     * @throws HandlingException If anything goes wrong while handling the message
     */
    private String handleMove(String request) throws HandlingException {
        if (expectedState != State.WAITING_FOR_MOVE) {
            return handleError("Didn't expect message of type move", TypeGame.move.getOrdinal());
        }
        log.info("Handling move of client " + clientID);
        try {
            Move move = gson.fromJson(request, Move.class);
            return new MoveHandler().handle(move, clientID);
        } catch (JsonSyntaxException e) {
            return handleError("Wrong message format from type move",
                    TypeGame.move.getOrdinal(), e);
        }
    }

    /**
     * Responsible for handling requests of type <code>leaveObs</code>
     *
     * @param request A String representing the request received from the client
     * @return A String representing a response to the client's <code>leaveObs</code> message
     * @throws HandlingException If anything goes wrong while handling the message
     */
    private String handleLeaveObs(String request) throws HandlingException {
        if (expectedState != State.NO_MOVE
                && expectedState != State.WAITING_FOR_MOVE
                && expectedState != State.WAITING_FOR_GAME_START) {
            return handleError("Received wrong type, didn't expect leaveObs");
        }
        log.info("Trying to handle leave observer request");
        try {
            LeaveObs leaveObs = gson.fromJson(request, LeaveObs.class);
            return new LeaveObsHandler().handle(leaveObs, clientID);
        } catch (JsonSyntaxException e) {
            return handleError("Wrong message format from type leaveObs",
                    TypeGame.leaveObs.getOrdinal(), e);
        }
    }

    /**
     * Responsible for handling requests of type <code>leavePlayer</code>
     *
     * @param request A String representing the request received from the client
     * @return A String representing a response to the client's <code>leavePlayer</code> message
     * @throws HandlingException If anything goes wrong while handling the message
     */
    private String handleLeavePlayer(String request) throws HandlingException {
        if (expectedState != State.NO_MOVE
                && expectedState != State.WAITING_FOR_MOVE
                && expectedState != State.WAITING_FOR_GAME_START) {
            return handleError("Received wrong type, didn't expect leavePlayer");
        }
        log.info("Trying to handle leave player request");
        try {
            LeavePlayer leavePlayer = gson.fromJson(request, LeavePlayer.class);
            return new LeavePlayerHandler().handle(leavePlayer, clientID);
        } catch (JsonSyntaxException e) {
            return handleError("Wrong message format from type leavePlayer",
                    TypeGame.leavePlayer.getOrdinal(), e);
        }
    }

    /**
     * Checks whether an object contains a field named "type" with a numeric value that can be represented as an integer
     *
     * @param jsonRequest A JSON object typically representing the request received from the client
     * @return A Boolean representing if type can be displayed as an Integer or not
     */
    private boolean isTypeInt(JsonObject jsonRequest) {
        return jsonRequest.has("type")
                && jsonRequest.get("type").isJsonPrimitive()
                && jsonRequest.get("type").getAsJsonPrimitive().isNumber()
                && jsonRequest.get("type").getAsNumber().doubleValue()
                == (int) jsonRequest.get("type").getAsNumber().doubleValue();
    }

    private enum State {
        CONNECT_TO_SERVER,
        REQUEST_GAME_LIST_AND_FIND_TOURNAMENT,
        REQUEST_GAME_LIST,
        FIND_TOURNAMENT,
        REQUEST_JOIN,
        WAITING_FOR_GAME_START,
        WAITING_FOR_MOVE,
        NO_MOVE;
    }
}
