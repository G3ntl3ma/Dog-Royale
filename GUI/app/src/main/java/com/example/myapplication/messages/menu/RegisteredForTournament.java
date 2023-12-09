package com.example.myapplication.messages.menu;


import com.example.myapplication.messages.game.AbstractGameMessage;
import com.example.myapplication.messages.game.BoardState;
import lombok.Data;

import java.util.List;

/**
 *  Server bestätigt erfolgreiche Turnieranmeldung
 *
 * @author kellerb
 */
@Data
public class RegisteredForTournament {
    private List<Player> players;
    private boolean success;
    private int tournamentId;
    private int maxPlayer;
    private int rounds;

    @Data
    public static class Player{
        private int clientId;
        private String name;
    }
}
