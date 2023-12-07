package com.nexusvision.server.model.messages.menu;

import lombok.Data;

/**
 * Requests tournaments
 *
 * @author kellerb
 */
@Data
public class FindTournament extends AbstractMenuMessage {
    private int clientId;
    private int tournamentStarting;
    private int tournamentInProgress;
    private int tournamentFinished;
}
