package com.nexusvision.server.model.messages.menu;


import lombok.Builder;
import lombok.Data;

/**
 * Anfrage nach gestarteten, laufenden und beendeten Spielen
 *
 * @author kellerb
 */
@Data
@Builder
public class RequestGameList extends AbstractMenuMessage {
    private int clientID;
    private int gameCountStarting;
    private int gameCountInProgress;
    private int gameCountFinished;
}
