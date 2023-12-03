package com.nexusvision.server.model.messages.menu;

import lombok.Builder;
import lombok.Data;

/**
 * Anfrage nach Turnieren
 *
 * @author kellerb
 */
@Data
@Builder
public class FindTournament extends AbstractMenuMessage {
    private int clientId;
    private int tournamentStarting;
    private int tournamentInProgress;
    private int tournamentFinished;
}
