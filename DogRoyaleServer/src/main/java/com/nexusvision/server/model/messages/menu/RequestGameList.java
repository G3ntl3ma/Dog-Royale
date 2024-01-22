package com.nexusvision.server.model.messages.menu;


import com.nexusvision.server.model.messages.AbstractMessage;
import lombok.Data;

/**
 * Requesting starting, in progress and finished games
 *
 * @author kellerb
 */
@Data
public class RequestGameList extends AbstractMessage {
    private int clientId;
    private int gamesUpcomingCount;
    private int gamesRunningCount;
    private int gamesFinishedCount;
}
