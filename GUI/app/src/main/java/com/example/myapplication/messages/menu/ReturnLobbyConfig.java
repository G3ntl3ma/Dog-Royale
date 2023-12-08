package GUI.app.src.main.java.com.example.myapplication.messages.menu;

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



}


