package com.example.myapplication.messages.menu;

import lombok.Data;

/**
 * Beitritt des Spiels als Beobachter
 *
 * @author kellerb
 */
@Data
public class JoinGameAsObserver extends com.example.myapplication.messages.menu.AbstractMenuMessage {
    private int gameId;
    private int clientId;
}