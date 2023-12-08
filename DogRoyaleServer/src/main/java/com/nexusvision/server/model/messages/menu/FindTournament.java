package com.nexusvision.server.model.messages.menu;

import com.nexusvision.server.model.messages.AbstractMessage;
import lombok.Data;

/**
 * Requests tournaments
 *
 * @author kellerb
 */
@Data
public class FindTournament extends AbstractMessage {
    private int clientId;
    private int tournamentStarting;
    private int tournamentInProgress;
    private int tournamentFinished;
}
