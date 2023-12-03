package com.nexusvision.server.model.messages.menu;


import lombok.Data;

/**
 * Anfrage nach gestarteten, laufenden und beendeten Spielen
 *
 * @author kellerb
 */
@Data
public class RequestGameList extends AbstractMenuMessage {
    private Integer clientID;
    private Integer gameCountStarting;
    private Integer gameCountInProgress;
    private Integer gameCountFinished;
}
