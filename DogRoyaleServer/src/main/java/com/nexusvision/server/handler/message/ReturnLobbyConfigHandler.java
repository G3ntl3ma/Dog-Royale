package com.nexusvision.server.handler.message;

import com.nexusvision.messages.menu.ReturnLobbyConfig;
import com.nexusvision.messages.menu.TypeMenue;
import com.nexusvision.server.controller.ServerController;
import com.nexusvision.messages.menu.Error;

import java.util.List;
import java.util.ArrayList;

public class ReturnLobbyConfigHandler implements MessageHandler<ReturnLobbyConfig> {
    @Override
    public String handle(ReturnLobbyConfig message, int clientID) {

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
		//parse colors
		//parse drawcardfields
		//parse startfields
		//parse observer

	Error error = new Error();
	error.setType(TypeMenue.error.ordinal() + 100);
	error.setDataId(TypeMenue.returnLobbyConfig.ordinal() + 100);


	ArrayList<Integer> playerIDs = new ArrayList<>();
	ArrayList<Integer> observerIDs = new ArrayList<>();

	boolean errorFound = false;
	ArrayList<String> errors = new ArrayList<>();

	Integer playerCount = message.getPlayerCount();
	if(playerCount == null) {
	    errors.add("playerCount");
	    errorFound = true;
	}

	Integer fieldSize = message.getFieldsize();
	if(fieldSize == null) {
	    errors.add("fieldsize");
	    errorFound = true;
	}

	Integer figuresPerPlayer = message.getFiguresPerPlayer();
	if(figuresPerPlayer == null) {
	    errors.add("figuresPerPlayer");
	    errorFound = true;
	}

	Integer initialCardsPerPlayer = message.getInitialCardsPerPlayer();
	if(initialCardsPerPlayer == null) {
	    errors.add("initialCardsPerPlayer");
	    errorFound = true;
	}

	Integer thinkTimePerMove = message.getThinkTimePerMove();
	if(thinkTimePerMove == null) {
	    errors.add("thinkTimePerMove");
	    errorFound = true;
	}

	Integer visualizationTimePerMove = message.getVisualizationTimePerMove();
	if(visualizationTimePerMove == null) {
	    errors.add("visualizationTimePerMove");
	    errorFound = true;
	}

	Integer consequencesForInvalidMove = message.getConsequencesForInvalidMove();
	if(consequencesForInvalidMove == null) {
	    errors.add("consequencesForInvalidMove");
	    errorFound = true;
	}

	Integer maximumGameDuration = message.getMaximumGameDuration();
	if(maximumGameDuration == null) {
	    errors.add("maximumGameDuration");
	    errorFound = true;
	}

	Integer maximumTotalMoves = message.getMaximumTotalMoves();
	if(maximumTotalMoves == null) {
	    errors.add("maximumTotalMoves");
	    errorFound = true;
	}

	if(errorFound) {
		error.setMessage("configuring game failed: " + String.join(", ", errors) + " not specified");
	    return gson.toJson(error);
	}



	ReturnLobbyConfig.PlayerOrder playerOrder = message.getPlayerOrder();
	List<ReturnLobbyConfig.PlayerOrder.Order> order = playerOrder.order;
	for (int i = 0; i < order.size(); i++) {
	    int clID = order.get(i).clientId;
	    //if specified clientIDs dont exist
	    if(!ServerController.clientIdRegistered(clID)) {
			error.setMessage("configuring game failed: clientId not registered");
			return gson.toJson(error);
	    }
	    if (ServerController.getObserver(clID)) {
			error.setMessage("configuring game failed: specified an observer as player");
			return gson.toJson(error);
	    }
	    else {
			playerIDs.add(clID);
	    }
	}
	List<ReturnLobbyConfig.Observer> observers = message.getObserver();
	for(int i = 0; i < observers.size(); i++) {
		int clID = observers.get(i).getClientId();
		if(!ServerController.clientIdRegistered(clID)) {
			error.setMessage("configuring game failed: clientId not registered");
			return gson.toJson(error);
		}
		if (ServerController.getObserver(clID)) {
			observerIDs.add(clID);
		}
		else {
			error.setMessage("configuring game failed: specified a non observer as observer");
			return gson.toJson(error);
		}
	}


	ServerController.createNewLobby(playerIDs, observerIDs);

	return "sea";
    }
}
