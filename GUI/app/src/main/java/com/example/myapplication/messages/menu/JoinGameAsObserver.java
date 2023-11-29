package com.example.myapplication.messages.menu;

import com.example.myapplication.messages.game.AbstractGameMessage;
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
