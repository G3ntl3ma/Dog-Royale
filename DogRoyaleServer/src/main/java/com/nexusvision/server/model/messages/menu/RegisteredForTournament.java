package com.nexusvision.server.model.messages.menu;


import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 *  Server bestätigt erfolgreiche Turnieranmeldung
 *
 * @author kellerb
 */
@Data
@Builder
public class RegisteredForTournament extends AbstractMenuMessage {
    private List<Player> players;
    private boolean success;
    private int tournamentId;
    private int maxPlayer;
    private int rounds;

    @Data
    @Builder
    public static class Player{
        private int clientId;
        private String name;
    }
}
