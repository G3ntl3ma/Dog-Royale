package com.nexusvision.messages.menu;


import com.nexusvision.messages.game.AbstractGameMessage;
import com.nexusvision.messages.game.BoardState;
import lombok.Data;

import java.util.List;

/**
 *  Server bestätigt erfolgreiche Turnieranmeldung
 *
 * @author kellerb
 */
@Data
public class RegisteredForTournament extends AbstractMenuMessage {
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
