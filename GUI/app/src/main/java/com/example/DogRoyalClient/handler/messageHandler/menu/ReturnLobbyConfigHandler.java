package com.example.DogRoyalClient.handler.messageHandler.menu;

import com.example.DogRoyalClient.GameInformation;
import com.example.DogRoyalClient.handler.Handler;
import com.example.DogRoyalClient.handler.HandlingException;
import com.example.DogRoyalClient.messages.menu.ReturnLobbyConfig;

public class ReturnLobbyConfigHandler extends Handler implements MenuMessageHandler<ReturnLobbyConfig>{
    @Override
    public String handle(ReturnLobbyConfig message) throws HandlingException {
        GameInformation.setPlayerCount(message.getPlayerCount());
        GameInformation.setFieldsize(message.getFieldsize());
        GameInformation.setFiguresPerPlayer(message.getFiguresPerPlayer());
        GameInformation.setColors(message.getColors());
        GameInformation.setDrawCardFields(message.getDrawCardFields());
        GameInformation.setStartFields(message.getStartFields());
        GameInformation.setInitialCardsPerPlayer(message.getInitialCardsPerPlayer());
        GameInformation.setPlayerOrder(message.getPlayerOrder());
        GameInformation.setObserver(message.getObserver());
        GameInformation.setThinkTimePerMove(message.getThinkTimePerMove());
        GameInformation.setVisualizationTimePerMove(message.getVisualizationTimePerMove());
        GameInformation.setConsequencesForInvalidMove(message.getConsequencesForInvalidMove());
        GameInformation.setMaximumGameDuration(message.getMaximumGameDuration());
        GameInformation.setMaximumTotalMoves(message.getMaximumTotalMoves());
        return null;
    }
}
