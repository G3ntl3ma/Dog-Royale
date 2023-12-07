package com.nexusvision.server.model.messages.menu;

import lombok.Data;

/**
 * Server sends confirmation if connection was successful
 *
 * @author kellerb
 */
@Data
public class ConnectedToGame extends AbstractMenuMessage {
    private boolean success;

}