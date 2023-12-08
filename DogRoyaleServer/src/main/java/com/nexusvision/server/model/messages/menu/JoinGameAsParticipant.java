package com.nexusvision.server.model.messages.menu;


import com.nexusvision.server.model.messages.AbstractMessage;
import lombok.Data;

/**
 * Joins the game as participant
 *
 * @author kellerb
 */
@Data
public class JoinGameAsParticipant extends AbstractMessage {
    private Integer gameId;
    private Integer clientId;
    private String playerName;
}
