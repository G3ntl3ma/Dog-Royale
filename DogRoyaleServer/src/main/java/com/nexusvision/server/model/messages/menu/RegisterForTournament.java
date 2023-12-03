package com.nexusvision.server.model.messages.menu;

import lombok.Builder;
import lombok.Data;

/**
 * Registrierung Turnier
 *
 * @author kellerb
 */
@Data
@Builder
public class RegisterForTournament extends AbstractMenuMessage{
    private int tournamentId;
    private int clientId;
}
