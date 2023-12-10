package com.nexusvision.server.model.messages.game;

import com.nexusvision.server.model.messages.AbstractMessage;
import lombok.Data;

/**
 * Kicks a player
 *
 * @author kellerb
 */
@Data
public class Kick extends AbstractMessage {
    private int clientId;
    private String reason;
}
