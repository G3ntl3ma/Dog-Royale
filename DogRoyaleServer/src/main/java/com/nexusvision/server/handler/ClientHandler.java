package com.nexusvision.server.handler;

import com.google.gson.*;
import com.nexusvision.server.common.Subscriber;
import com.nexusvision.server.controller.MessageBroker;
import com.nexusvision.server.handler.message.game.LeaveObsHandler;
import com.nexusvision.server.handler.message.game.LeavePlayerHandler;
import com.nexusvision.server.handler.message.game.MoveHandler;
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
    private final int clientId;
    private final PrintWriter messageSender;

    private State expectedState = State.CONNECT_TO_SERVER;

    /**
     * Constructor for <code>ClientHandler</code>
     *
     * @param clientSocket An instance representing the socket connection to a client
     * @param clientId     An integer representing the id for the client associated with this ClientHandler
     */
    public ClientHandler(Socket clientSocket, int clientId) {
        this.clientSocket = clientSocket;
        this.clientId = clientId;
        //this might block because iirc you need a buffered reader first, not sure
        PrintWriter writer;
        try {
            writer = new PrintWriter(clientSocket.getOutputStream(), false);
        } catch (Exception e) {
            writer = null;
        }
        messageSender = writer;
        MessageBroker.getInstance().addIdentifier(clientId, this);
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
                MessageBroker.getInstance().deleteSubscriber(clientId);
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
                handleRequestGameTournamentList(request, type);
            } else if (type == TypeMenue.joinGameAsObserver.getOrdinal()) {
                handleJoinGameAsObserver(request);
            } else if (type == TypeMenue.joinGameAsPlayer.getOrdinal()) {
                handleJoinGameAsPlayer(request);
            } else if (type == TypeMenue.registerForTournament.getOrdinal()) {
                handleRegisterForTournament(request);
            } else if (type == TypeMenue.requestTournamentInfo.getOrdinal()) {
                handleRequestTournamentInfo(request);
            } else if (type == TypeMenue.requestTechData.getOrdinal()) {
                handleRequestTechData(request);
            } else if (type == TypeGame.response.getOrdinal()) {
                return; // do nothing
            } else if (type == TypeGame.leaveObs.getOrdinal()) {
                handleLeaveObs(request);
            } else if (type == TypeGame.leavePlayer.getOrdinal()) {
                handleLeavePlayer(request);
            } else if (type == TypeGame.move.getOrdinal()) {
                handleMove(request);
            } else {
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
        log.info("Trying to connect client " + clientId);
        try {
            ConnectToServer connectToServer = gson.fromJson(request, ConnectToServer.class);
            new ConnectToServerHandler().handle(connectToServer, clientId);
            expectedState = State.REQUEST_GAME_TOURNAMENT_LIST;
            log.info("Ausrichter " + clientId + " connected successfully");
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
    private void handleRequestGameTournamentList(String request, int type) throws HandlingException {
        switch (expectedState) {
            case REQUEST_GAME_TOURNAMENT_LIST:
                if (type == TypeMenue.requestGameList.getOrdinal()) {
                    handleRequestGameList(request, State.REQUEST_TOURNAMENT_LIST);
                } else {
                    handleRequestTournamentList(request, State.REQUEST_GAME_LIST);
                }
                break;
            case REQUEST_GAME_LIST:
                if (type != TypeMenue.requestGameList.getOrdinal()) {
                    handleError("Received requestTournamentList but expected requestGameList",
                            TypeMenue.requestTournamentList.getOrdinal());
                    return;
                }
                handleRequestGameList(request, State.REQUEST_JOIN);
                break;
            case REQUEST_TOURNAMENT_LIST:
                if (type != TypeMenue.requestTournamentList.getOrdinal()) {
                    handleError("Received requestGameList but expected requestTournamentList",
                            TypeMenue.requestGameList.getOrdinal());
                    return;
                }
                handleRequestTournamentList(request, State.REQUEST_JOIN);
                break;
            default:
                handleError("Received wrong type, expected requestGameList or requestTournamentList");
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
            new RequestGameListHandler().handle(requestGameList, clientId);
            expectedState = nextState;
            log.info("Request game list was successful");
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
    private void handleRequestTournamentList(String request, State nextState) throws HandlingException {
        log.info("Trying to handle tournament info request");
        try {
            RequestTournamentList requestTournamentList = gson.fromJson(request, RequestTournamentList.class);
            new RequestTournamentListHandler().handle(requestTournamentList, clientId);
            expectedState = nextState;
            log.info("Request tournament list was successful");
        } catch (JsonSyntaxException e) {
            handleError("Wrong message format from type requestTournamentList",
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
            new JoinGameAsObserverHandler().handle(joinGameAsObserver, clientId);
            expectedState = State.WAITING_FOR_GAME_START;
            log.info("Join game as observer was successful");
        } catch (JsonSyntaxException e) {
            handleError("Wrong message format from type joinGameAsObserver",
                    TypeMenue.joinGameAsObserver.getOrdinal(), e);
        }
    }

    /**
     * Responsible for handling requests of type <code>joinGameAsPlayer</code>
     *
     * @param request A String representing the request received from the client
     * @throws HandlingException If anything goes wrong while handling the message
     */
    private void handleJoinGameAsPlayer(String request) throws HandlingException {
        if (expectedState != State.REQUEST_JOIN) {
            handleError("Received wrong type, didn't expect joinGameAsPlayer");
            return;
        }
        log.info("Trying to handle join game as player request");
        try {
            JoinGameAsPlayer joinGameAsPlayer = gson.fromJson(request, JoinGameAsPlayer.class);
            new JoinGameAsPlayerHandler().handle(joinGameAsPlayer, clientId);
            expectedState = State.WAITING_FOR_GAME_START;
            log.info("Join game as player was successful");
        } catch (JsonSyntaxException e) {
            handleError("Wrong message format from type joinGameAsPlayer",
                    TypeMenue.joinGameAsPlayer.getOrdinal(), e);
        }
    }

    private void handleRegisterForTournament(String request) throws HandlingException {
        if (expectedState != State.REQUEST_JOIN) {
            handleError("Received wrong type, didn't expect registerForTournament");
            return;
        }
        log.info("Trying to handle register for tournament request");
        try {
            RegisterForTournament registerForTournament = gson.fromJson(request, RegisterForTournament.class);
            new RegisterForTournamentHandler().handle(registerForTournament, clientId);
            expectedState = State.WAITING_FOR_GAME_START;
            log.info("Register for tournament was successful");
        } catch (JsonSyntaxException e) {
            handleError("Wrong message format from type registerForTournament",
                    TypeMenue.joinGameAsPlayer.getOrdinal(), e);
        }
    }

    private void handleRequestTournamentInfo(String request) throws HandlingException {
        log.info("Trying to handle request tournament info");
        try {
            RequestTournamentInfo requestTournamentInfo = gson.fromJson(request, RequestTournamentInfo.class);
            new RequestTournamentInfoHandler().handle(requestTournamentInfo, clientId);
            log.info("Request tournament info was successful");
        } catch (JsonSyntaxException e) {
            handleError("Wrong message format from type requestTournamentInfo",
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
            new RequestTechDataHandler().handle(requestTechData, clientId);
            log.info("Request tech data was successful");
        } catch (JsonSyntaxException e) {
            handleError("Wrong message format from type requestTechData",
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
        log.info("Handling move of client " + clientId);
        try {
            Move move = gson.fromJson(request, Move.class);
            new MoveHandler().handle(move, clientId);
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
            new LeaveObsHandler().handle(leaveObs, clientId);
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
            new LeavePlayerHandler().handle(leavePlayer, clientId);
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
        REQUEST_GAME_TOURNAMENT_LIST,
        REQUEST_GAME_LIST,
        REQUEST_TOURNAMENT_LIST,
        REQUEST_JOIN,
        WAITING_FOR_GAME_START,
        WAITING_FOR_MOVE,
        NO_MOVE;
    }
}
