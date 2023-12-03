package com.nexusvision.server.model.messages.menu;

import lombok.Builder;
import lombok.Data;

/**
 * Anfrage nach aktuellen Turnierinformationen
 *
 * @author kellerb
 */
@Data
@Builder
public class RequestTournamentInfo extends AbstractMenuMessage {
    private int clientId;
    private int tournamentId;
}
