package com.nexusvision.server.model.messages.menu;


import com.nexusvision.server.model.messages.AbstractMessage;
import lombok.Data;

/**
 * Joins the game as participant
 *
 * @author felixwr
 */
@Data
public class JoinGameAsPlayer extends AbstractMessage {
    private int gameId;
    private int clientId;
    private String playerName;
}
