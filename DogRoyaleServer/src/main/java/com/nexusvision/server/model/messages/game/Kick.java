package com.nexusvision.server.model.messages.game;

/**
 * Kicks a player
 *
 * @author kellerb
 */
public class Kick extends AbstractGameMessage{
    private int clientId;
    private String reason;

}
