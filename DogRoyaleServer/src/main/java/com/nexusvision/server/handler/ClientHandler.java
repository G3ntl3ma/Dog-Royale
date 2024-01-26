package com.nexusvision.server.handler;

import com.google.gson.*;
import com.nexusvision.server.common.Subscriber;
import com.nexusvision.server.controller.MessageBroker;
import com.nexusvision.server.handler.message.game.LeaveObsHandler;
import com.nexusvision.server.handler.message.game.LeavePlayerHandler;
import com.nexusvision.server.handler.message.game.MoveHandler;
import com.nexusvision.server.handler.message.game.ResponseHandler;
import com.nexusvision.server.handler.message.menu.*;
import com.nexusvision.server.model.messages.game.*;
import com.nexusvision.server.model.messages.menu.*;
import com.nexusvision.server.model.messages.menu.Error;
import lombok.extern.log4j.Log4j2;

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
public class ClientHandler extends Handler implements Runnable, Subscriber {

    private final Socket clientSocket;
    private final int clientID;
    private final PrintWriter messageSender;

    private State expectedState = State.CONNECT_TO_SERVER;

    /**
     * Constructor for <code>ClientHandler</code>
     *
     * @param clientSocket An instance representing the socket connection to a client
     * @param clientID     An integer representing the id for the client associated with this ClientHandler
     */
    public ClientHandler(Socket clientSocket, int clientID) {
        this.clientSocket = clientSocket;
        this.clientID = clientID;
        //this might block because iirc you need a buffered reader first, not sure
        PrintWriter writer;
        try {
            writer = new PrintWriter(clientSocket.getOutputStream(), false);
        } catch (Exception e) {
            writer = null;
        }
        messageSender = writer;
        MessageBroker.getInstance().addIdentifier(clientID, this);
    }

    /**
     * Represents the logic for handling incoming messages from a client
     */
    @Override
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));

            String request, response;
            while (true) {
                if ((request = reader.readLine()) != null) {
                    handle(request);
                }
            }
        } catch (IOException e) {
            log.error("Error while trying to read the client message: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
                MessageBroker.getInstance().deleteSubscriber(this);
            } catch (IOException e) {
                log.error("Error while trying to close the connection: " + e.getMessage());
            }
        }
    }

    /**
     * Sends <code>message</code> back to the client
     *
     * @param message The message
     */
    @Override
    public synchronized void sendMessage(String message) {
        messageSender.write(message);
        messageSender.flush();
    }

    /**
     * Lets the client handler know that the game has started
     *
     * @throws ConsistencyException if the expected state doesn't match the correct state
     */
    public void startGame() throws ConsistencyException {
        if (expectedState != State.WAITING_FOR_GAME_START) {
            throw new ConsistencyException("Expected client handler to have the state WAITING_FOR_GAME_START right " +
                    "now, but was actually in the state " + expectedState.toString());
        }
        expectedState = State.NO_MOVE;
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
     */
    private void handle(String request) {
        try {
            JsonObject jsonRequest = JsonParser.parseString(request).getAsJsonObject();
            if (!isTypeInt(jsonRequest)) {
                handleError("Received no valid type");
                return;
            }
            int type = jsonRequest.get("type").getAsInt();
            if (type == TypeMenue.connectToServer.getOrdinal()) {
                handleConnectToServer(request);
            } else if (type == TypeMenue.requestGameList.getOrdinal()
                    || type == TypeMenue.requestTournamentList.getOrdinal()) {
                handleRequestGameListAndFindTournament(request, type);
            } else if (type == TypeMenue.joinGameAsObserver.getOrdinal()) {
                handleJoinGameAsObserver(request);
            } else if (type == TypeMenue.joinGameAsPlayer.getOrdinal()) {
                handleJoinGameAsParticipant(request);
            } else if (type == TypeMenue.requestTechData.getOrdinal()) {
                handleRequestTechData(request);
            } else if (type == TypeGame.response.getOrdinal()) {
                handleResponse(request);
            } else if (type == TypeGame.leaveObs.getOrdinal()) {
                handleLeaveObs(request);
            } else if (type == TypeGame.leavePlayer.getOrdinal()) {
                handleLeavePlayer(request);
            }
            // TODO: Register for tournament, request tournament info
            else {
                handleError("The request has no valid type");
            }
        } catch (JsonSyntaxException e) {
            handleError("The request is not in json format", e);
        } catch (HandlingException e) {
            handleError("Failed to handle the request", e.getType(), e);
        }
    }

    /**
     * Responsible for handling requests of type <code>connectToServer</code>
     *
     * @param request A String representing the request received from the client
     * @throws HandlingException If anything goes wrong while handling the message
     */
    private void handleConnectToServer(String request) throws HandlingException {
        if (expectedState != State.CONNECT_TO_SERVER) {
            handleError("Received wrong type, didn't expect connectToServer");
            return;
        }
        log.info("Trying to connect client " + clientID);
        try {
            ConnectToServer connectToServer = gson.fromJson(request, ConnectToServer.class);
            new ConnectToServerHandler().handle(connectToServer, clientID);
            expectedState = State.REQUEST_GAME_LIST_AND_FIND_TOURNAMENT;
            log.info("Client " + clientID + " connected successfully");
        } catch (JsonSyntaxException e) {
            handleError("Wrong message format from type connectToServer",
                    TypeMenue.connectToServer.getOrdinal(), e);
        }
    }

    /**
     * Responsible for handling requests of type <code>requestGameList</code> and <code>findTournament</code>
     *
     * @param request A String representing the request received from the client
     * @param type An Integer representing the type of the request
     * @throws HandlingException If anything goes wrong while handling the message
     */
    private void handleRequestGameListAndFindTournament(String request, int type) throws HandlingException {
        switch (expectedState) {
            case REQUEST_GAME_LIST_AND_FIND_TOURNAMENT:
                if (type == TypeMenue.requestGameList.getOrdinal()) {
                    handleRequestGameList(request, State.FIND_TOURNAMENT);
                } else {
                    handleFindTournament(request, State.REQUEST_GAME_LIST);
                }
                break;
            case REQUEST_GAME_LIST:
                if (type != TypeMenue.requestGameList.getOrdinal()) {
                    handleError("Received requestTournamentInfo but expected requestGameList",
                            TypeMenue.requestTournamentList.getOrdinal());
                    return;
                }
                handleRequestGameList(request, State.REQUEST_JOIN);
                break;
            case FIND_TOURNAMENT:
                if (type != TypeMenue.requestTournamentList.getOrdinal()) {
                    handleError("Received requestGameList but expected findTournament",
                            TypeMenue.requestGameList.getOrdinal());
                    return;
                }
                handleFindTournament(request, State.REQUEST_JOIN);
                break;
            default:
                handleError("Received wrong type, expected requestGameList or findTournament");
        }
    }

    /**
     * Responsible for handling requests of type <code>requestGameList</code>
     *
     * @param request A String representing the request received from the client
     * @param nextState An object representing the next expected state after successfully handling the current request
     * @throws HandlingException If anything goes wrong while handling the message
     */
    private void handleRequestGameList(String request, State nextState) throws HandlingException {
        log.info("Trying to handle game list request");
        try {
            RequestGameList requestGameList = gson.fromJson(request, RequestGameList.class);
            new RequestGameListHandler().handle(requestGameList, clientID);
            expectedState = nextState;
            log.info("Game list request was successful");
        } catch (JsonSyntaxException e) {
            handleError("Wrong message format from type requestGameList",
                    TypeMenue.requestGameList.getOrdinal(), e);
        }
    }

    /**
     * Responsible for handling requests of type <code>findTournament</code>
     *
     * @param request A String representing the request received from the client
     * @param nextState An object representing the next expected state after successfully handling the current request
     * @throws HandlingException If anything goes wrong while handling the message
     */
    private void handleFindTournament(String request, State nextState) throws HandlingException {
        log.info("Trying to handle tournament info request");
        try {
            RequestTournamentList requestTournamentList = gson.fromJson(request, RequestTournamentList.class);
            new FindTournamentHandler().handle(requestTournamentList, clientID);
            expectedState = nextState;
            log.info("Find tournament request was successful");
        } catch (JsonSyntaxException e) {
            handleError("Wrong message format from type findTournament",
                    TypeMenue.requestTournamentList.getOrdinal(), e);
        }
    }

    /**
     * Responsible for handling requests of type <code>joinGameAsObserver</code>
     *
     * @param request A String representing the request received from the client
     * @throws HandlingException If anything goes wrong while handling the message
     */
    private void handleJoinGameAsObserver(String request) throws HandlingException {
        if (expectedState != State.REQUEST_JOIN) {
            handleError("Received wrong type, didn't expect joinGameAsObserver");
            return;
        }
        log.info("Trying to handle join game as observer request");
        try {
            JoinGameAsObserver joinGameAsObserver = gson.fromJson(request, JoinGameAsObserver.class);
            new JoinGameAsObserverHandler().handle(joinGameAsObserver, clientID);
            expectedState = State.WAITING_FOR_GAME_START;
            log.info("Join game as observer was successful");
        } catch (JsonSyntaxException e) {
            handleError("Wrong message format from type joinGameAsObserver",
                    TypeMenue.joinGameAsObserver.getOrdinal(), e);
        }
    }

    /**
     * Responsible for handling requests of type <code>joinGameAsParticipant</code>
     *
     * @param request A String representing the request received from the client
     * @throws HandlingException If anything goes wrong while handling the message
     */
    private void handleJoinGameAsParticipant(String request) throws HandlingException {
        if (expectedState != State.REQUEST_JOIN) {
            handleError("Received wrong type, didn't expect joinGameAsParticipant");
        }
        log.info("Trying to handle join game as participant request");
        try {
            JoinGameAsPlayer joinGameAsPlayer = gson.fromJson(request, JoinGameAsPlayer.class);
            new JoinGameAsParticipantHandler().handle(joinGameAsPlayer, clientID);
            expectedState = State.WAITING_FOR_GAME_START;
            log.info("Join game as participant was successful");
        } catch (JsonSyntaxException e) {
            handleError("Wrong message format from type joinGameAsParticipant",
                    TypeMenue.joinGameAsPlayer.getOrdinal(), e);
        }
    }

    /**
     * Responsible for handling requests of type <code>requestTechData</code>
     *
     * @param request A String representing the request received from the client
     * @throws HandlingException If anything goes wrong while handling the message
     */
    private void handleRequestTechData(String request) throws HandlingException {
        log.info("Trying to handle tech data request");
        try {
            RequestTechData requestTechData = gson.fromJson(request, RequestTechData.class);
            new RequestTechDataHandler().handle(requestTechData, clientID);
            log.info("Request tech data was successful");
        } catch (JsonSyntaxException e) {
            handleError("Wrong message format from type requestTechData",
                    TypeMenue.requestTechData.getOrdinal(), e);
        }
    }

    /**
     * Responsible for handling requests of type <code>response</code>
     *
     * @param request A String representing the request received from the client
     * @throws HandlingException If anything goes wrong while handling the message
     */
    private void handleResponse(String request) throws HandlingException {
        if (expectedState != State.NO_MOVE && expectedState != State.WAITING_FOR_MOVE) {
            handleError("Didn't expect message of type response", TypeGame.response.getOrdinal());
            return;
        }
        log.info("Received response of client " + clientID);
        try {
            Response response = gson.fromJson(request, Response.class);
            new ResponseHandler().handle(response, clientID);
        } catch (JsonSyntaxException e) {
            handleError("Wrong message format from type response",
                    TypeMenue.requestTechData.getOrdinal(), e);
        }
    }

    /**
     * Responsible for handling requests of type <code>move</code>
     *
     * @param request A String representing the request received from the client
     * @throws HandlingException If anything goes wrong while handling the message
     */
    private void handleMove(String request) throws HandlingException {
        if (expectedState != State.WAITING_FOR_MOVE) {
            handleError("Didn't expect message of type move", TypeGame.move.getOrdinal());
            return;
        }
        log.info("Handling move of client " + clientID);
        try {
            Move move = gson.fromJson(request, Move.class);
            new MoveHandler().handle(move, clientID);
        } catch (JsonSyntaxException e) {
            handleError("Wrong message format from type move",
                    TypeGame.move.getOrdinal(), e);
        }
    }

    /**
     * Responsible for handling requests of type <code>leaveObs</code>
     *
     * @param request A String representing the request received from the client
     * @throws HandlingException If anything goes wrong while handling the message
     */
    private void handleLeaveObs(String request) throws HandlingException {
        if (expectedState != State.NO_MOVE
                && expectedState != State.WAITING_FOR_MOVE
                && expectedState != State.WAITING_FOR_GAME_START) {
            handleError("Received wrong type, didn't expect leaveObs");
            return;
        }
        log.info("Trying to handle leave observer request");
        try {
            LeaveObs leaveObs = gson.fromJson(request, LeaveObs.class);
            new LeaveObsHandler().handle(leaveObs, clientID);
        } catch (JsonSyntaxException e) {
            handleError("Wrong message format from type leaveObs",
                    TypeGame.leaveObs.getOrdinal(), e);
        }
    }

    /**
     * Responsible for handling requests of type <code>leavePlayer</code>
     *
     * @param request A String representing the request received from the client
     * @throws HandlingException If anything goes wrong while handling the message
     */
    private void handleLeavePlayer(String request) throws HandlingException {
        if (expectedState != State.NO_MOVE
                && expectedState != State.WAITING_FOR_MOVE
                && expectedState != State.WAITING_FOR_GAME_START) {
            handleError("Received wrong type, didn't expect leavePlayer");
            return;
        }
        log.info("Trying to handle leave player request");
        try {
            LeavePlayer leavePlayer = gson.fromJson(request, LeavePlayer.class);
            new LeavePlayerHandler().handle(leavePlayer, clientID);
        } catch (JsonSyntaxException e) {
            handleError("Wrong message format from type leavePlayer",
                    TypeGame.leavePlayer.getOrdinal(), e);
        }
    }

    /**
     * Handles errors
     *
     * @param errorMessage A String describing the nature of the error that occurred
     */
    protected void handleError(String errorMessage) {
        log.error(errorMessage);
        Error error = new Error();
        error.setType(TypeMenue.error.getOrdinal());
        error.setMessage(errorMessage);
        String response = gson.toJson(error, Error.class);
        sendMessage(response);
    }

    /**
     * Handles errors
     *
     * @param errorMessage A String describing the nature of the error that occurred
     * @param type An Integer representing the type of the error
     */
    protected void handleError(String errorMessage, int type) {
        log.error(errorMessage);
        Error error = new Error();
        error.setType(TypeMenue.error.getOrdinal());
        error.setType(type);
        error.setMessage(errorMessage);
        String response = gson.toJson(error, Error.class);
        sendMessage(response);
    }

    /**
     * Handles errors
     *
     * @param errorMessage A String describing the nature of the error that occurred
     * @param e An instance of the Exception class representing the exception or error that occurred
     */
    private void handleError(String errorMessage, Exception e) {
        log.error(errorMessage + ": " + e.getMessage());
        Error error = new Error();
        error.setType(TypeMenue.error.getOrdinal());
        error.setMessage(errorMessage);
        String response = gson.toJson(error, Error.class);
        sendMessage(response);
    }

    /**
     * Handles errors
     *
     * @param errorMessage A String describing the nature of the error that occurred
     * @param type An Integer representing the type of the error
     * @param e An instance of the Exception class representing the exception or error that occurred
     */
    private void handleError(String errorMessage, int type, Exception e) {
        log.error(errorMessage + ": " + e.getMessage());
        Error error = new Error();
        error.setType(TypeMenue.error.getOrdinal());
        error.setType(type);
        error.setMessage(errorMessage);
        String response = gson.toJson(error, Error.class);
        sendMessage(response);
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
