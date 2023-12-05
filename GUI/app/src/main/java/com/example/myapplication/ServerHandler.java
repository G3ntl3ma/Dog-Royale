//package com.example.myapplication;
//import com.example.myapplication.messages.game.AbstractGameMessage;
//import com.example.myapplication.messages.game.BoardState;
//import com.example.myapplication.messages.game.DrawCards;
//import com.example.myapplication.messages.game.Freeze;
//import com.example.myapplication.messages.game.Kick;
//import com.example.myapplication.messages.game.Move;
//import com.example.myapplication.messages.game.MoveValid;
//import com.example.myapplication.messages.game.Unfreeze;
//import com.example.myapplication.messages.menu.AbstractMenuMessage;
//import com.example.myapplication.messages.menu.ConnectedToGame;
//import com.example.myapplication.messages.menu.ConnectedToServer;
//import com.example.myapplication.messages.menu.Error;
//import com.example.myapplication.messages.menu.RegisteredForTournament;
//import com.example.myapplication.messages.menu.ReturnGameList;
//import com.example.myapplication.messages.menu.ReturnTechData;
//import com.example.myapplication.messages.menu.ReturnTournamentInfo;
//import com.example.myapplication.messages.sync.JoinObs;
//import com.example.myapplication.messages.sync.LiveTimer;
//import com.example.myapplication.messages.sync.TurnTimer;
//import com.example.myapplication.messages.sync.AbstractSyncMessage;
//import com.google.gson.Gson;
//import com.google.gson.JsonElement;
//import com.google.gson.JsonObject;
//import com.google.gson.JsonParser;
//
//
//// import org.apache.logging.log4j.LogManager;
//// import org.apache.logging.log4j.Logger;
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.PrintWriter;
//import java.net.Socket;
///**
// * Soll Anfragen des Servers auswerten. Funktioniert noch nicht und ist nur Test.
// * Ist analog zur Klasse 'ClientHandler' vom Branch develop-server-felixwr
// * Ich weiß noch nicht, wie processMessage(String serverMessage) serverMessage erhält
// * Muss aber irgendwie gehen
// *
// * processMessage erkennt den Typen der Nachricht und ruft dann die Methode .getResponse() auf
// * der jeweiligen Klasse auf. Diese Methoden sollen die Verarbeitungslogik enthalten und müssen
// * noch geschrieben werden
// *
// * @author Mattes
// */
//
//
//public class ServerHandler {
//    // private static final Logger logger = LogManager.getLogger(ServerHandler.class);
//    private final Socket serverSocket; //Abdou: maybe should be static? because the Beobachter will only be connected to one server.
//
//    //i think client id should be here, since it is only needed to send messages to the server
//    private static int ClientId = -1;
//
//    public ServerHandler(Socket serverSocket) {
//        this.serverSocket = serverSocket;
//    }
//
//    private String processMessage(String serverMessage) {
//        String returnMessage = null;
//        try {
//            // logger.info("try");
//            Gson gson = new Gson();
//            JsonElement jsonElement = JsonParser.parseString(serverMessage);
//            JsonObject jsonObject = jsonElement.getAsJsonObject();
//
//            int typeInx = jsonObject.get("type").getAsInt() - 100;
//            AbstractMenuMessage.TypeMenue typeM = AbstractMenuMessage.TypeMenue.values()[typeInx];
//            // logger.info("typeM: " + typeM);
//            switch (typeM) {
//                case connectedToServer:
//                    ConnectedToServer connectedToServer = gson.fromJson(serverMessage, ConnectedToServer.class);
//                    // returnMessage = connectedToServer.getResponse();
//                    break;
//                case returnGameList:
//                    ReturnGameList returnGameList = gson.fromJson(serverMessage, ReturnGameList.class);
//                    // returnMessage = returnGameList.getResponse();
//                case connectedToGame:
//                    ConnectedToGame connectedToGame = gson.fromJson(serverMessage, ConnectedToGame.class);
//                    // returnMessage = connectedToGame.getResponse();
//                    break;
//                case registeredForTournament:
//                    RegisteredForTournament registeredForTournament = gson.fromJson(serverMessage, RegisteredForTournament.class);
//                    // returnMessage = registeredForTournament.getResponse();
//                    break;
//                case returnTournamentInfo:
//                    ReturnTournamentInfo returnTournamentInfo = gson.fromJson(serverMessage, ReturnTournamentInfo.class);
//                    // returnMessage = TournamentInfo.getResponse();
//                    break;
//                case error:
//                    Error error = gson.fromJson(serverMessage, Error.class);
//                    // returnMessage = Error.getResponse();
//                    break;
//                case returnTechData:
//                    ReturnTechData returnTechData = gson.fromJson(serverMessage, ReturnTechData.class);
//                    // returnMessage = ReturnTechData.getResponse();
//                case boardState:
//                    BoardState boardState = gson.fromJson(serverMessage, BoardState.class);
//                case drawCards:
//                    DrawCards drawCards= gson.fromJson(serverMessage, DrawCards.class);
//                case moveValid:
//                    MoveValid moveValid = gson.fromJson(serverMessage, Move.class);
//                case freeze:
//                    Freeze freeze = gson.fromJson(serverMessage, Freeze.class);
//                case unfreeze:
//                    Unfreeze unfreeze = gson.fromJson(serverMessage, Unfreeze.class);
//                case cancel:
//                    Cancel cancel = gson.fromJson(serverMessage, Cancel.class);
//                case kick:
//                    Kick kick = gson.fromJson(serverMessage, Kick.class);
//                case joinObs:
//                    JoinObs joinObs = gson.fromJson(serverMessage, JoinObs.class);
//                case liveTimer:
//                    LiveTimer liveTimer = gson.fromJson(serverMessage, LiveTimer.class);
//                case turnTimer:
//                    TurnTimer turnTimer = gson.fromJson(serverMessage, liveTimer.class);
//
//                default:
//                    // logger.info("type unknown");
//            }
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//
//        // Return a response message
//        if (returnMessage == null) returnMessage = "response not found, received: " + serverMessage;
//        return returnMessage;
//
//    }
//
//    public static void setClientId(int clientId) {
//        ClientId = clientId;
//    }
//}
