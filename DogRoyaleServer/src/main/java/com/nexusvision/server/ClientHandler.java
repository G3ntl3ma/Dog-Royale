package com.nexusvision.server;

import com.google.gson.Gson;
import com.nexusvision.messages.menu.*;

import com.google.gson.JsonObject;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import java.util.ArrayList;

/**
 * ClientHandler, der sich dem übergebenem ClientSocket widmet
 * und mit diesem kommuniziert
 *
 * @author felixwr
 */
public class ClientHandler implements Runnable {
    private static final Logger logger = LogManager.getLogger(ClientHandler.class);
    private final Socket clientSocket;

    public ArrayList<Integer> Ids;

    public ClientHandler(Socket clientSocket, ArrayList<Integer> Ids) {
        this.clientSocket = clientSocket;
	this.Ids = Ids;
    }

    @Override
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter writer = new PrintWriter(
                    clientSocket.getOutputStream(), false);

            String clientMessage;
            while ((clientMessage = reader.readLine()) != null) {
                //  MessageHandling
                String messageResponse = processMessage(clientMessage);
                writer.println(messageResponse);
                logger.info("Von Client wurde folgendes empfangen: " + clientMessage);

                writer.println("Nachricht erhalten");
		writer.flush();
            }

        } catch (IOException e) {
            logger.error(e.getStackTrace());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                logger.error(e.getStackTrace());
            }
        }
    }
    private String processMessage(String clientMessage) {
        // Implement message processing logic here
	String returnMessage = null;
        try {
            logger.info("try");
            Gson gson = new Gson();
            JsonElement jsonElement = JsonParser.parseString(clientMessage);
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            //if in key
            int typeInx = jsonObject.get("type").getAsInt() - 100; //minus 100
            AbstractMenuMessage.TypeMenue typeM = AbstractMenuMessage.TypeMenue.values()[typeInx];
            logger.info("typeM " + typeM);
            switch (typeM) {
                //case connectedToGame:
                 //   ConnectedToGame connectedToGame = gson.fromJson(clientMessage, ConnectedToGame.class);
                //    break;
                // case connectedToServer:
                    // ConnectedToServer connectedToServer = gson.fromJson(clientMessage, ConnectedToServer.class);
                    // break;
                case connectToServer:
                    ConnectToServer connectToServer = gson.fromJson(clientMessage, ConnectToServer.class);
                    returnMessage = connectToServer.getResponse(Ids);
                    break;
                case error:
                    //Error error = gson.fromJson(clientMessage, Error.class); //Error class multiple choices
                    break;
                case findTournament:
                    FindTournament findTournament = gson.fromJson(clientMessage, FindTournament.class);
                    break;
                case joinGameAsObserver:
                    JoinGameAsObserver joinGameAsObserver = gson.fromJson(clientMessage, JoinGameAsObserver.class);
                    break;
                case joinGameAsParticipant:
                    JoinGameAsParticipant joinGameAsParticipant = gson.fromJson(clientMessage, JoinGameAsParticipant.class);
                    break;
                // case registeredForTournament:
                    // RegisteredForTournament registeredForTournament = gson.fromJson(clientMessage, RegisteredForTournament.class);
                    // break;
                case registerForTournament:
                    RegisterForTournament registerForTournament = gson.fromJson(clientMessage, RegisterForTournament.class);
                    break;
                case requestGameList:
                    RequestGameList requestGameList = gson.fromJson(clientMessage, RequestGameList.class);
                    break;
                case requestTechData:
                    RequestTechData requestTechData = gson.fromJson(clientMessage, RequestTechData.class);
                    break;
                case requestTournamentInfo:
                    RequestTournamentInfo requestTournamentInfo = gson.fromJson(clientMessage, RequestTournamentInfo.class);
                    break;
                case returnFindTournament:
                    ReturnFindTournament returnFindTournament = gson.fromJson(clientMessage, ReturnFindTournament.class);
                    break;
                // case returnGameList:
                    // ReturnGameList returnGameList = gson.fromJson(clientMessage, ReturnGameList.class);
                    // break;
                // case returnLobbyConfig:
                    // ReturnLobbyConfig returnLobbyConfig = gson.fromJson(clientMessage, ReturnLobbyConfig.class);
                    // break;
                // case returnTechData:
                    // ReturnTechData returnTechData = gson.fromJson(clientMessage, ReturnTechData.class);
                    // break;
                // case returnTournamentInfo:
                    // ReturnTournamentInfo returnTournamentInfo = gson.fromJson(clientMessage, ReturnTournamentInfo.class);
                    // break;
                default:
                    logger.info("type unknown");
                    break;
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }



        // Return a response message
	    if (returnMessage == null) returnMessage = "response not found, received: " + clientMessage;
        return returnMessage;
    }

}
