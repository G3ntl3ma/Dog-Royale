package com.nexusvision.messages.game;


import lombok.Data;

/**
 * Server bestätigt erfolgreiche Anmeldung
 *
 * @author kellerb
 */
@Data
public class ConnectedToServer extends AbstractGameMessage {
    private int clientId;
}
