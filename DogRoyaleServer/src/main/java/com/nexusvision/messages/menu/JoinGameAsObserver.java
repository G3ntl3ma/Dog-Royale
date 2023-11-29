package com.nexusvision.messages.menu;

import com.nexusvision.messages.game.AbstractGameMessage;
import lombok.Data;

/**
 * Beitritt des Spiels als Beobachter
 *
 * @author kellerb
 */
@Data
public class JoinGameAsObserver extends AbstractMenuMessage {
    private int gameId;
    private int clientId;
}
