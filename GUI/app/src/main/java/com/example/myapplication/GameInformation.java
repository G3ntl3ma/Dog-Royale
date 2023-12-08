package com.example.myapplication;

import com.example.myapplication.GameInformationClasses.Color;
import com.example.myapplication.GameInformationClasses.DrawCardFields;
import com.example.myapplication.GameInformationClasses.Observer;
import com.example.myapplication.GameInformationClasses.PlayerOrder;
import com.example.myapplication.GameInformationClasses.StartFields;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class GameInformation {


    private enum Colors{
        farbe1,
        farbe2,
        farbe3,
        farbe4,
        farbe5,
        farbe6
    }

    private enum OrderType{
        fixed,
        random
    }

    public static Integer playerCount;
    public static Integer fieldsize;
    public static Integer figuresPerPlayer;
    public static List<Color> colors;
    public static DrawCardFields drawCardFields;
    public static StartFields startFields;
    public static Integer initialCardsPerPlayer;
    public static PlayerOrder playerOrder;
    public static List<Observer> observer;
    public static Integer thinkTimePerMove;
    public static Integer visualizationTimePerMove;
    public static Integer consequencesForInvalidMove;
    public static Integer maximumGameDuration;
    public static Integer maximumTotalMoves;



    public GameInformation(Integer playerCount, Integer fieldsize, Integer figuresPerPlayer, List<Color> colors, DrawCardFields drawCardFields, StartFields startFields, Integer initialCardsPerPlayer, PlayerOrder playerOrder, List<Observer> observer, Integer thinkTimePerMove, Integer visualizationTimePerMove, Integer consequencesForInvalidMove, Integer maximumGameDuration, Integer maximumTotalMoves) {
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

    public static void setPlayerCount(Integer playerCount) {
        GameInformation.playerCount = playerCount;
    }

    public static void setFieldsize(Integer fieldsize) {
        GameInformation.fieldsize = fieldsize;
    }

    public static void setFiguresPerPlayer(Integer figuresPerPlayer) {
        GameInformation.figuresPerPlayer = figuresPerPlayer;
    }

    public static void setColors(List<Color> colors) {
        GameInformation.colors = colors;
    }

    public static void setDrawCardFields(DrawCardFields drawCardFields) {
        GameInformation.drawCardFields = drawCardFields;
    }

    public static void setStartFields(StartFields startFields) {
        GameInformation.startFields = startFields;
    }

    public static void setInitialCardsPerPlayer(Integer initialCardsPerPlayer) {
        GameInformation.initialCardsPerPlayer = initialCardsPerPlayer;
    }

    public static void setPlayerOrder(PlayerOrder playerOrder) {
        GameInformation.playerOrder = playerOrder;
    }

    public static void setObserver(List<Observer> observer) {
        GameInformation.observer = observer;
    }

    public static void setThinkTimePerMove(Integer thinkTimePerMove) {
        GameInformation.thinkTimePerMove = thinkTimePerMove;
    }

    public static void setVisualizationTimePerMove(Integer visualizationTimePerMove) {
        GameInformation.visualizationTimePerMove = visualizationTimePerMove;
    }

    public static void setConsequencesForInvalidMove(Integer consequencesForInvalidMove) {
        GameInformation.consequencesForInvalidMove = consequencesForInvalidMove;
    }

    public static void setMaximumGameDuration(Integer maximumGameDuration) {
        GameInformation.maximumGameDuration = maximumGameDuration;
    }

    public static void setMaximumTotalMoves(Integer maximumTotalMoves) {
        GameInformation.maximumTotalMoves = maximumTotalMoves;
    }

    public static Integer getPlayerCount() {
        return playerCount;
    }

    public static Integer getFieldsize() {
        return fieldsize;
    }

    public static Integer getFiguresPerPlayer() {
        return figuresPerPlayer;
    }

    public static List<Color> getColors() {
        return colors;
    }

    public static DrawCardFields getDrawCardFields() {
        return drawCardFields;
    }

    public static StartFields getStartFields() {
        return startFields;
    }

    public static Integer getInitialCardsPerPlayer() {
        return initialCardsPerPlayer;
    }

    public static PlayerOrder getPlayerOrder() {
        return playerOrder;
    }

    public static List<Observer> getObserver() {
        return observer;
    }

    public static Integer getThinkTimePerMove() {
        return thinkTimePerMove;
    }

    public static Integer getVisualizationTimePerMove() {
        return visualizationTimePerMove;
    }


    public static Integer getConsequencesForInvalidMove() {
        return consequencesForInvalidMove;
    }

    public static Integer getMaximumGameDuration() {
        return maximumGameDuration;
    }

    public static Integer getMaximumTotalMoves() {
        return maximumTotalMoves;
    }

}

