package com.nexusvision.server.model.messages.menu;

import lombok.Builder;
import lombok.Data;

/**
 * Server sendet Bestätigung bei erfolgreicher Verbindung
 *
 * @author kellerb
 */
@Data
@Builder
public class ConnectedToGame extends AbstractMenuMessage {
    private boolean success;

}