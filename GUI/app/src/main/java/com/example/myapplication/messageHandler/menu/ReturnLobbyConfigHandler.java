package com.example.myapplication.messageHandler.menu;

import com.example.myapplication.GameInformationClasses.Color;
import com.example.myapplication.GameInformation;
import com.example.myapplication.GameboardViewModel;
import com.example.myapplication.MainActivity;
import com.example.myapplication.messages.menu.ReturnLobbyConfig;

import java.util.ArrayList;
import java.util.List;

public class ReturnLobbyConfigHandler{
    GameboardViewModel gameboardViewModel = MainActivity.getGameboardViewModel();

    private GameInformation gameInformation;



    public void handle(com.example.myapplication.messages.menu.ReturnLobbyConfig message) {




        gameInformation = new GameInformation(message.getPlayerCount(), message.getFieldsize(), message.getFiguresPerPlayer(),message.getColors(), message.getDrawCardFields(), message.getStartFields(), message.getInitialCardsPerPlayer(), message.getPlayerOrder(), message.getObserver(), message.getThinkTimePerMove(), message.getVisualizationTimePerMove(), message.getConsequencesForInvalidMove(), message.getMaximumGameDuration(), message.getMaximumTotalMoves());

        gameboardViewModel.setGameInformation(gameInformation);

    };
}
