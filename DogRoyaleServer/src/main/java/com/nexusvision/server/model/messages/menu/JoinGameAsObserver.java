package com.nexusvision.server.model.messages.menu;

import com.nexusvision.server.model.messages.AbstractMessage;
import lombok.Data;

/**
 * Joins game as observer
 *
 * @author kellerb
 */
@Data
public class JoinGameAsObserver extends AbstractMessage {
    private int gameId;
    private int clientId;
}
