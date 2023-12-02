package com.nexusvision.server.model.messages.menu;


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
