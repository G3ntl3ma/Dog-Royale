package com.nexusvision.messages.game;

import lombok.Data;

/**
 * Beitritt des Spiels als Beobachter
 *
 * @author kellerb
 */
@Data
public class JoinGameAsObserver extends AbstractGameMessage{
    private int gameId;
    private int clientId;
}
