package com.nexusvision.server.model.messages.menu;

import com.nexusvision.server.model.messages.AbstractMessage;
import lombok.Data;

/**
 * Requests tournaments
 *
 * @author felixwr
 */
@Data
public class RequestTournamentList extends AbstractMessage {
    private int clientId;
    private int tournamentsUpcomingCount;
    private int tournamentsRunningCount;
    private int tournamentsFinishedCount;
}
