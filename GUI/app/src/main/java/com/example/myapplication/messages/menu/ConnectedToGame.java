package com.example.myapplication.messages.menu;

import com.example.myapplication.messages.game.AbstractGameMessage;
import lombok.Data;

/**
 * Server sendet Bestätigung bei erfolgreicher Verbindung
 *
 * @author kellerb
 */
@Data
public class ConnectedToGame {
    private boolean success;

    public boolean isSuccess() {
        return success;
    }
}