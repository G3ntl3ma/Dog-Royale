package com.example.myapplication.messages.menu;


import com.example.myapplication.messages.game.AbstractGameMessage;
import lombok.Data;

/**
 * Server bestätigt erfolgreiche Anmeldung
 *
 * @author kellerb
 */
@Data
public class ConnectedToServer {
    private int clientId;
}
