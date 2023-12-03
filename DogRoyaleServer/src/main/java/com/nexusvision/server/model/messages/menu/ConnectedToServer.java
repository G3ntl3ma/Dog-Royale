package com.nexusvision.server.model.messages.menu;


import lombok.Builder;
import lombok.Data;

/**
 * Server bestätigt erfolgreiche Anmeldung
 *
 * @author kellerb
 */
@Data
@Builder
public class ConnectedToServer extends AbstractMenuMessage {
    private int clientId;
}
