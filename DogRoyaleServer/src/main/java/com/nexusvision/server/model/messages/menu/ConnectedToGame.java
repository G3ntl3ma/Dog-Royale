package com.nexusvision.server.model.messages.menu;

import com.nexusvision.server.model.messages.AbstractMessage;
import lombok.Data;

/**
 * Server sends confirmation if connection was successful
 *
 * @author kellerb
 */
@Data
public class ConnectedToGame extends AbstractMessage {
    private boolean success;
}