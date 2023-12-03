package com.example.myapplication;
import com.example.myapplication.messages.game.AbstractGameMessage;
import com.example.myapplication.messages.game.BoardState;
import com.example.myapplication.messages.game.Cancel;
import com.example.myapplication.messages.game.DrawCards;
import com.example.myapplication.messages.game.Freeze;
import com.example.myapplication.messages.game.Kick;
import com.example.myapplication.messages.game.Move;
import com.example.myapplication.messages.game.MoveValid;
import com.example.myapplication.messages.game.Unfreeze;
import com.example.myapplication.messages.menu.AbstractMenuMessage;
import com.example.myapplication.messages.menu.ConnectedToGame;
import com.example.myapplication.messages.menu.ConnectedToServer;
import com.example.myapplication.messages.menu.Error;
import com.example.myapplication.messages.menu.RegisteredForTournament;
import com.example.myapplication.messages.menu.ReturnGameList;
import com.example.myapplication.messages.menu.ReturnTechData;
import com.example.myapplication.messages.menu.ReturnTournamentInfo;
import com.example.myapplication.messages.sync.JoinObs;
import com.example.myapplication.messages.sync.LiveTimer;
import com.example.myapplication.messages.sync.TurnTimer;
import com.example.myapplication.messages.sync.AbstractSyncMessage;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.net.Socket;

import lombok.Data;

/**
 * Soll ServerHandler ersetzen. Kann Menu und Game Messages einordnen.
 *
 * @author Mattes
 */
@Data
public class MasterHandler {

    private final Socket serverSocket;
    public MasterHandler(Socket serverSocket) {
        this.serverSocket = serverSocket;
    }

    private String determineType(String serverMessage) {
        String returnMessage = null;
        try {
            Gson gson = new Gson();
            JsonElement jsonElement = JsonParser.parseString(serverMessage);
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            Character typeIndicator = jsonObject.get("type").getAsString().charAt(0);
            int typeInx = jsonObject.get("type").getAsInt();
            switch (typeIndicator) {
                case 1:
                    returnMessage = processMenuMessage(serverMessage, typeInx); // Nachricht ist vom Kontext Menü
                case 2:
                    returnMessage = processGameMessage(serverMessage, typeInx); // Nachricht ist vom Kontext Spiel
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // Return a response message
        if (returnMessage == null) {
            returnMessage = "response not found, received: " + serverMessage;
        }
        return returnMessage;

    }

    private String processMenuMessage(String serverMessage, int typeInx) {
        typeInx = typeInx - 100;
        AbstractMenuMessage.TypeMenue typeM = AbstractMenuMessage.TypeMenue.values()[typeInx];
        Gson gson = new Gson();
        switch (typeM) {
            case connectedToServer:
                ConnectedToServer connectedToServer = gson.fromJson(serverMessage, ConnectedToServer.class);
                // returnMessage = connectedToServerHandler.getResponse();
                break;
            case returnGameList:
                ReturnGameList returnGameList = gson.fromJson(serverMessage, ReturnGameList.class);
                // returnMessage = returnGameListHandler.getResponse();
            case connectedToGame:
                ConnectedToGame connectedToGame = gson.fromJson(serverMessage, ConnectedToGame.class);
                // returnMessage = connectedToGameHandler.getResponse();
                break;
            case registeredForTournament:
                RegisteredForTournament registeredForTournament = gson.fromJson(serverMessage, RegisteredForTournament.class);
                // returnMessage = registeredForTournamentHandler.getResponse();
                break;
            case returnTournamentInfo:
                ReturnTournamentInfo returnTournamentInfo = gson.fromJson(serverMessage, ReturnTournamentInfo.class);
                // returnMessage = TournamentInfoHandler.getResponse();
                break;
            case error:
                Error error = gson.fromJson(serverMessage, Error.class);
                // returnMessage = ErrorHandler.getResponse();
                break;
            case returnTechData:
                ReturnTechData returnTechData = gson.fromJson(serverMessage, ReturnTechData.class);
                // returnMessage = ReturnTechDataHandler.getResponse();

            default:
                // unbekannte Nachricht
        }
    }
    private String processGameMessage(String serverMessage, int typeInx) {
        typeInx = typeInx - 200;
        AbstractGameMessage.TypeGame typeG = AbstractGameMessage.TypeGame.values()[typeInx];
        Gson gson = new Gson();
        switch (typeG) {
            case boardState:
                BoardState boardState = gson.fromJson(serverMessage, BoardState.class);
                returnMessage = ReturnBoardStateHandler.getResponse();
            case drawCards:
                DrawCards drawCards = gson.fromJson(serverMessage, DrawCards.class);
                returnMessage = DrawCardsHandler.getResponse();
            case moveValid:
                MoveValid moveValid = gson.fromJson(serverMessage, MoveValid.class);
                returnMessage = MoveValidHandler.getResponse();
            case freeze:
                Freeze freeze = gson.fromJson(serverMessage, Freeze.class);
                returnMessage = Freeze.Handler.getResponse();
            case unfreeze:
                Unfreeze unfreeze = gson.fromJson(serverMessage, Unfreeze.class);
                returnMessage = UnfreezeHandler.getResponse();
            case cancel:
                Cancel cancel = gson.fromJson(serverMessage, Cancel.class);
                returnMessage = CancelHandler.getResponse();
            case kick:
                Kick kick = gson.fromJson(serverMessage, Kick.class);
                returnMessage = KickHandler.getResponse();
            case joinObs:
                JoinObs joinObs = gson.fromJson(serverMessage, JoinObs.class);
                returnMessage = JoinObsHandler.getResponse();
            case liveTimer:
                LiveTimer liveTimer = gson.fromJson(serverMessage, LiveTimer.class);
                returnMessage = LiveTimerHandler.getResponse();
            case turnTimer:
                TurnTimer turnTimer = gson.fromJson(serverMessage, TurnTimer.class); // Sync Klassen auch hier aufrufen?

            default:
                // unbekannte Nachricht
        }
    }
}
