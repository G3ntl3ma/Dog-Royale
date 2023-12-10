package com.example.myapplication.messages.menu;


import lombok.Data;

/**
 * Beitritt des Spiels als Teilnehmer
 *
 * @author kellerb
 */
@Data
public class JoinGameAsParticipant {
    private int gameId;
    private int clientId;
    private String playerName;
}
