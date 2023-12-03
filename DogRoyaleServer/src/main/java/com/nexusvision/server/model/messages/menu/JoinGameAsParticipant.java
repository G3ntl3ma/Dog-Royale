package com.nexusvision.server.model.messages.menu;


import lombok.Builder;
import lombok.Data;

/**
 * Beitritt des Spiels als Teilnehmer
 *
 * @author kellerb
 */
@Data
@Builder
public class JoinGameAsParticipant extends AbstractMenuMessage {
    private Integer gameId;
    private Integer clientId;
    private String playerName;
}
