package com.nexusvision.server.model.messages.menu;

import com.nexusvision.server.model.messages.AbstractMessage;
import lombok.Data;

/**
 * registration for the tournament
 *
 * @author kellerb
 */
@Data
public class RegisterForTournament extends AbstractMessage {
    private int tournamentId;
    private int clientId;
}
