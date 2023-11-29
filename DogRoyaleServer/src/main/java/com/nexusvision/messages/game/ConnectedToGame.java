package com.nexusvision.messages.game;

import lombok.Data;

/**
 * Server sendet Bestätigung bei erfolgreicher Verbindung
 *
 * @author kellerb
 */
@Data
public class ConnectedToGame extends AbstractGameMessage{
    private boolean success;

}
