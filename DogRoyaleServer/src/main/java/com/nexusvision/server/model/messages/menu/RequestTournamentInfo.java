package com.nexusvision.server.model.messages.menu;

import lombok.Data;

/**
 * Requesting up-to-date tournament information
 *
 * @author kellerb
 */
@Data
public class RequestTournamentInfo extends AbstractMenuMessage {
    private int clientId;
    private int tournamentId;
}
