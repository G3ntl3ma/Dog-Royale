package com.example.myapplication.messages.menu;

import lombok.Data;

/**
 * Konfiguration des Spiels
 *
 * @author kellerb
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

    private enum OrderType{
        fixed,
        random
    }

    private int playerCount;
    private int figuresPerPlayer;

    private Colors color;

    private Penalty penalty;
}
