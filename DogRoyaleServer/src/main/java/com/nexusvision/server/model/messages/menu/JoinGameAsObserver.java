package com.nexusvision.server.model.messages.menu;

import lombok.Data;

/**
 * Joins game as observer
 *
 * @author kellerb
 */
@Data
public class JoinGameAsObserver extends AbstractMenuMessage {
    private Integer gameId;
    private Integer clientId;
}
