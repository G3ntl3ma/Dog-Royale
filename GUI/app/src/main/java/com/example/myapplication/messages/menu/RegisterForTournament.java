package com.example.myapplication.messages.menu;

import lombok.Data;

/**
 * Registrierung Turnier
 *
 * @author kellerb
 */
@Data
public class RegisterForTournament extends AbstractMenuMessage {
    private int tournamentId;
    private int clientId;
}
