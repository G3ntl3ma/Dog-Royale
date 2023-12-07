package com.example.myapplication.messages.menu;


import lombok.Data;

/**
 * Beitritt des Spiels als Teilnehmer
 *
 * @author kellerb
 */
@Data
public class JoinGameAsParticipant extends com.example.myapplication.messages.menu.AbstractMenuMessage {
    private int gameId;
    private int clientId;
    private String playerName;
}
