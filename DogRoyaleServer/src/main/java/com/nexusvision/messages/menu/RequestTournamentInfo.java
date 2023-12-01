package com.nexusvision.messages.menu;

import lombok.Data;
import java.util.List;
/**
 * Anfrage nach aktuellen Turnierinformationen
 *
 * @author kellerb
 */
@Data
public class RequestTournamentInfo extends AbstractMenuMessage {
    private int clientId;
    private int tournamentId;
}
