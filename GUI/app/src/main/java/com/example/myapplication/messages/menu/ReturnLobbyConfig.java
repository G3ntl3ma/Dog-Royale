package com.example.myapplication.messages.menu;

import lombok.Data;

import java.util.List;

/**
 * Konfiguration des Spiels
 *
 * @author Mattes
 */
@Data
public class ReturnLobbyConfig{
    private enum Penalty {
        excludeFromRound,
        kickFromGame
    }

    private enum Colors{
        farbe1,
        farbe2,
        farbe3,
        farbe4,
        farbe5,
        farbe6
    }

    private Integer playerCount;
    private Integer fieldsize;
    private Integer figuresPerPlayer;
    private List<com.example.myapplication.GameInformationClasses.Color> colors;
    private com.example.myapplication.GameInformationClasses.DrawCardFields drawCardFields;
    private com.example.myapplication.GameInformationClasses.StartFields startFields;
    private Integer initialCardsPerPlayer;
    private com.example.myapplication.GameInformationClasses.PlayerOrder playerOrder;
    private List<com.example.myapplication.GameInformationClasses.Observer> observer;
    private Integer thinkTimePerMove;
    private Integer visualizationTimePerMove;
    private Integer consequencesForInvalidMove;
    private Integer maximumGameDuration;
    private Integer maximumTotalMoves;


    public ReturnLobbyConfig(Integer playerCount, Integer fieldsize, Integer figuresPerPlayer, List<com.example.myapplication.GameInformationClasses.Color> colors, com.example.myapplication.GameInformationClasses.DrawCardFields drawCardFields, com.example.myapplication.GameInformationClasses.StartFields startFields, Integer initialCardsPerPlayer, com.example.myapplication.GameInformationClasses.PlayerOrder playerOrder, List<com.example.myapplication.GameInformationClasses.Observer> observer, Integer thinkTimePerMove, Integer visualizationTimePerMove, Integer consequencesForInvalidMove, Integer maximumGameDuration, Integer maximumTotalMoves) {
        this.playerCount = playerCount;
        this.fieldsize = fieldsize;
        this.figuresPerPlayer = figuresPerPlayer;
        this.colors = colors;
        this.drawCardFields = drawCardFields;
        this.startFields = startFields;
        this.initialCardsPerPlayer = initialCardsPerPlayer;
        this.playerOrder = playerOrder;
        this.observer = observer;
        this.thinkTimePerMove = thinkTimePerMove;
        this.visualizationTimePerMove = visualizationTimePerMove;
        this.consequencesForInvalidMove = consequencesForInvalidMove;
        this.maximumGameDuration = maximumGameDuration;
        this.maximumTotalMoves = maximumTotalMoves;
    }

    public Integer getPlayerCount() {
        return playerCount;
    }

    public Integer getFieldsize() {
        return fieldsize;
    }

    public Integer getFiguresPerPlayer() {
        return figuresPerPlayer;
    }

    public List<com.example.myapplication.GameInformationClasses.Color> getColors() {
        return colors;
    }

    public com.example.myapplication.GameInformationClasses.DrawCardFields getDrawCardFields() {
        return drawCardFields;
    }

    public com.example.myapplication.GameInformationClasses.StartFields getStartFields() {
        return startFields;
    }

    public Integer getInitialCardsPerPlayer() {
        return initialCardsPerPlayer;
    }

    public com.example.myapplication.GameInformationClasses.PlayerOrder getPlayerOrder() {
        return playerOrder;
    }

    public List<com.example.myapplication.GameInformationClasses.Observer> getObserver() {
        return observer;
    }

    public Integer getThinkTimePerMove() {
        return thinkTimePerMove;
    }

    public Integer getVisualizationTimePerMove() {
        return visualizationTimePerMove;
    }

    public Integer getConsequencesForInvalidMove() {
        return consequencesForInvalidMove;
    }

    public Integer getMaximumGameDuration() {
        return maximumGameDuration;
    }

    public Integer getMaximumTotalMoves() {
        return maximumTotalMoves;
    }


}


