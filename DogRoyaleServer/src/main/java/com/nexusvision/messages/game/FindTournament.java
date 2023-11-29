package com.nexusvision.messages.game;

import lombok.Data;

/**
 * Anfrage nach Turnieren
 *
 * @author kellerb
 */
@Data
public class FindTournament extends AbstractGameMessage{
    private int clientId;
    private int tournamentStarting;
    private int tournamentInProgress;
    private int tournamentFinished;

}
