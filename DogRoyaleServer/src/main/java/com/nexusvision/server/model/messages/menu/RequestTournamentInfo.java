package com.nexusvision.server.model.messages.menu;

import com.nexusvision.server.model.messages.AbstractMessage;
import lombok.Data;

/**
 * Requesting up-to-date tournament information
 *
 * @author kellerb
 */
@Data
public class RequestTournamentInfo extends AbstractMessage {
    private int clientId;
    private int tournamentId;
}
