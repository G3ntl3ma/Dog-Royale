package com.nexusvision.server.model.messages.menu;


import lombok.Data;

/**
 * Requesting starting, in progress and finished games
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
