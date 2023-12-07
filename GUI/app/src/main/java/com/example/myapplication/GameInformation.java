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

}

