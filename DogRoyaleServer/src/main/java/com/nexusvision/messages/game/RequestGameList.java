package com.nexusvision.messages.game;


import lombok.Data;

/**
 * Anfrage nach gestarteten, laufenden und beendeten Spielen
 *
 * @author kellerb
 */
@Data
public class RequestGameList extends AbstractGameMessage {
    private int clientID;
    private int gameCountStarting;
    private int gameCountInProgress;
    private int gameCountFinished;
}
