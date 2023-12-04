package com.nexusvision.server.handler.message.menu;

import com.nexusvision.server.handler.Handler;
import com.nexusvision.server.model.messages.menu.ReturnLobbyConfig;
import com.nexusvision.server.model.messages.menu.TypeMenue;
import com.nexusvision.server.controller.ServerController;
import com.nexusvision.server.model.messages.menu.Error;

import java.util.List;
import java.util.ArrayList;

public class ReturnLobbyConfigHandler extends Handler implements MenuMessageHandler<ReturnLobbyConfig> {

    @Override
    public String handle(ReturnLobbyConfig message, int clientID) {

        ServerController serverController = ServerController.getInstance();

        //TODO error handling
        //if client is not in another game
        //if playercount bigger 6 or smaller 2
        //if fieldsize too large or small
        //if figures per player too large or small
        //if drawcardfields count bigger than fieldsize
        //if startfield count not equal maxplayers
        //if initial cards per player to large or small
        //if thinktime per move ok number
        //if visualizationtimepermove ok
        //if consequences for invalid move ok
        //if maximumgameduration ok
        //if maximumtotalmoves ok
        //if colorlist and orderlist are permutations of each other

        // TODO: Use errorHandling
        Error error = new Error();
        error.setType(TypeMenue.error.ordinal() + 100);
        error.setDataId(TypeMenue.returnLobbyConfig.ordinal() + 100);

        ArrayList<Integer> playerOrderList = new ArrayList<>();
        ArrayList<Integer> playerColorList = new ArrayList<>();
        ArrayList<Integer> observerIDs = new ArrayList<>();

        boolean errorFound = false;
        ArrayList<String> errors = new ArrayList<>();

        Integer playerCount = message.getPlayerCount();
        if (playerCount == null) {
            errors.add("playerCount");
            errorFound = true;
        }

        Integer fieldSize = message.getFieldsize();
        if (fieldSize == null) {
            errors.add("fieldsize");
            errorFound = true;
        }

        Integer figuresPerPlayer = message.getFiguresPerPlayer();
        if (figuresPerPlayer == null) {
            errors.add("figuresPerPlayer");
            errorFound = true;
        }

        Integer initialCardsPerPlayer = message.getInitialCardsPerPlayer();
        if (initialCardsPerPlayer == null) {
            errors.add("initialCardsPerPlayer");
            errorFound = true;
        }

        Integer thinkTimePerMove = message.getThinkTimePerMove();
        if (thinkTimePerMove == null) {
            errors.add("thinkTimePerMove");
            errorFound = true;
        }

        Integer visualizationTimePerMove = message.getVisualizationTimePerMove();
        if (visualizationTimePerMove == null) {
            errors.add("visualizationTimePerMove");
            errorFound = true;
        }

        Integer consequencesForInvalidMove = message.getConsequencesForInvalidMove();
        if (consequencesForInvalidMove == null) {
            errors.add("consequencesForInvalidMove");
            errorFound = true;
        }

        Integer maximumGameDuration = message.getMaximumGameDuration();
        if (maximumGameDuration == null) {
            errors.add("maximumGameDuration");
            errorFound = true;
        }

        Integer maximumTotalMoves = message.getMaximumTotalMoves();
        if (maximumTotalMoves == null) {
            errors.add("maximumTotalMoves");
            errorFound = true;
        }


        ReturnLobbyConfig.PlayerOrder playerOrder = message.getPlayerOrder();
        List<ReturnLobbyConfig.PlayerOrder.Order> order = playerOrder.getOrder();
        if (playerOrder == null) {
            errors.add("playerOrder");
            errorFound = true;
        }
        else {
            for (int i = 0; i < order.size(); i++) {
                int clientId = order.get(i).getClientId();
                //TODO check if null
                //if specified clientIDs dont exist
                if (!serverController.clientIdRegistered(clientId)) {
                    error.setMessage("configuring game failed: clientId not registered");
                    return gson.toJson(error);
                }
                // TODO: REWRITE
//                if (serverController.getObserver(clientId)) {
//                    error.setMessage("configuring game failed: specified an observer as player");
//                    return gson.toJson(error);
//                } else {
//                    playerOrderList.add(clientId);
//                }
            }
        }

        List<ReturnLobbyConfig.Observer> observers = message.getObserver();
        if (observers == null) {
            errors.add("observers");
            errorFound = true;
        }
        else {
            for (int i = 0; i < observers.size(); i++) {
                int clientId = observers.get(i).getClientId();
                if (!serverController.clientIdRegistered(clientId)) {
                    error.setMessage("configuring game failed: clientId not registered");
                    return gson.toJson(error);
                }
                // TODO: REWRITE
//                if (serverController.getObserver(clientId)) {
//                    observerIDs.add(clientId);
//                } else {
//                    error.setMessage("configuring game failed: specified a non observer as observer");
//                    return gson.toJson(error);
//                }
            }
        }

        List<ReturnLobbyConfig.Color> colors = message.getColors();
        if (colors == null) {
            errors.add("colors");
            errorFound = true;
        }
        else {
            for (int i = 0; i < colors.size(); i++) {
                int clID = colors.get(i).getClientId();
                if (!serverController.clientIdRegistered(clID)) {
                    error.setMessage("configuring game failed: clientId not registered");
                    return gson.toJson(error);
                }
                // TODO: REWRITE
//                if (serverController.getObserver(clID)) {
//                    observerIDs.add(clID);
//                } else {
//                    error.setMessage("configuring game failed: specified a non observer as observer");
//                    return gson.toJson(error);
//                }
            }
        }

        ReturnLobbyConfig.DrawCardFields drawCardFields = message.getDrawCardFields();
        if(drawCardFields == null) {
            errors.add("drawCardFields");
            errorFound = true;
        }
        else {
            if(drawCardFields.getPositions() == null) {
                errors.add("drawCardFields positions");
                errorFound = true;
            }
        }

        ReturnLobbyConfig.StartFields startFields = message.getStartFields();
        if(startFields == null) {
            errors.add("startFields");
            errorFound = true;
        }
        else {
            if(startFields.getPositions() == null) {
                errors.add("startFields positions");
                errorFound = true;
            }
        }

        if (errorFound) {
            error.setMessage("configuring game failed: " + String.join(", ", errors) + " not specified");
            return gson.toJson(error);
        }

        // TODO: REWRITE
//        int gameID = serverController.createNewLobby(playerOrderList, observerIDs, playerColorList);
//        serverController.setConfiguration(gameID, playerCount, fieldSize, figuresPerPlayer,  drawCardFields.getPositions(),
//                startFields.getPositions(), initialCardsPerPlayer, thinkTimePerMove,
//        consequencesForInvalidMove, maximumGameDuration, maximumTotalMoves);

        return "sea";
    }
}
