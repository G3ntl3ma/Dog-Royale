package com.nexusvision.messages.menu;

import com.nexusvision.messages.game.AbstractGameMessage;
import lombok.Data;

/**
 * Server sendet Bestätigung bei erfolgreicher Verbindung
 *
 * @author kellerb
 */
@Data
public class ConnectedToGame extends AbstractMenuMessage {
    private boolean success;

}