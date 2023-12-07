package com.nexusvision.server.model.messages.menu;

import lombok.Data;

/**
 * registration for the tournament
 *
 * @author kellerb
 */
@Data
public class RegisterForTournament extends AbstractMenuMessage{
    private int tournamentId;
    private int clientId;
}
