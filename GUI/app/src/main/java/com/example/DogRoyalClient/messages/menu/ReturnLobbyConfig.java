package com.example.DogRoyalClient.messages.menu;

import lombok.Data;

import java.util.List;

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
    private List<com.example.DogRoyalClient.GameInformationClasses.Color> colors;
    private com.example.DogRoyalClient.GameInformationClasses.DrawCardFields drawCardFields;
    private com.example.DogRoyalClient.GameInformationClasses.StartFields startFields;
    private Integer initialCardsPerPlayer;
    private com.example.DogRoyalClient.GameInformationClasses.PlayerOrder playerOrder;
    private List<com.example.DogRoyalClient.GameInformationClasses.Observer> observer;
    private Integer thinkTimePerMove;
    private Integer visualizationTimePerMove;
    private Integer consequencesForInvalidMove;
    private Integer maximumGameDuration;
    private Integer maximumTotalMoves;



}


