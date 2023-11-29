package com.nexusvision.messages.menu;


import com.nexusvision.messages.game.AbstractGameMessage;
import lombok.Data;

/**
 * Anfrage nach gestarteten, laufenden und beendeten Spielen
 *
 * @author kellerb
 */
@Data
public class RequestGameList extends AbstractMenuMessage {
    private int clientID;
    private int gameCountStarting;
    private int gameCountInProgress;
    private int gameCountFinished;
}
