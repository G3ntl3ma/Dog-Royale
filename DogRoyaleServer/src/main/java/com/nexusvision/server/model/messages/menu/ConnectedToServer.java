package com.nexusvision.server.model.messages.menu;


import com.nexusvision.server.model.messages.AbstractMessage;
import lombok.Data;

/**
 * Server confirms successful registration
 *
 * @author kellerb
 */
@Data
public class ConnectedToServer extends AbstractMessage {
    private int clientId;
}
