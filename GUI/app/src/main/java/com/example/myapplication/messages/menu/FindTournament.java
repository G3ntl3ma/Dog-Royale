package com.example.myapplication.messages.menu;

import lombok.Data;

/**
 * Anfrage nach Turnieren
 *
 * @author kellerb
 */
@Data
public class FindTournament  {
    private int clientId;
    private int tournamentStarting;
    private int tournamentInProgress;
    private int tournamentFinished;

}