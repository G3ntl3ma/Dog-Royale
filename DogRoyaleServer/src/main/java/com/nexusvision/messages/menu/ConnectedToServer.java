package com.nexusvision.messages.menu;


import com.nexusvision.messages.game.AbstractGameMessage;
import lombok.Data;

/**
 * Server bestätigt erfolgreiche Anmeldung
 *
 * @author kellerb
 */
@Data
public class ConnectedToServer extends AbstractMenuMessage {
    private int clientId;
}
