package com.nexusvision.server.model.messages.menu;


import lombok.Data;

/**
 * Beitritt des Spiels als Teilnehmer
 *
 * @author kellerb
 */
@Data
public class JoinGameAsParticipant extends AbstractMenuMessage {
    private int gameId;
    private int clientId;
    private String playerName;
}
