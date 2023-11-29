package com.example.myapplication.messages.menu;

import com.example.myapplication.messages.game.AbstractGameMessage;
import lombok.Data;

/**
 * Server sendet Bestätigung bei erfolgreicher Verbindung
 *
 * @author kellerb
 */
@Data
public class ConnectedToGame extends AbstractMenuMessage {
    private boolean success;

    // TODO: getResponse()-Methode schreiben
}
