package com.nexusvision.messages.game;


import lombok.Data;

/**
 * Beitritt des Spiels als Teilnehmer
 *
 * @author kellerb
 */
@Data
public class JoinGameAsParticipant extends AbstractGameMessage {
    private int gameId;
    private int clientId;
    private String playerName;
}
