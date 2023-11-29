package com.nexusvision.messages.menu;


import com.nexusvision.messages.game.AbstractGameMessage;
import lombok.Data;

/**
 *  Server bestätigt erfolgreiche Turnieranmeldung
 *
 * @author kellerb
 */
@Data
public class RegisteredForTournament extends AbstractMenuMessage {
    private boolean success;
    private int tournamentId;
    private int maxPlayer;
    private int rounds;

    @Data
    public static class players{
        private int clientId;
        private String name;
    }
}
