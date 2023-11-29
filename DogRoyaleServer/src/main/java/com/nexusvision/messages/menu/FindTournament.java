package com.nexusvision.messages.menu;

import com.nexusvision.messages.game.AbstractGameMessage;
import lombok.Data;

/**
 * Anfrage nach Turnieren
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
