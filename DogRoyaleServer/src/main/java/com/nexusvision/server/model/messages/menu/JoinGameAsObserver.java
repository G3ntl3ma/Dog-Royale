package com.nexusvision.server.model.messages.menu;

import lombok.Data;

/**
 * Beitritt des Spiels als Beobachter
 *
 * @author kellerb
 */
@Data
public class JoinGameAsObserver extends AbstractMenuMessage {
    private Integer gameId;
    private Integer clientId;
}
