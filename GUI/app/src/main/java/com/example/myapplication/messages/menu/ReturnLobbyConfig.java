package com.example.myapplication.messages.menu;

import com.example.myapplication.GameInformationClasses.Color;
import com.example.myapplication.GameInformationClasses.DrawCardFields;
import com.example.myapplication.GameInformationClasses.OrderType;
import com.example.myapplication.GameInformationClasses.PlayerOrder;
import com.example.myapplication.GameInformationClasses.StartFields;
import com.example.myapplication.GameInformationClasses.Observer;

import java.util.List;

import lombok.Data;

/**
 * Konfiguration des Spiels
 *
 * @author Mattes
 */
@Data
public class ReturnLobbyConfig extends AbstractMenuMessage{
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
    private List<Color> colors;
    private DrawCardFields drawCardFields;
    private StartFields startFields;
    private Integer initialCardsPerPlayer;
    private PlayerOrder playerOrder;
    private List<Observer> observer;
    private Integer thinkTimePerMove;
    private Integer visualizationTimePerMove;
    private Integer consequencesForInvalidMove;
    private Integer maximumGameDuration;
    private Integer maximumTotalMoves;



}


