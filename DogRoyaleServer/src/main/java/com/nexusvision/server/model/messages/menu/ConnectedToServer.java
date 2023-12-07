package com.nexusvision.server.model.messages.menu;


import lombok.Data;

/**
 * Server confirms successful registration
 *
 * @author kellerb
 */
@Data
public class ConnectedToServer extends AbstractMenuMessage {
    private int clientId;
}
