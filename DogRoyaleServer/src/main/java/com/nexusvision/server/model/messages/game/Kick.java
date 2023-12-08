package com.nexusvision.server.model.messages.game;

import com.nexusvision.server.model.messages.AbstractMessage;

/**
 * Kicks a player
 *
 * @author kellerb
 */
public class Kick extends AbstractMessage {
    private int clientId;
    private String reason;

}
