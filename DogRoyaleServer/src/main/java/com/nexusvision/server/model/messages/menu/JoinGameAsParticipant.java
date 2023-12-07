package com.nexusvision.server.model.messages.menu;


import lombok.Data;

/**
 * Joins the game as participant
 *
 * @author kellerb
 */
@Data
public class JoinGameAsParticipant extends AbstractMenuMessage {
    private Integer gameId;
    private Integer clientId;
    private String playerName;
}
