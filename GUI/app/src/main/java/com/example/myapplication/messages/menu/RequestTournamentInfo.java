package com.example.myapplication.messages.menu;

import lombok.Data;

/**
 * Anfrage nach aktuellen Turnierinformationen
 *
 * @author kellerb
 */
@Data
public class RequestTournamentInfo extends AbstractMenuMessage  {
    private int clientId;
    private int tournamentId;
}
